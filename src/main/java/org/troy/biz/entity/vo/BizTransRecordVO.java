/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package org.troy.biz.entity.vo;

import org.troy.biz.enums.InvestDirectionEnum;
import org.troy.biz.enums.TransStatusEnum;
import org.troy.common.utils.MoneyUtil;

/**
 * 总体说明
 *
 * <p>具体说明</p>
 *
 * @author wb-chenyy@alipay.com
 * @version $Id: BizTransRecordVO.java,v 0.1 2014-7-14 下午04:34:38 wb-chenyy Exp $
 */
public class BizTransRecordVO {

    /**
     * 数据库记录Id
     */
    private Long   id;

    /**
     *会员ID
     */
    private String memberId;

    /**
     *投资品种
     */
    private String investType;

    private String investTypeStr;

    /**
     *导入时间 YYYY-MM-DD
     */
    private String importDate;

    /**
     *记录类型 ，主要记录是否是第一次导入
     */
    private String recordType;

    /**
     *手续费
     */
    private Long   fee;

    private String feeWYuan;

    /**
     *当日盈亏
     */
    private Long   gainsAndLosses;

    private String gainsAndLossesWYuan;

    /**
     *当日入金
     */
    private Long   currIncome;

    private String currIncomeWYuan;

    /**
     * 累计盈亏 是一个百分比数
     */
    private String totalGainsAndLosses;

    /**
     *当日出金
     */
    private Long   currOutcome;

    private String currOutcomeWYuan;

    /**
     *当天权益
     */
    private Long   currValue;

    private String currValueWYuan;

    /**
     * 上日权益
     */
    private Long   lastDayValue;

    private String lastDayValueWYuan;

    /**
     * 初期资金
     */
    private Long   origionValue;

    private String origionValueWYuan;

    /**
     * 累计入金
     */
    private Long   totalIncome;

    private String totalIncomeWYuan;

    /**
     * 累计出金
     */
    private Long   totalOutcome;

