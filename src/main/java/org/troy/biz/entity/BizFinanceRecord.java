package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/**
 * 交易记录表  主要用于记录当前系统的库存
 *
 * @author siren.lb
 * @version $Id: BizFinanceRecord.java,v 0.1 2014年10月30日 下午12:05:48 siren.lb Exp $
 */
@Entity
@Table(name = "biz_finance_record")
public class BizFinanceRecord extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 4906790913250221040L;

    /**
     *类型
     */
    @Column(length = 20)
    private String            type;

    /**
     *资金类型
     */
    @Column(length = 20)
    private String            financeType;
    /**
     *资金类型对应的值
     */
    @Column(length = 11)
    private Long              financeValue;

    /**
     *扩展字段
     */
    @Column(length = 45)
    private String            extendField;
    /**
     *创建时间
     */
    private Date              gmtCreate;
    /**
     *修改时间
     */
    private Date              gmtModified;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFinanceType() {
        return financeType;
    }

    public void setFinanceType(String financeType) {
        this.financeType = financeType;
    }

    public Long getFinanceValue() {
        return financeValue;
    }

    public void setFinanceValue(Long financeValue) {
        this.financeValue = financeValue;
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
