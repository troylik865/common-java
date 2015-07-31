package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizPublishMessageEntity </p> 
 *
 * <p>Description:biz_publish_message 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
@Entity
@Table(name = "biz_publish_message")
public class BizPublishMessage extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = -2690729138303348372L;

    /**
     *会员ID
     */
    @Column(length = 10)
    private long              memberId;
    /**
     *发布内容
     */
    @Column(length = 1000)
    private String            content;

    /**
     * 状态 成功：S 失败：F 审核中和初始化：I
     */
    private String            status;

    /**
     *扩展字段
     */
    @Column(length = 400)
    private String            extendField;
    /**
     *创建时间
     */
    private Date              gmtCreate;
    /**
     *修改时间
     */
    private Date              gmtModified;

    /**
     * 通过验证的时间
     */
    private Date              gmtValidated;

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
      * @param memberId the memberId to set
      */
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    /**
      * @return the memberId 
      */
    public long getMemberId() {
        return this.memberId;
    }

    /**
      * @param content the content to set
      */
    public void setContent(String content) {
        this.content = content;
    }

    /**
      * @return the content 
      */
    public String getContent() {
        return this.content;
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

    public Date getGmtValidated() {
        return gmtValidated;
    }

    public void setGmtValidated(Date gmtValidated) {
        this.gmtValidated = gmtValidated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
