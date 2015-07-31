package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizMemberSearchEntity </p> 
 *
 * <p>Description:biz_member_search 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Entity
@Table(name = "biz_member_search")
public class BizMemberSearch extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 3475047162034748658L;

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
     *邮箱
     */
    @Column(length = 100)
    private String            email;
    /**
     *QQ
     */
    @Column(length = 30)
    private String            qq;
    /**
     *投资方向
     */
    @Column(length = 20)
    private String            investDirection;
    /**
     *地址
     */
    @Column(length = 200)
    private String            address;
    /**
     *最高交易账户
     */
    @Column(length = 10)
    private Integer           highestTransAccountValue;
    /**
     *意向交易账户
     */
    @Column(length = 10)
    private Integer           intentTransAccountValue;
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
      * @param email the email to set
      */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
      * @return the email 
      */
    public String getEmail() {
        return this.email;
    }

    /**
      * @param qq the qq to set
      */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
      * @return the qq 
      */
    public String getQq() {
        return this.qq;
    }

    /**
      * @param investDirection the investDirection to set
      */
    public void setInvestDirection(String investDirection) {
        this.investDirection = investDirection;
    }

    /**
      * @return the investDirection 
      */
    public String getInvestDirection() {
        return this.investDirection;
    }

    /**
      * @param adress the adress to set
      */
    public void setAddress(String adress) {
        this.address = adress;
    }

    /**
      * @return the adress 
      */
    public String getAddress() {
        return this.address;
    }

    /**
      * @param highestTransAccountValue the highestTransAccountValue to set
      */
    public void setHighestTransAccountValue(Integer highestTransAccountValue) {
        this.highestTransAccountValue = highestTransAccountValue;
    }

    /**
      * @return the highestTransAccountValue 
      */
    public Integer getHighestTransAccountValue() {
        return this.highestTransAccountValue;
    }

    /**
      * @param intentTransAccountValue the intentTransAccountValue to set
      */
    public void setIntentTransAccountValue(Integer intentTransAccountValue) {
        this.intentTransAccountValue = intentTransAccountValue;
    }

    /**
      * @return the intentTransAccountValue 
      */
    public Integer getIntentTransAccountValue() {
        return this.intentTransAccountValue;
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

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

}
