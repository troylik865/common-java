package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizLoginHistoryEntity </p> 
 *
 * <p>Description:biz_login_history 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Entity
@Table(name = "biz_login_history")
public class BizLoginHistory extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = 20912800539130457L;

    /**
     *会员ID
     */
    @Column(length = 10)
    private Long              memberId;
    /**
     *登陆时间
     */
    @Column(length = 19)
    private Date              gmtLogin;
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
      * @param gmtLogin the gmtLogin to set
      */
    public void setGmtLogin(Date gmtLogin) {
        this.gmtLogin = gmtLogin;
    }

    /**
      * @return the gmtLogin 
      */
    public Date getGmtLogin() {
        return this.gmtLogin;
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

}
