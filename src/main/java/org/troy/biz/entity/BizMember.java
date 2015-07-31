package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.biz.enums.CostTypeEnum;
import org.troy.biz.enums.IsValidatedEnum;
import org.troy.biz.enums.SexEnum;
import org.troy.common.utils.enums.EnumUtils;
import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizMemberEntity </p> 
 *
 * <p>Description:biz_member 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Entity
@Table(name = "biz_member")
public class BizMember extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 4537899114412145892L;

    /**
     *会员编号
     */
    @Column(length = 30)
    private String            memberNo;
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
     *用户名
     */
    @Column(length = 100)
    private String            userName;
    /**
     *密码
     */
    @Column(length = 100)
    private String            password;
    /**
     *地址
     */
    @Column(length = 200)
    private String            address;
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
     * 是否通过实名制验证
     * 
     * 针对业务的参赛审核信息转到biz_invenstion表中单独针对某个投资方向信息中
     */
    @Column(length = 10)
    private String            isValidated;

    /**
     * 通过验证的时间
     */
    @Column(length = 19)
    private Date              gmtValidated;

    /**
     * 上次登录的时间
     */
    @Column(length = 19)
    private Date              gmtLastLogin;

    @Column(length = 10)
    private long              attentDailyCost;

    @Column(length = 19)
    private String            costType;

    @Column(length = 400)
    private String            memberLink;

    public long getAttentDailyCost() {
        return attentDailyCost;
    }

    public void setAttentDailyCost(long attentDailyCost) {
        this.attentDailyCost = attentDailyCost;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

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
      * @param memberNo the memberNo to set
      */
    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    /**
      * @return the memberNo 
      */
    public String getMemberNo() {
        return this.memberNo;
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
      * @param userName the userName to set
      */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
      * @return the userName 
      */
    public String getUserName() {
        return this.userName;
    }

    /**
      * @param password the password to set
      */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
      * @return the password 
      */
    public String getPassword() {
        return this.password;
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

    public String getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(String isValidated) {
        this.isValidated = isValidated;
    }

    public Date getGmtValidated() {
        return gmtValidated;
    }

    public void setGmtValidated(Date gmtValidated) {
        this.gmtValidated = gmtValidated;
    }

    public Date getGmtLastLogin() {
        return gmtLastLogin;
    }

    public void setGmtLastLogin(Date gmtLastLogin) {
        this.gmtLastLogin = gmtLastLogin;
    }

    public String getSexStr() {
        String str = "";
        Object obj = EnumUtils.toMap(SexEnum.class).get(sex);
        if (obj != null) {
            str = obj.toString();
        }
        return str;
    }

    public String getIsValidatedStr() {
        String str = "";
        Object obj = EnumUtils.toMap(IsValidatedEnum.class).get(isValidated);
        if (obj != null) {
            str = obj.toString();
        }
        return str;
    }

    public String getCostTypeStr() {
        String str = "";
        Object obj = EnumUtils.toMap(CostTypeEnum.class).get(costType);
        if (obj != null) {
            str = obj.toString();
        }
        return str;
    }

    public String getMemberLink() {
        return memberLink;
    }

    public void setMemberLink(String memberLink) {
        this.memberLink = memberLink;
    }
}
