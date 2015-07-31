package org.troy.system.entity.main;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.troy.system.entity.IdEntity;

import com.google.common.collect.Lists;

/***
 * <p>Title:系统：行政区域类 </p> 
 *
 * <p>Description:行政区划</p> 
 *
 * <p>Author:wangj </p> 
 *
 * <p>Copyright: Copyright (c) 2013 </p>
 * 
 * <p>CreateDate: 2013-06-21 09:59 </p>
 *
 */
@Entity
@Table(name="sys_area")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="org.troy.system.entity.main")
public class SysArea extends IdEntity{
 
     /** 描述  */
     private static final long serialVersionUID = -5537665695891354775L;

	/**
	 *区域编号
	 */
    @NotBlank
 	@Length(min=1, max=16)
    @Column( length=16)
    private String areaCode;
	/**
	 *区域名称
	 */
    @NotBlank
	@Length(min=1, max=50)
    @Column( length=50)
    private String areaName;
	/**
	 *父级区域id
	 */
    @ManyToOne
    @JoinColumn(name="parentId")
    private SysArea parent;
	/**
	 *级别:1、区域；2、街道
	 */
    @Column( length=1)
    private String areaLevel;
	/**
	 *备注
	 */
    @Length(max=255)
    @Column( length=255)
    private String remark;
	/**
	 *消费点代码
	 */
    @Length(max=8)
    @Column( length=8)
    private String consumeCode;
    
    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="parent")
	private List<SysArea> children = Lists.newArrayList();
     /**
       * @param areaCode the areaCode to set
       */
    public void setAreaCode(String areaCode){
       this.areaCode = areaCode;
    }
    
    /**
      * @return the areaCode 
      */
    public String getAreaCode(){
       return this.areaCode ;
    }
     /**
       * @param areaName the areaName to set
       */
    public void setAreaName(String areaName){
       this.areaName = areaName;
    }
    
    /**
      * @return the areaName 
      */
    public String getAreaName(){
       return this.areaName ;
    }
    /**  
	 * 返回 parent 的值   
	 * @return parent  
	 */
	public SysArea getParent() {
		return parent;
	}

	/**  
	 * 设置 parent 的值  
	 * @param parent
	 */
	public void setParent(SysArea parent) {
		this.parent = parent;
	}

	/**  
	 * 返回 children 的值   
	 * @return children  
	 */
	public List<SysArea> getChildren() {
		return children;
	}

	/**  
	 * 设置 children 的值  
	 * @param children
	 */
	public void setChildren(List<SysArea> children) {
		this.children = children;
	}
     /**
       * @param areaLevel the areaLevel to set
       */
    public void setAreaLevel(String areaLevel){
       this.areaLevel = areaLevel;
    }
    
    /**
      * @return the areaLevel 
      */
    public String getAreaLevel(){
       return this.areaLevel ;
    }
     /**
       * @param remark the remark to set
       */
    public void setRemark(String remark){
       this.remark = remark;
    }
    
    /**
      * @return the remark 
      */
    public String getRemark(){
       return this.remark ;
    }
     /**
       * @param consumeCode the consumeCode to set
       */
    public void setConsumeCode(String consumeCode){
       this.consumeCode = consumeCode;
    }
    
    /**
      * @return the consumeCode 
      */
    public String getConsumeCode(){
       return this.consumeCode ;
    }
	
}
