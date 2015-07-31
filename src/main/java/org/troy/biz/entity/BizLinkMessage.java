package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizLinkMessageEntity </p> 
 *
 * <p>Description:biz_link_message 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Entity
@Table(name = "biz_link_message")
public class BizLinkMessage extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 1729697909093020591L;

    /**
     *会员ID
     */
    @Column(length = 10)
    private long              memberId;
    /**
     *姓名
     */
    @Column(length = 100)
    private String            name;
    /**
     *性别
     */
    @Column(length = 10)
    private String            sex;
    /**
     *年龄
     */
    @Column(length = 10)
    private Integer           age;
    /**
     *手机号码
     */
    @Column(length = 30)
    private String            phoneNumber;

    /**
     * 地址
     */
    @Column(length = 200)
    private String            address;

    /**
     *信息内容
     */
    @Column(length = 1000)
    private String            message;
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
      * @param name the name to set
      */
    public void setName(String name) {
        this.name = name;
    }

    /**
      * @return the name 
      */
    public String getName() {
        return this.name;
    }

    /**
      * @param sex the sex to set
      */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
      * @return the sex 
      */
    public String getSex() {
        return this.sex;
    }

    /**
      * @param age the age to set
      */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
      * @return the age 
      */
    public Integer getAge() {
        return this.age;
    }

    /**
      * @param phoneNumber the phoneNumber to set
      */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
      * @return the phoneNumber 
      */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
      * @param message the message to set
      */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
      * @return the message 
      */
    public String getMessage() {
        return this.message;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
