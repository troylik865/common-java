package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizMemberRankEntity </p> 
 *
 * <p>Description:biz_member_rank 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Entity
@Table(name = "biz_member_rank")
public class BizMemberRank extends IdEntity {

    /** 序列化Id */
    private static final long serialVersionUID = -3379839373883173462L;

    /**
     *会员ID
     */
    @Column(length = 10)
    private long              memberId;

    /**
     *排名
     */
    @Column(length = 10)
    private Integer           rank;
    /**
     *扩展字段
     */
    @Column(length = 200)
    private String            extendField;

    /**
     * 投资方向
     */
    @Column(length = 20)
    private String            investDirection;

    /**
     * 大师评价
     */
    @Column(length = 400)
    private String            rankDesc;

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
      * @param rank the rank to set
      */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
      * @return the rank 
      */
    public Integer getRank() {
        return this.rank;
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

    public String getInvestDirection() {
        return investDirection;
    }

    public void setInvestDirection(String investDirection) {
        this.investDirection = investDirection;
    }

    public String getRankDesc() {
        return rankDesc;
    }

    public void setRankDesc(String rankDesc) {
        this.rankDesc = rankDesc;
    }

}
