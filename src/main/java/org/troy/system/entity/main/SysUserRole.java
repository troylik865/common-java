package org.troy.system.entity.main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;
import org.troy.system.entity.IdEntity;

/**
 * 系统：用户角色类
 * @author wangj
 * 2013-5-17
 */
@Entity
@Table(name="sys_user_role")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="org.troy.system.entity.main")
public class SysUserRole extends IdEntity {
	
	/** 描述  */
	private static final long serialVersionUID = -2888778227379780116L;
	
	/**
	 * 值越小，优先级越高
	 */
	@NotNull
	@Range(min=1, max=99)
	@Column(length=2, nullable=false)
	private Integer priority = 99;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="roleId")
	private SysRole role;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	private SysUser user;
	/**  
	 * 返回 role 的值   
	 * @return role  
	 */
	public SysRole getRole() {
		return role;
	}

	/**  
	 * 设置 role 的值  
	 * @param role
	 */
	public void setRole(SysRole role) {
		this.role = role;
	}

	/**  
	 * 返回 user 的值   
	 * @return user  
	 */
	public SysUser getUser() {
		return user;
	}

	/**  
	 * 设置 user 的值  
	 * @param user
	 */
	public void setUser(SysUser user) {
		this.user = user;
	}

	/**  
	 * 返回 priority 的值   
	 * @return priority  
	 */
	public Integer getPriority() {
		return priority;
	}

	/**  
	 * 设置 priority 的值  
	 * @param priority
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}