    private String totalOutcomeWYuan;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter method for property <tt>memberId</tt>.
     * 
     * @return property value of memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * Setter method for property <tt>memberId</tt>.
     * 
     * @param memberId value to be assigned to property memberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
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
        this.investTypeStr = InvestDirectionEnum.getInvestDirectionEnum(investType).getName();
    }

    /**
     * Getter method for property <tt>importDate</tt>.
     * 
     * @return property value of importDate
     */
    public String getImportDate() {
        return importDate;
    }

    /**
     * Setter method for property <tt>importDate</tt>.
     * 
     * @param importDate value to be assigned to property importDate
     */
    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    /**
     * Getter method for property <tt>recordType</tt>.
     * 
     * @return property value of recordType
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     * Setter method for property <tt>recordType</tt>.
     * 
     * @param recordType value to be assigned to property recordType
     */
    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    /**
     * Getter method for property <tt>fee</tt>.
     * 
     * @return property value of fee
     */
    public Long getFee() {
        return fee;
    }

    /**
     * Setter method for property <tt>fee</tt>.
     * 
     * @param fee value to be assigned to property fee
     */
    public void setFee(Long fee) {
        this.fee = fee;
        this.feeWYuan = MoneyUtil.toWYuan(fee);
    }

    /**
     * Getter method for property <tt>gainsAndLosses</tt>.
     * 
     * @return property value of gainsAndLosses
     */
    public Long getGainsAndLosses() {
        return gainsAndLosses == null ? 0 : gainsAndLosses;
    }

    /**
     * Setter method for property <tt>gainsAndLosses</tt>.
     * 
     * @param gainsAndLosses value to be assigned to property gainsAndLosses
     */
    public void setGainsAndLosses(Long gainsAndLosses) {
        this.gainsAndLosses = gainsAndLosses;
        this.gainsAndLossesWYuan = MoneyUtil.toWYuan(gainsAndLosses);
    }

    /**
     * Getter method for property <tt>currIncome</tt>.
     * 
     * @return property value of currIncome
     */
    public Long getCurrIncome() {
        return currIncome;
    }

    /**
     * Setter method for property <tt>currIncome</tt>.
     * 
     * @param currIncome value to be assigned to property currIncome
     */
    public void setCurrIncome(Long currIncome) {
        this.currIncome = currIncome;
        this.currIncomeWYuan = MoneyUtil.toWYuan(currIncome);
    }

    /**
     * Getter method for property <tt>currOutcome</tt>.
     * 
     * @return property value of currOutcome
     */
    public Long getCurrOutcome() {
        return currOutcome;
    }

    /**
     * Setter method for property <tt>currOutcome</tt>.
     * 
     * @param currOutcome value to be assigned to property currOutcome
     */
    public void setCurrOutcome(Long currOutcome) {
        this.currOutcome = currOutcome;
        this.currOutcomeWYuan = MoneyUtil.toWYuan(currOutcome);
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
        this.currValueWYuan = MoneyUtil.toWYuan(currValue);
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
        this.lastDayValueWYuan = MoneyUtil.toWYuan(lastDayValue);
    }

    /**
     * Getter method for property <tt>lastDayValueWYuan</tt>.
     * 
     * @return property value of lastDayValueWYuan
     */
    public String getLastDayValueWYuan() {
        return lastDayValueWYuan;
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
        this.origionValueWYuan = MoneyUtil.toWYuan(origionValue);
    }

    /**
     * Getter method for property <tt>totalIncome</tt>.
     * 
     * @return property value of totalIncome
     */
    public Long getTotalIncome() {
        return totalIncome;
    }

    /**
     * Setter method for property <tt>totalIncome</tt>.
     * 
     * @param totalIncome value to be assigned to property totalIncome
     */
    public void setTotalIncome(Long totalIncome) {
        this.totalIncome = totalIncome;
        this.totalIncomeWYuan = MoneyUtil.toWYuan(totalIncome);
    }

    /**
     * Getter method for property <tt>totalOutcome</tt>.
     * 
     * @return property value of totalOutcome
     */
    public Long getTotalOutcome() {
        return totalOutcome;
    }

    /**
     * Setter method for property <tt>totalOutcome</tt>.
     * 
     * @param totalOutcome value to be assigned to property totalOutcome
     */
    public void setTotalOutcome(Long totalOutcome) {
        this.totalOutcome = totalOutcome;
        this.totalOutcomeWYuan = MoneyUtil.toWYuan(totalOutcome);
    }

    /**
     * Getter method for property <tt>feeWYuan</tt>.
     * 
     * @return property value of feeWYuan
     */
    public String getFeeWYuan() {
        return feeWYuan;
    }

    /**
     * Getter method for property <tt>gainsAndLossesWYuan</tt>.
     * 
     * @return property value of gainsAndLossesWYuan
     */
    public String getGainsAndLossesWYuan() {
        return gainsAndLossesWYuan;
    }

    /**
     * Getter method for property <tt>currIncomeWYuan</tt>.
     * 
     * @return property value of currIncomeWYuan
     */
    public String getCurrIncomeWYuan() {
        return currIncomeWYuan;
    }

    /**
     * Getter method for property <tt>currOutcomeWYuan</tt>.
     * 
     * @return property value of currOutcomeWYuan
     */
    public String getCurrOutcomeWYuan() {
        return currOutcomeWYuan;
    }

    /**
     * Getter method for property <tt>currValueWYuan</tt>.
     * 
     * @return property value of currValueWYuan
     */
    public String getCurrValueWYuan() {
        return currValueWYuan;
    }

    /**
     * Getter method for property <tt>origionValueWYuan</tt>.
     * 
     * @return property value of origionValueWYuan
     */
    public String getOrigionValueWYuan() {
        return origionValueWYuan;
    }

    /**
     * Getter method for property <tt>totalIncomeWYuan</tt>.
     * 
     * @return property value of totalIncomeWYuan
     */
    public String getTotalIncomeWYuan() {
        return totalIncomeWYuan;
    }

    /**
     * Getter method for property <tt>totalOutcomeWYuan</tt>.
     * 
     * @return property value of totalOutcomeWYuan
     */
    public String getTotalOutcomeWYuan() {
        return totalOutcomeWYuan;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Setter method for property <tt>feeWYuan</tt>.
     * 
     * @param feeWYuan value to be assigned to property feeWYuan
     */
    public void setFeeWYuan(String feeWYuan) {
        this.feeWYuan = feeWYuan;
    }

    /**
     * Setter method for property <tt>gainsAndLossesWYuan</tt>.
     * 
     * @param gainsAndLossesWYuan value to be assigned to property gainsAndLossesWYuan
     */
    public void setGainsAndLossesWYuan(String gainsAndLossesWYuan) {
        this.gainsAndLossesWYuan = gainsAndLossesWYuan;
    }

    /**
     * Setter method for property <tt>currIncomeWYuan</tt>.
     * 
     * @param currIncomeWYuan value to be assigned to property currIncomeWYuan
     */
    public void setCurrIncomeWYuan(String currIncomeWYuan) {
        this.currIncomeWYuan = currIncomeWYuan;
    }

    /**
     * Setter method for property <tt>currOutcomeWYuan</tt>.
     * 
     * @param currOutcomeWYuan value to be assigned to property currOutcomeWYuan
     */
    public void setCurrOutcomeWYuan(String currOutcomeWYuan) {
        this.currOutcomeWYuan = currOutcomeWYuan;
    }

    /**
     * Setter method for property <tt>currValueWYuan</tt>.
     * 
     * @param currValueWYuan value to be assigned to property currValueWYuan
     */
    public void setCurrValueWYuan(String currValueWYuan) {
        this.currValueWYuan = currValueWYuan;
    }

    /**
     * Setter method for property <tt>lastDayValueWYuan</tt>.
     * 
     * @param lastDayValueWYuan value to be assigned to property lastDayValueWYuan
     */
    public void setLastDayValueWYuan(String lastDayValueWYuan) {
        this.lastDayValueWYuan = lastDayValueWYuan;
    }

    /**
     * Setter method for property <tt>origionValueWYuan</tt>.
     * 
     * @param origionValueWYuan value to be assigned to property origionValueWYuan
     */
    public void setOrigionValueWYuan(String origionValueWYuan) {
        this.origionValueWYuan = origionValueWYuan;
    }

    /**
     * Setter method for property <tt>totalIncomeWYuan</tt>.
     * 
     * @param totalIncomeWYuan value to be assigned to property totalIncomeWYuan
     */
    public void setTotalIncomeWYuan(String totalIncomeWYuan) {
        this.totalIncomeWYuan = totalIncomeWYuan;
    }

    /**
     * Setter method for property <tt>totalOutcomeWYuan</tt>.
     * 
     * @param totalOutcomeWYuan value to be assigned to property totalOutcomeWYuan
     */
    public void setTotalOutcomeWYuan(String totalOutcomeWYuan) {
        this.totalOutcomeWYuan = totalOutcomeWYuan;
    }

    /**
     * Getter method for property <tt>investTypeStr</tt>.
     * 
     * @return property value of investTypeStr
     */
    public String getInvestTypeStr() {
        return investTypeStr;
    }

    /**
     * Setter method for property <tt>investTypeStr</tt>.
     * 
     * @param investTypeStr value to be assigned to property investTypeStr
     */
    public void setInvestTypeStr(String investTypeStr) {
        this.investTypeStr = investTypeStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.statusName = TransStatusEnum.getEnum(status).getName();
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTotalGainsAndLosses() {
        return totalGainsAndLosses;
    }

    public void setTotalGainsAndLosses(String totalGainsAndLosses) {
        this.totalGainsAndLosses = totalGainsAndLosses;
    }

}
