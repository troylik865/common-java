package org.troy.system.entity.main;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.troy.system.entity.IdEntity;


/**
 * 系统：角色模块类
 * @author wangj
 * 2013-5-17
 */
@Entity
@Table(name="sys_role_module")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="org.troy.system.entity.main")
public class SysRoleModule extends IdEntity {
	
	/** 描述  */
	private static final long serialVersionUID = -8888778221379780126L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="roleId")
	private SysRole role;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="moduleId")
	private SysModule module;
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
	 * 返回 module 的值   
	 * @return module  
	 */
	public SysModule getModule() {
		return module;
	}

	/**  
	 * 设置 module 的值  
	 * @param module
	 */
	public void setModule(SysModule module) {
		this.module = module;
	}

	@Override
	public int hashCode() {
		return this.role.hashCode() + this.module.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		 if(obj instanceof SysRoleModule){  
		     SysRoleModule key = (SysRoleModule)obj ;  
            if(this.role.getId().equals(key.getRole().getId()) && this.module.getId().equals(key.getModule().getId())){  
             return true ;  
	        }  
	      }  
	    return false ; 
	}


}
