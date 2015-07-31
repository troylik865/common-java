package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizTransRecordEntity </p> 
 *
 * <p>Description:biz_trans_record 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
@Entity
@Table(name = "biz_trans_record")
public class BizTransRecord extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 173761130126229399L;

    /**
     *会员ID
     */
    @Column(length = 10)
    private Long              memberId;

    /**
     *投资品种
     */
    @Column(length = 20)
    private String            investType;

    /**
     *导入时间 YYYY-MM-DD
     */
    @Column(length = 30)
    private String            importDate;

    /**
     *记录类型 ，主要记录是否是第一次导入
     */
    @Column(length = 10)
    private String            recordType;

    /**
     *手续费
     */
    @Column(length = 19)
    private Long              fee;

    /**
     *当日盈亏
     */
    @Column(length = 19)
    private Long              gainsAndLosses;

    /**
     *当日入金
     */
    @Column(length = 19)
    private Long              currIncome;

    /**
     *当日出金
     */
    @Column(length = 19)
    private Long              currOutcome;

    /**
     *当天权益
     */
    @Column(length = 20)
    private Long              currValue;

    /**
     *上日权益
     */
    @Column(length = 20)
    private Long              lastDayValue;

    /**
     * 累计盈亏 是一个百分比数
     */
    @Column(length = 20)
    private String            totalGainsAndLosses;

    /**
     * 初期资金
     */
    @Column(length = 20)
    private Long              origionValue;

    /**
     *扩展字段
     */
    @Column(length = 200)
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
    private Date              gmtValidate;

    /**
     * 状态：I:初始;S:通过;F:退回
     */
    @Column(length = 1)
    private String            status;

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
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
      * @return the memberId 
      */
    public Long getMemberId() {
        return this.memberId;
    }

    /**
     * Getter method for property <tt>investType</tt>.
     * 
     * @return property value of investType
     */
    public String getInvestType() {
        return investType;
    }

    /**
     * Setter method for property <tt>investType</tt>.
     * 
     * @param investType value to be assigned to property investType
     */
    public void setInvestType(String investType) {
        this.investType = investType;
    }

    /**
      * @param importDate the importDate to set
      */
    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    /**
      * @return the importDate 
      */
    public String getImportDate() {
        return this.importDate;
    }

    /**
      * @param recordType the recordType to set
      */
    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    /**
      * @return the recordType 
      */
    public String getRecordType() {
        return this.recordType;
    }

    /**
      * @param fee the fee to set
      */
    public void setFee(Long fee) {
        this.fee = fee;
    }

    /**
      * @return the fee 
      */
    public Long getFee() {
        return this.fee;
    }

    /**
      * @param gainsAndLosses the gainsAndLosses to set
      */
    public void setGainsAndLosses(Long gainsAndLosses) {
        this.gainsAndLosses = gainsAndLosses;
    }

    /**
      * @return the gainsAndLosses 
      */
    public Long getGainsAndLosses() {
        return this.gainsAndLosses;
    }

    /**
      * @param currIncome the currIncome to set
      */
    public void setCurrIncome(Long currIncome) {
        this.currIncome = currIncome;
    }

    /**
      * @return the currIncome 
      */
    public Long getCurrIncome() {
        return this.currIncome == null ? 0 : this.currIncome;
    }

    /**
      * @param currOutcome the currOutcome to set
      */
    public void setCurrOutcome(Long currOutcome) {
        this.currOutcome = currOutcome;
    }

    /**
      * @return the currOutcome 
      */
    public Long getCurrOutcome() {
        return this.currOutcome == null ? 0 : this.currOutcome;
    }

    /**
     * Getter method for property <tt>currValue</tt>.
     * 
     * @return property value of currValue
     */
    public Long getCurrValue() {
        return currValue;
    }

    /**
     * Setter method for property <tt>currValue</tt>.
     * 
     * @param currValue value to be assigned to property currValue
     */
    public void setCurrValue(Long currValue) {
        this.currValue = currValue;
    }

    /**
     * Getter method for property <tt>lastDayValue</tt>.
     * 
     * @return property value of lastDayValue
     */
    public Long getLastDayValue() {
        return lastDayValue;
    }

    /**
     * Setter method for property <tt>lastDayValue</tt>.
     * 
     * @param lastDayValue value to be assigned to property lastDayValue
     */
    public void setLastDayValue(Long lastDayValue) {
        this.lastDayValue = lastDayValue;
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
     * Getter method for property <tt>origionValue</tt>.
     * 
     * @return property value of origionValue
     */
    public Long getOrigionValue() {
        return origionValue;
    }

    /**
     * Setter method for property <tt>origionValue</tt>.
     * 
     * @param origionValue value to be assigned to property origionValue
     */
    public void setOrigionValue(Long origionValue) {
        this.origionValue = origionValue;
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalGainsAndLosses() {
        return totalGainsAndLosses;
    }

    public void setTotalGainsAndLosses(String totalGainsAndLosses) {
        this.totalGainsAndLosses = totalGainsAndLosses;
    }

    public Date getGmtValidate() {
        return gmtValidate;
    }

    public void setGmtValidate(Date gmtValidate) {
        this.gmtValidate = gmtValidate;
    }

}
