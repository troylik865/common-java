package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/**
 * 
 * 短信记录表
 *
 * @author siren.lb
 * @version $Id: 
.java,v 0.1 2014年7月29日 上午11:28:16 siren.lb Exp $
 */
@Entity
@Table(name = "biz_mobile_msg")
public class BizMobileMsg extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 2675844715965234500L;

    /**
     *手机号
     */
    @Column(length = 45)
    private String            phoneNumber;

    /**
     *短信内容
     */
    @Column(length = 400)
    private String            msgContent;

    /**
     *短信用途
     */
    @Column(length = 45)
    private String            msgUse;

    /**
     *扩展字段
     */
    @Column(length = 100)
    private String            extendField;

    /**
     *创建时间
     */
    @Column(length = 19)
    private Date              gmtCreate;

    /**
     *修改时间
     */
    @Column(length = 19)
    private Date              gmtModified;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgUse() {
        return msgUse;
    }

    public void setMsgUse(String msgUse) {
        this.msgUse = msgUse;
    }

    public String getExtendField() {
        return extendField;
    }

    public void setExtendField(String extendField) {
        this.extendField = extendField;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}
