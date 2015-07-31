package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizFinanceTransDetailEntity </p> 
 *
 * <p>Description:biz_finance_trans_detail 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Entity
@Table(name = "biz_finance_trans_detail")
public class BizFinanceTransDetail extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 1411884969388565807L;

    /**
     *会员ID
     */
    @Column(length = 10)
    private long              memberId;
    /**
     *资金类型
     */
    @Column(length = 10)
    private String            type;
    /**
     *资金类型对应的值
     */
    @Column(length = 19)
    private Long              value;

    /**
     *资金类型账户余额
     */
    @Column(length = 19)
    private Long              accountBalance;

    /**
     *交易用途
     */
    @Column(length = 30)
    private String            transUse;
    /**
     *交易类型，主要记录资金的出/入
     */
    @Column(length = 10)
    private String            transType;
    /**
     *交易用途对应的业务逻辑ID
     */
    @Column(length = 10)
    private long              transUseId;

    /**
     * 当前库存金额
     */
    @Column(length = 19)
    private Long              sysLeft;

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
      * @param memberId the memberId to set
      */
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public Long getSysLeft() {
        return sysLeft;
    }

    public void setSysLeft(Long sysLeft) {
        this.sysLeft = sysLeft;
    }

    /**
      * @return the memberId 
      */
    public long getMemberId() {
        return this.memberId;
    }

    /**
      * @param type the type to set
      */
    public void setType(String type) {
        this.type = type;
    }

    /**
      * @return the type 
      */
    public String getType() {
        return this.type;
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
      * @param transUse the transUse to set
      */
    public void setTransUse(String transUse) {
        this.transUse = transUse;
    }

    /**
      * @return the transUse 
      */
    public String getTransUse() {
        return this.transUse;
    }

    /**
      * @param transType the transType to set
      */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
      * @return the transType 
      */
    public String getTransType() {
        return this.transType;
    }

    /**
      * @param transUseId the transUseId to set
      */
    public void setTransUseId(long transUseId) {
        this.transUseId = transUseId;
    }

    /**
      * @return the transUseId 
      */
    public long getTransUseId() {
        return this.transUseId;
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

    public Long getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Long accountBalance) {
        this.accountBalance = accountBalance;
    }
}
