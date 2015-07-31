package org.troy.common.component.sync.handler.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.troy.biz.service.CommonService;
import org.troy.common.component.sync.bean.LocalMessageCache;
import org.troy.common.component.sync.domain.DataSyncCipher;
import org.troy.common.component.sync.domain.DataSyncConst;
import org.troy.common.component.sync.domain.DataSyncMessage;
import org.troy.common.component.sync.domain.TransportSQL;
import org.troy.common.component.sync.handler.ServerHandler;
import org.troy.common.component.sync.util.DataSyncMessageBuilder;
import org.troy.common.component.sync.util.SqlParser;
import org.troy.common.component.sync.util.SyncDataUtil;
import org.troy.common.utils.RSAUtil;
import org.troy.system.cache.SecretkeyCache;

import com.alibaba.fastjson.JSON;

/**
 * 服务端监听消息处理类
 * @author 杨磊
 * @since 2014-4-24 下午4:47:16
 * Copyright © 2013 - 2014 CNNCT
 */
public class ServerHandlerImpl implements ServerHandler {
    private static Logger log = Logger.getLogger(ServerHandlerImpl.class);

    @Autowired
    private CommonService service;
    
    /**
     * 接收报文(处理来自支付宝公共平台的缴费记录)
     */
    @Override
    public void handleMessage(DataSyncCipher cipher) {
        if (cipher != null) {
        	//应该将后续工作分派到线程池运行
        	dispatchWork(cipher);
        } else {
            log.debug("丢弃无效的回包,继续等待");
        }
    }
    
    /**
     * 解析报文
     * @param cipher
     * @return
     */
    private DataSyncMessage parseMessage(DataSyncCipher cipher){
    	DataSyncMessage message = null;
    	try {
            String publicKey = SecretkeyCache.getPublicKeyString(DataSyncConst.ALIPAY_SOURCE);
            boolean isVerify = RSAUtil.verify(cipher.getCipher(), publicKey, cipher.getSign());
            if (isVerify) {
                String decodedData = RSAUtil.decryptByPublicKey(cipher.getCipher(), publicKey);
                message = JSON.parseObject(decodedData, DataSyncMessage.class);
            } else {
                log.debug("校验回包数据错误");
            }
        } catch (Exception e) {
            log.debug("解析回包出现错误");
        }
    	return message;
    }
    
    /**
     * 分派工作
     * @param cipher
     */
    private void dispatchWork(DataSyncCipher cipher){
    	DataSyncMessage message = parseMessage(cipher);
        if (message != null) {
            switch (message.getAck()) {
                case DataSyncConst.ACK_PROCESSING:
                    //处理正常的 待处理业务包
                    doWork(message);
                    break;
                case DataSyncConst.ACK_FAIL:
                    doProcessFail(message);
                    break;
                case DataSyncConst.ACK_OK:
                    doProcessOk(message);
                    break;
                default:
                    break;
            }
        } else {
            log.debug("没有解析到回包数据");
        }
    }

    /**
     * 处理业务回包
     * @param message
     */
    private void doWork(DataSyncMessage message) {
        try {
            log.debug("处理回包,RequestId:" + message.getRequestId());
            //开始写库
            boolean isok = writeToDB(message.getData());
            if (isok) {
                //写库成功后响应给客户端一个业务处理成功的ack包
                replyMessage(message.getRequestId(), DataSyncConst.ACK_OK);
            } else {
                replyMessage(message.getRequestId(), DataSyncConst.ACK_FAIL,"DB写入失败,RequestId:" + message.getRequestId());
                log.debug("DB写入失败,RequestId:" + message.getRequestId());
            }
        } catch (Exception e) {
            replyMessage(message.getRequestId(), DataSyncConst.ACK_FAIL, e.getMessage());
            log.debug("DB写入出现异常,RequestId:" + message.getRequestId()+"\r\n异常信息:"+e.getMessage());
        }
    }

    /**
     * 处理非业务回包
     * @param message
     */
    private void doProcessFail(DataSyncMessage message) {
        log.debug("RequestId:"+message.getRequestId()+" 处理失败! 错误信息:"+message.getExtra());
        if(LocalMessageCache.queryCount(message.getRequestId())<=DataSyncConst.MAX_REPEAT_NUM){
        	if(LocalMessageCache.containsKey(message.getRequestId())){
        		//对方处理失败, 从本地队列中将message取出来重发一次
        		SyncDataUtil.sendDataAsyncToQueue(LocalMessageCache.get(message.getRequestId()));
        		LocalMessageCache.addCount(message.getRequestId());
        		log.debug("RequestId:"+message.getRequestId()+" 从本地缓存中取出重发...");
        	}
        } else {
        	LocalMessageCache.clearCount(message.getRequestId());
        	log.debug("RequestId:"+message.getRequestId()+" 重发"+DataSyncConst.MAX_REPEAT_NUM+"次处理失败,请检查!\r\n错误信息:"+message.getExtra());
        }
        
    }

    /**
     * 处理非业务回包
     * @param message
     */
    private void doProcessOk(DataSyncMessage message) {
        log.debug("RequestId:"+message.getRequestId()+" 处理成功! ");
        if(LocalMessageCache.containsKey(message.getRequestId())){
        	//对方处理成功, 将message从本地队列中移除
        	LocalMessageCache.remove(message.getRequestId());
        	log.debug("RequestId:"+message.getRequestId()+" 已从本地缓存中删除成功! ");
        	LocalMessageCache.clearCount(message.getRequestId());
    		log.debug("RequestId:"+message.getRequestId()+" 重发计数器清空成功!");
        }
    }

    /**
     * 回复处理结果
     * @param requestId
     * @param ack
     * @param extra
     */
    private void replyMessage(String requestId, int ack, String... extra) {
        SyncDataUtil.sendReplyAsyncToQueue(DataSyncMessageBuilder.buildReplyMessage(requestId, ack, extra));
        log.debug("响应包>requestId:" + requestId + " ack:" + ack);
    }

    /**
     * 写入数据库
     * @param sqls
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
	public boolean writeToDB(List<TransportSQL> sqls) {
    	try {
    		int result = 0;
        	SqlParser sqlParser = new SqlParser();
        	for (TransportSQL sql : sqls) {
        		if(sql.getDml().equals(DataSyncConst.DML_UPDATE)){
        			//如果是update操作,先查询一下对应的记录是否存在,如果不存在则插入
        			Integer nums = service.queryForInt("select count(1) from "+sql.getTable()+" where "+sql.getPrimaryKey()+"=?",sql.getPrimaryValue());
        			if(nums==0){
        				//转换成insert语句
        				sql.setSql(sqlParser.transUpdate2Insert(sql));
        			}
        		}
        		result +=service.update(sql.getSql());
    		}
    		return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}