package org.troy.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.troy.system.entity.IdEntity;

/***
 * <p>Title:BizColumnListEntity </p> 
 *
 * <p>Description:biz_column_list 实体类</p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
@Entity
@Table(name="biz_column_list")
public class BizColumnList extends IdEntity{
 
    /** 序列化Id */
    private static final long serialVersionUID = -9041596401436354664L;
    
    /**
	 *栏目名称
	 */
    @Column( length=80)
    private String itemName;
	/**
	 *栏目类型
	 */
    @Column( length=10)
    private String itemType;
	/**
	 *显示位置
	 */
    @Column( length=20)
    private String showPosition;
	/**
	 *栏目内容类型   能下载的附件/超链接
	 */
    @Column( length=10)
    private String itemContentType;
	/**
	 *栏目内容 如果是类目类型是 1. 超链接： 超链接的链接地址 2. 附件：  附件ID
	 */
    @Column( length=200)
    private String itemContent;
	/**
	 *排序
	 */
    @Column( length=10)
    private Integer rank;
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
       * @param itemName the itemName to set
       */
    public void setItemName(String itemName){
       this.itemName = itemName;
    }
    
    /**
      * @return the itemName 
      */
    public String getItemName(){
       return this.itemName ;
    }
     /**
       * @param itemType the itemType to set
       */
    public void setItemType(String itemType){
       this.itemType = itemType;
    }
    
    /**
      * @return the itemType 
      */
    public String getItemType(){
       return this.itemType ;
    }
     /**
       * @param showPosition the showPosition to set
       */
    public void setShowPosition(String showPosition){
       this.showPosition = showPosition;
    }
    
    /**
      * @return the showPosition 
      */
    public String getShowPosition(){
       return this.showPosition ;
    }
     /**
       * @param itemContentType the itemContentType to set
       */
    public void setItemContentType(String itemContentType){
       this.itemContentType = itemContentType;
    }
    
    /**
      * @return the itemContentType 
      */
    public String getItemContentType(){
       return this.itemContentType ;
    }
     /**
       * @param itemContent the itemContent to set
       */
    public void setItemContent(String itemContent){
       this.itemContent = itemContent;
    }
    
    /**
      * @return the itemContent 
      */
    public String getItemContent(){
       return this.itemContent ;
    }
     /**
       * @param rank the rank to set
       */
    public void setRank(Integer rank){
       this.rank = rank;
    }
    
    /**
      * @return the rank 
      */
    public Integer getRank(){
       return this.rank ;
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
