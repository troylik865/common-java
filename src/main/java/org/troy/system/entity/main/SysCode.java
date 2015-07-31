package org.troy.system.entity.main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.troy.system.entity.IdEntity;

/**
 * 系统：数据字典类
 * @author wangj
 * 2013-5-17
 */
@Entity
@Table(name="sys_code")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="org.troy.system.entity.main")
public class SysCode extends IdEntity {
	
	/** 描述  */
	private static final long serialVersionUID = -5537665695891354775L;

	/**
	 * 代码类别
	 */
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32)
	private String codeType;
	
	/**
	 * 类别名称
	 */
	@Length(max=255)
	@Column(length=255)
	private String typeName;

	/**
	 * 代码值
	 */
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32)
	private String codeValue;
	
	/**
	 * 代码名称
	 */
	@Length(max=255)
	@Column(length=255)
	private String codeName;

	/**
	 * 代码描述
	 */
	@Length(max=255)
	@Column(length=255)
	private String codeDesc;

	/**
	 * 状态（0正常1停用）
	 */
	@NotBlank
	@Length(max=1)
	@Column(length=1)
	private String codeState;
	
	/**
	 * 序号
	 */
	@Range(min=0, max=99)
	@Column(length=10)
	private Integer ordNo;

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public String getCodeState() {
		return codeState;
	}

	public void setCodeState(String codeState) {
		this.codeState = codeState;
	}

	public Integer getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(Integer ordNo) {
		this.ordNo = ordNo;
	}

}
