package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizAttachEntity </p> 
 *
 * <p>Description:biz_attach 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
@Entity
@Table(name = "biz_attach")
public class BizAttach extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = -5804885689346799013L;

    /**
     *附件真正的名称
     */
    @Column(length = 200)
    private String            attachRealName;

    /**
     *附件名称
     */
    @Column(length = 200)
    private String            attachName;

    /**
     * 附件路径
     */
    @Column(length = 200)
    private String            attachPath;

    /**
     *主要是福建关联的业务类型
     */
    @Column(length = 40)
    private String            attachType;

    /**
     *附件关联的业务的数据IDN
     */
    @Column(length = 11)
    private Long              refId;

    /**
     *附件大小
     */
    @Column(length = 10)
    private Integer           attachSize;

    /**
     *花费的类型
     */
    @Column(length = 10)
    private String            costType;

    /**
     *对应的花费值
     */
    @Column(length = 19)
    private Long              value;

    /**
     *扩展字段
     */
    @Column(length = 200)
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
      * @param attachRealName the attachRealName to set
      */
    public void setAttachRealName(String attachRealName) {
        this.attachRealName = attachRealName;
    }

    /**
      * @return the attachRealName 
      */
    public String getAttachRealName() {
        return this.attachRealName;
    }

    /**
      * @param attachName the attachName to set
      */
    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    /**
      * @return the attachName 
      */
    public String getAttachName() {
        return this.attachName;
    }

    /**
      * @param attachType the attachType to set
      */
    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

    /**
      * @return the attachType 
      */
    public String getAttachType() {
        return this.attachType;
    }

    /**
      * @param refId the refId to set
      */
    public void setRefId(Long refId) {
        this.refId = refId;
    }

    /**
      * @return the refId 
      */
    public Long getRefId() {
        return this.refId;
    }

    /**
      * @param attachSize the attachSize to set
      */
    public void setAttachSize(Integer attachSize) {
        this.attachSize = attachSize;
    }

    /**
      * @return the attachSize 
      */
    public Integer getAttachSize() {
        return this.attachSize;
    }

    /**
      * @param costType the costType to set
      */
    public void setCostType(String costType) {
        this.costType = costType;
    }

    /**
      * @return the costType 
      */
    public String getCostType() {
        return this.costType;
    }

    /**
      * @param value the value to set
      */
    public void setValue(Long value) {
        this.value = value;
    }

    /**
      * @return the value 
      */
    public Long getValue() {
        return this.value;
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

    /**
     * Getter method for property <tt>attachPath</tt>.
     * 
     * @return property value of attachPath
     */
    public String getAttachPath() {
        return attachPath;
    }

    /**
     * Setter method for property <tt>attachPath</tt>.
     * 
     * @param attachPath value to be assigned to property attachPath
     */
    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }

}
