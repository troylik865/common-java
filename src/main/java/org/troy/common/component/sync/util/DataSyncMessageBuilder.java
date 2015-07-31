package org.troy.common.component.sync.util;

import java.util.List;

import org.troy.common.component.sync.domain.DataSyncCipher;
import org.troy.common.component.sync.domain.DataSyncConst;
import org.troy.common.component.sync.domain.DataSyncMessage;
import org.troy.common.component.sync.domain.TransportSQL;
import org.troy.common.utils.RSAUtil;
import org.troy.system.cache.SecretkeyCache;

import com.alibaba.fastjson.JSON;

/**
 * DataSyncMessage构造器,自动构造加密过的密文
 * @author 杨磊
 * @since 2014-5-22 下午9:28:33
 * Copyright © 2013 - 2014 CNNCT
 */
public class DataSyncMessageBuilder {
    /**
     * 构造正常的业务message
     * @param list
     * @param dml
     * @param table
     * @return
     */
    public static DataSyncCipher buildDataMessage(List<TransportSQL> sqls){
        DataSyncMessage message  =new DataSyncMessage(UtilDate.getOrderNum());
        message.setData(sqls);
        return buildCipherMessage(message);
    }
    
    /**
     * 构造响应包message
     * @param requestId
     * @param ack
     * @param extra
     * @return
     */
    public static DataSyncCipher buildReplyMessage(String requestId,int ack,String... extra){
        DataSyncMessage replyMessage = new DataSyncMessage(requestId);
        replyMessage.setAck(ack);//处理完毕后响应给客户端一个ack包
        replyMessage.setExtra(extra.length>0?extra[0]:null);
        return buildCipherMessage(replyMessage);
    }

    /**
     * 公钥加密密文
     * 私钥生成签名
     * @param message
     * @return
     */
    private static DataSyncCipher buildCipherMessage(DataSyncMessage message){
        DataSyncCipher cipher = new DataSyncCipher();
        String privateKey= SecretkeyCache.getPrivateKeyString(DataSyncConst.ALIPAY_SOURCE);
        String data = JSON.toJSONString(message);
        try {
            String encodedData = RSAUtil.encryptByPrivateKey(data, privateKey);
            String sign = RSAUtil.sign(encodedData, privateKey);
            cipher.setCipher(encodedData);
            cipher.setSign(sign);
            cipher.setRequestId(message.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipher;
    }
}
