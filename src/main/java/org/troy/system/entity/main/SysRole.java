package org.troy.system.entity.main;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.troy.system.entity.IdEntity;

/**
 * 系统：角色类
 * @author wangj
 * 2013-5-17
 */
@Entity
@Table(name="sys_role")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="org.troy.system.entity.main")
public class SysRole extends IdEntity {
	
	/** 描述  */
	private static final long serialVersionUID = -5537665695891354775L;

	/**
	 * 角色描述
	 */
	@Length(max=255)
	@Column(length=255)
	private String description;
	
	/**
	 * 角色名称
	 */
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32)
	private String name;
	
	@OneToMany(mappedBy="role", cascade=CascadeType.ALL)
	private List<SysUserRole> userRoles = new ArrayList<SysUserRole>(0);
	
	@OneToMany(mappedBy="role", cascade=CascadeType.ALL)
	private List<SysRoleModule> roleModules = new ArrayList<SysRoleModule>(0);
	/**  
	 * 返回 description 的值   
	 * @return description  
	 */
	public String getDescription() {
		return description;
	}

	/**  
	 * 设置 description 的值  
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**  
	 * 返回 name 的值   
	 * @return name  
	 */
	public String getName() {
		return name;
	}

	/**  
	 * 设置 name 的值  
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**  
	 * 返回 userRoles 的值   
	 * @return userRoles  
	 */
	public List<SysUserRole> getUserRoles() {
		return userRoles;
	}

	/**  
	 * 设置 userRoles 的值  
	 * @param userRoles
	 */
	public void setUserRoles(List<SysUserRole> userRoles) {
		this.userRoles = userRoles;
	}

	/**
	 * 返回roleModules 的值   
	 * @return roleModules
	 */
	public List<SysRoleModule> getRoleModules() {
		return roleModules;
	}

	/**
	 * 设置 roleModules 的值  
	 * @param roleModules
	 */
	public void setRoleModules(List<SysRoleModule> roleModules) {
		this.roleModules = roleModules;
	}

	
}
