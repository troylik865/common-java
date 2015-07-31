package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/**
 * 站内信息
 * 
 * @author troy
 * @version $Id: BizSiteMessage.java, v 0.1 2014年7月19日 下午6:14:28 troy Exp $
 */
@Entity
@Table(name = "biz_site_message")
public class BizSiteMessage extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = -3929746829686984111L;

    /**
     *会员ID
     */
    @Column(length = 10)
    private long              memberId;

    /**
     *相关会员Id
     */
    @Column(length = 10)
    private long              relatedMemberId;

    /**
     *接收信息的会员Id
     */
    @Column(length = 10)
    private long              receiveMemberId;

    /**
     *信息类型
     */
    @Column(length = 40)
    private String            messageType;

    /**
     *信息内容
     */
    @Column(length = 1000)
    private String            messageContent;

    /**
     *状态
     */
    @Column(length = 45)
    private String            status;

    /**
     *扩展字段
     */
    @Column(length = 400)
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

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getRelatedMemberId() {
        return relatedMemberId;
    }

    public void setRelatedMemberId(long relatedMemberId) {
        this.relatedMemberId = relatedMemberId;
    }

    public long getReceiveMemberId() {
        return receiveMemberId;
    }

    public void setReceiveMemberId(long receiveMemberId) {
        this.receiveMemberId = receiveMemberId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
