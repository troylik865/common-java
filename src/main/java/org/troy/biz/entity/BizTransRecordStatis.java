package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/**
 * 交易记录统计信息
 * 
 * @author troy
 * @version $Id: BizTransRecordStatis.java, v 0.1 2014年7月22日 下午11:32:16 troy Exp $
 */
@Entity
@Table(name = "biz_trans_record_statis")
public class BizTransRecordStatis extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 5930725234915359287L;

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
     *初期资金
     */
    @Column(length = 19)
    private Long              origionValue;

    /**
     *最新权益
     */
    @Column(length = 19)
    private Long              lastestValue;

    /**
     *累计盈亏
     */
    @Column(length = 19)
    private Long              total;

    /**
     *累计入金
     */
    @Column(length = 19)
    private Long              totalIncome;

    /**
     *累计出金
     */
    @Column(length = 19)
    private Long              totalOutcome;

    /**
     *排名
     */
    @Column(length = 11)
    private int               rank;

    /**
     * 是否参赛
     */
    @Column(length = 10)
    private String            isValidated;

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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public Long getOrigionValue() {
        return null == origionValue ? 0 : origionValue;
    }

    public void setOrigionValue(Long origionValue) {
        this.origionValue = origionValue;
    }

    public Long getLastestValue() {
        return lastestValue;
    }

    public void setLastestValue(Long lastestValue) {
        this.lastestValue = lastestValue;
    }

    public Long getTotal() {
        return total == null ? 0 : total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalIncome() {
        return totalIncome == null ? 0 : totalIncome;
    }

    public void setTotalIncome(Long totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Long getTotalOutcome() {
        return totalOutcome == null ? 0 : totalOutcome;
    }

    public void setTotalOutcome(Long totalOutcome) {
        this.totalOutcome = totalOutcome;
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

    public String getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(String isValidated) {
        this.isValidated = isValidated;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
