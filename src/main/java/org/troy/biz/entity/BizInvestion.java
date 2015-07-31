package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/**
 * 
 * 投资方向相关的实体类
 *
 *
 * @author siren.lb
 * @version $Id: BizInvestion.java,v 0.1 2014年7月26日 下午1:41:57 siren.lb Exp $
 */
@Entity
@Table(name = "biz_investion")
public class BizInvestion extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = -988716591653925312L;

    /**
     *会员ID
     */
    @Column(length = 10)
    private long              memberId;

    /**
     *投资方向
     */
    @Column(length = 20)
    private String            investDirection;

    /**
     * 是否通过参赛验证
     */
    @Column(length = 10)
    private String            isValidated;

    /**
     * 通过验证的时间
     */
    @Column(length = 19)
    private Date              gmtValidated;

    /**
     *扩展字段
     */
    @Column(length = 100)
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
    
    public long getMemberId() {
        return memberId;
    }
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }
    public String getInvestDirection() {
        return investDirection;
    }
    public void setInvestDirection(String investDirection) {
        this.investDirection = investDirection;
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
    
    

}
