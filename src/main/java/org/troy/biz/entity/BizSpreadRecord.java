package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/**
 * 推广信息相关的实体类
 * 
 * @author troy
 * @version $Id: BizSpreadRecord.java, v 0.1 2014年7月17日 上午11:45:53 troy Exp $
 */
@Entity
@Table(name = "biz_spread_record")
public class BizSpreadRecord extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = -7608883814938168292L;

    /**
     *推广的会员Id
     */
    @Column(length = 10)
    private long              spreadMemberId;

    /**
     *相关注册会员Id
     */
    @Column(length = 10)
    private long              registMemberId;
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

    public long getSpreadMemberId() {
        return spreadMemberId;
    }

    public void setSpreadMemberId(long spreadMemberId) {
        this.spreadMemberId = spreadMemberId;
    }

    public long getRegistMemberId() {
        return registMemberId;
    }

    public void setRegistMemberId(long registMemberId) {
        this.registMemberId = registMemberId;
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
