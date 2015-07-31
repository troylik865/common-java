package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizPublishMessageDetailEntity </p> 
 *
 * <p>Description:biz_publish_message_detail 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
@Entity
@Table(name = "biz_publish_message_detail")
public class BizPublishMessageDetail extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 4448119337179497508L;

    /**
     *消息ID
     */
    @Column(length = 10)
    private long              messageId;

    /**
     *发布消息的会员ID
     */
    @Column(length = 10)
    private long              publishMemberId;

    /**
     *接收消息的会员ID
     */
    @Column(length = 10)
    private long              acceptMemberId;
    /**
     *消息接收的情况 查看/未查看
     */
    @Column(length = 10)
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

    /**
    * @return the id
    */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
      * @param messageId the messageid to set
      */
    public void setMessageid(long messageId) {
        this.messageId = messageId;
    }

    /**
      * @return the messageId 
      */
    public long getMessageId() {
        return this.messageId;
    }

    /**
      * @param acceptMemberId the acceptMemberId to set
      */
    public void setAcceptMemberId(long acceptMemberId) {
        this.acceptMemberId = acceptMemberId;
    }

    /**
      * @return the acceptMemberId 
      */
    public long getAcceptMemberId() {
        return this.acceptMemberId;
    }

    /**
      * @param status the status to set
      */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
      * @return the status 
      */
    public String getStatus() {
        return this.status;
    }

    /**
      * @param extendField the extendField to set
      */
    public void setExtendField(String extendField) {
        this.extendField = extendField;
    }

    /**
      * @return the extendField 
      */
    public String getExtendField() {
        return this.extendField;
    }

    /**
      * @param gmtCreate the gmtCreate to set
      */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
      * @return the gmtCreate 
      */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /**
      * @param gmtModified the gmtModified to set
      */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
      * @return the gmtModified 
      */
    public Date getGmtModified() {
        return this.gmtModified;
    }

    public long getPublishMemberId() {
        return publishMemberId;
    }

    public void setPublishMemberId(long publishMemberId) {
        this.publishMemberId = publishMemberId;
    }

}
