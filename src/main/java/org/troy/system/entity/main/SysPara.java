package  org.troy.system.entity.main;  

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.troy.system.entity.IdEntity;
/***
 * <p>Title:系统参数 </p> 
 *
 * <p>Description:系统参数 </p> 
 *
 * <p>Author:wangj </p> 
 *
 * <p>Copyright: Copyright (c) 2013 </p>
 * 
 * <p>CreateDate: 2013-06-13 14:10 </p>
 *
 */
@Entity
@Table(name="sys_para")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="org.troy.system.entity.main")
public class SysPara extends IdEntity{

	/** 描述  */
	private static final long serialVersionUID = -5537665695891354775L;
	
	/**
	 * 参数关键字
	 */
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false,length=32)
	private  String paraKey;
	
	/**
	 * 参数值
	 */
	@NotBlank
	@Length(min=1,max=1024)
	@Column(nullable=false,length=1024)
	private  String paraValue;
	
	/**
	 * 参数名称
	 */
	@Length(max=32)
	@Column(length=32)
	private  String paraName;
	
	/**
	 * 参数描述
	 */
	@Length(max=128)
	@Column(length=128)
	private  String paraDesc;
	
	/**
	 * 状态（0正常1停用）
	 */
	@NotBlank
	@Length(max=1)
	@Column(length=1)
	private String paraState;
	/**
	 * @return the paraKey
	 */
	public String getParaKey() {
		return paraKey;
	}
	/**
	 * @param paraKey the paraKey to set
	 */
	public void setParaKey(String paraKey) {
		this.paraKey = paraKey;
	}
	/**
	 * @return the paraValue
	 */
	public String getParaValue() {
		return paraValue;
	}
	/**
	 * @param paraValue the paraValue to set
	 */
	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	/**
	 * @return the paraName
	 */
	public String getParaName() {
		return paraName;
	}
	/**
	 * @param paraName the paraName to set
	 */
	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	/**
	 * @return the paraDesc
	 */
	public String getParaDesc() {
		return paraDesc;
	}
	/**
	 * @param paraDesc the paraDesc to set
	 */
	public void setParaDesc(String paraDesc) {
		this.paraDesc = paraDesc;
	}
	
	public String getParaState() {
		return paraState;
	}
	
	public void setParaState(String paraState) {
		this.paraState = paraState;
	}

}

