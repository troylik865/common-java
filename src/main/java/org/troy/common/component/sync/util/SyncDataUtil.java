package org.troy.common.component.sync.util;

import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.troy.common.component.sync.bean.LocalMessageCache;
import org.troy.common.component.sync.domain.DataSyncCipher;
import org.troy.common.component.sync.domain.DataSyncConst;
import org.troy.system.util.SpringBeanLoader;

import com.alibaba.fastjson.JSON;

/**
 * 公众服务平台和后台系统数据同步模块 工具类
 * @author 杨磊
 * @since 2014-4-25 下午4:24:07
 * Copyright © 2013 - 2014 CNNCT
 */
public final class SyncDataUtil {
    private static Logger log = LoggerFactory.getLogger(SyncDataUtil.class);
    //锁对象,防止线程问题
    private static final ReentrantLock lock = new ReentrantLock();
    private static RabbitTemplate amqpTemplate;
    static{
    	try {
    		amqpTemplate = (RabbitTemplate)SpringBeanLoader.getSpringBean("rabbitTemplate");
		} catch (Exception e) {
			throw new RuntimeException("amqpTemplate初始化失败!");
		}
    }

    /**
     * 将业务数据发送到队列(异步)
     */
    public static void sendDataAsyncToQueue(DataSyncCipher message) {
        try{
            lock.lock();
            amqpTemplate.convertAndSend(DataSyncConst.UPSTREAM_QUEUE, message);
            if(!LocalMessageCache.containsKey(message.getRequestId())){
            	LocalMessageCache.put(message.getRequestId(), message);
            }
            LocalMessageCache.addCount(message.getRequestId());
        } catch (Exception e){
            log.warn("队列:"+DataSyncConst.UPSTREAM_QUEUE+"消息发送失败, 参数:"+ JSON.toJSONString(message),e);
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * 将回包发送到队列(异步)
     */
    public static void sendReplyAsyncToQueue(DataSyncCipher message) {
        try{
            lock.lock();
            amqpTemplate.convertAndSend(DataSyncConst.UPSTREAM_QUEUE, message);
        } catch (Exception e){
            log.warn("队列:"+DataSyncConst.UPSTREAM_QUEUE+"消息发送失败, 参数:"+ JSON.toJSONString(message),e);
        } finally {
            lock.unlock();
        }
    }
}
