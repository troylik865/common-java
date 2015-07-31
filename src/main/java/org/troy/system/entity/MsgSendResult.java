package org.troy.system.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * 
 * @author troy
 * @version $Id: MsgSendResult.java, v 0.1 2014年6月25日 下午5:57:12 troy Exp $
 */
public class MsgSendResult implements Serializable {

    /** 序列化Id */
    private static final long serialVersionUID = 2592805600541208563L;

    /**
     * 短信Id
     */
    private String            msgId;

    /**
     * 是否发送成功
     */
    private boolean           isSuc;

    /**
     * 结果码
     */
    private String            resultCode;

    /**
     * 结果信息
     */
    private String            resultMsg;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public boolean isSuc() {
        return isSuc;
    }

    public void setSuc(boolean isSuc) {
        this.isSuc = isSuc;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
