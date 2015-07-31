package org.troy.system.entity.main;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.troy.system.entity.IdEntity;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * 系统：功能模块类（功能菜单、操作权限）
 * @author wangj
 * 2013-5-17
 */
@Entity
@Table(name="sys_module")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="org.troy.system.entity.main")
public class SysModule extends IdEntity implements Comparable<SysModule> {

	/** 描述  */
	private static final long serialVersionUID = -6926690440815291509L;
	
	/**
	 * 功能模块名称
	 */
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32)
	private String name;
	
	/**
	 * 模块的入口地址
	 */
	@NotBlank
	@Length(min=1, max=255)
	@Column(nullable=false, length=255)
	private String url;
	
	/**
	 * 功能模块描述
	 */
	@Length(max=255)
	@Column(length=255)
	private String description;
	
	/**
	 * 模块的类型（1、显示菜单；0、操作动作）
	 */
	@NotBlank
	@Column(nullable=false, length=1)
	private String type;
	
	/**
	 * 标志符，用于授权action名称（类似action名称module）
	 */
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32)
	private String actions;
	
	/**
	 * 标志符，用于授权method名称（类似method名称save）
	 */
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32)
	private String methods;
	
	/**
	 * 模块的排序号,越小优先级越高
	 */
	@NotNull
	@Range(min=1, max=99)
	@Column(length=2)
	private Integer priority = 99;

	@ManyToOne
	@JoinColumn(name="parentId")
	private SysModule parent;
	
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="parent")
	@OrderBy("priority ASC")
	private List<SysModule> children = Lists.newArrayList();

	@OneToMany(mappedBy="module", cascade=CascadeType.ALL)
	private List<SysRoleModule> roleModules = new ArrayList<SysRoleModule>(0);
	
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
	 * 返回 url 的值   
	 * @return url  
	 */
	public String getUrl() {
		return url;
	}

	/**  
	 * 设置 url 的值  
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

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

	/**  
	 * 返回 parent 的值   
	 * @return parent  
	 */
	public SysModule getParent() {
		return parent;
	}

	/**  
	 * 设置 parent 的值  
	 * @param parent
	 */
	public void setParent(SysModule parent) {
		this.parent = parent;
	}

	/**  
	 * 返回 children 的值   
	 * @return children  
	 */
	public List<SysModule> getChildren() {
		return children;
	}

	/**  
	 * 设置 children 的值  
	 * @param children
	 */
	public void setChildren(List<SysModule> children) {
		this.children = children;
	}

	/**  
	 * 返回 action 的值   
	 * @return action  
	 */
	public String getActions() {
		return actions;
	}

	/**  
	 * 设置 action 的值  
	 * @param action
	 */
	public void setActions(String actions) {
		this.actions = actions;
	}
	
	/**  
	 * 返回 type 的值   
	 * @return type  
	 */
	public String getType() {
		return type;
	}

	/**  
	 * 设置 type 的值  
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**  
	 * 返回 method 的值   
	 * @return method  
	 */
	public String getMethods() {
		return methods;
	}

	/**  
	 * 设置 method 的值  
	 * @param method
	 */
	public void setMethods(String methods) {
		this.methods = methods;
	}

	/**
	 * 返回 roleModules 的值   
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

	/**
	 * 比较器
	 */
	public int compareTo(SysModule m) {
		if (m == null) {
			return -1;
		} else if (m == this) {
			return 0;
		} else if (this.priority < m.getPriority()) {
			return -1;
		} else if (this.priority > m.getPriority()) {
			return 1;
		}

		return 0;	
	}
	
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(id)
				.addValue(name)
				.addValue(parent == null ? null:parent.getName())
				.addValue(children.size())
				.toString();
	}
}
