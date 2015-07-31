package org.troy.common.component.sync.convert;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.troy.common.component.sync.domain.DataSyncCipher;

import com.alibaba.fastjson.JSON;
/**
 * 自定义的报文消息转换器(JSON)
 * @author 杨磊
 * @since 2014-4-24 下午4:51:29
 * Copyright © 2013 - 2014 CNNCT
 */
public class FastJsonMessageConverter  extends AbstractMessageConverter {
    public static final String DEFAULT_CHARSET = "UTF-8";

    private volatile String defaultCharset = DEFAULT_CHARSET;

    public FastJsonMessageConverter() {
        super();
    }

    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = (defaultCharset != null) ? defaultCharset
                : DEFAULT_CHARSET;
    }

    public Object fromMessage(Message message)
            throws MessageConversionException {
    	return JSON.parseObject(message.getBody(), DataSyncCipher.class);
    }

    @SuppressWarnings("unchecked")
	public <T> T fromMessage(Message message,T t) {
        String json = "";
        try {
            json = new String(message.getBody(),defaultCharset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) JSON.parseObject(json,t.getClass());
    }


    protected Message createMessage(Object objectToConvert,
                                    MessageProperties messageProperties)
            throws MessageConversionException {
        byte[] bytes;
        try {
            String jsonString = JSON.toJSONString(objectToConvert);
            bytes = jsonString.getBytes(this.defaultCharset);
        } catch (Exception e) {
            throw new MessageConversionException(
                    "Failed to convert Message content", e);
        }
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(this.defaultCharset);
        if (bytes != null) {
            messageProperties.setContentLength(bytes.length);
        }
        return new Message(bytes, messageProperties);

    }
}