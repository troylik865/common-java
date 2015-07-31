package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.biz.enums.FinanceEnum;
import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizFinanceEntity </p> 
 *
 * <p>Description:biz_finance 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Entity
@Table(name = "biz_finance")
public class BizFinance extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 1407044480629938153L;

    /**
     *会员ID
     */
    @Column(length = 10)
    private long              memberId;
    /**
     *
     */
    @Column(length = 10)
    private String            type;
    /**
     *具体的值
     */
    @Column(length = 19)
    private Long              value;
    /**
     *扩展字段N
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
     * 获取会员默认的资金对象
     * 
     * @param memberId
     * @return
     */
    public static BizFinance getDefaultFinance(long memberId) {
        BizFinance finance = new BizFinance();
        finance.setMemberId(memberId);
        finance.setType(FinanceEnum.GOLD.getValue());
        finance.setValue(3000L);
        Date date = new Date();
        finance.setGmtCreate(date);
        finance.setGmtModified(date);
        return finance;
    }

}
