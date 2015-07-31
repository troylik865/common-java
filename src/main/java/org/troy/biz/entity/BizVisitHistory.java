package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizVisitHistoryEntity </p> 
 *
 * <p>Description:biz_visit_history 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
@Entity
@Table(name="biz_visit_history")
public class BizVisitHistory extends IdEntity{
    
    /** 序列化Id */
    private static final long serialVersionUID = 4157704651978311098L;
    
    /**
	 *会员ID
	 */
    @Column( length=10)
    private long memberId;
	/**
	 *被关注的大师记录
	 */
    @Column( length=10)
    private long attentedMemberId;
	/**
	 *访问时间
	 */
    @Column( length=19)
    private Date gmtVisit;
	/**
	 *扩展字段
	 */
    @Column( length=200)
    private String extendField;
	/**
	 *创建时间
	 */
    @Column( length=19)
    private Date gmtCreate;
	/**
	 *修改时间
	 */
    @Column( length=19)
    private Date gmtModified;

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
    public void setMemberId(long memberId){
       this.memberId = memberId;
    }
    
    /**
      * @return the memberId 
      */
    public long getMemberId(){
       return this.memberId ;
    }
     /**
       * @param attentedMemberId the attentedMemberId to set
       */
    public void setAttentedMemberId(long attentedMemberId){
       this.attentedMemberId = attentedMemberId;
    }
    
    /**
      * @return the attentedMemberId 
      */
    public long getAttentedMemberId(){
       return this.attentedMemberId ;
    }
     /**
       * @param gmtVisit the gmtVisit to set
       */
    public void setGmtVisit(Date gmtVisit){
       this.gmtVisit = gmtVisit;
    }
    
    /**
      * @return the gmtVisit 
      */
    public Date getGmtVisit(){
       return this.gmtVisit ;
    }
     /**
       * @param extendField the extendField to set
       */
    public void setExtendField(String extendField){
       this.extendField = extendField;
    }
    
    /**
      * @return the extendField 
      */
    public String getExtendField(){
       return this.extendField ;
    }
     /**
       * @param gmtCreate the gmtCreate to set
       */
    public void setGmtCreate(Date gmtCreate){
       this.gmtCreate = gmtCreate;
    }
    
    /**
      * @return the gmtCreate 
      */
    public Date getGmtCreate(){
       return this.gmtCreate ;
    }
     /**
       * @param gmtModified the gmtModified to set
       */
    public void setGmtModified(Date gmtModified){
       this.gmtModified = gmtModified;
    }
    
    /**
      * @return the gmtModified 
      */
    public Date getGmtModified(){
       return this.gmtModified ;
    }
	
}
