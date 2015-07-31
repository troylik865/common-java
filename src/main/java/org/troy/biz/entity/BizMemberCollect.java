package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/**
 * 
 * 大师收藏的表
 *
 *
 * @author siren.lb
 * @version $Id: BizMemberCollect.java,v 0.1 2014年9月14日 下午11:58:10 siren.lb Exp $
 */
@Entity
@Table(name = "biz_member_collect")
public class BizMemberCollect extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = -8965441110215805558L;

    @Column(length = 11)
    private long              memberId;

    @Column(length = 11)
    private long              collectedMemberId;

    /**
     * 扩展信息
     */
    @Column(length = 200)
    private String            extendField;

    /**
     * 创建时间
     */
    private Date              gmtCreate;

    /**
     * 修改时间
     */
    private Date              gmtModified;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getCollectedMemberId() {
        return collectedMemberId;
    }

    public void setCollectedMemberId(long collectedMemberId) {
        this.collectedMemberId = collectedMemberId;
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
