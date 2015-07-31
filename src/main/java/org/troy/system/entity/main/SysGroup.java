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

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * 系统：组织机构类
 * @author wangj
 * 2013-5-17
 */
@Entity
@Table(name = "sys_group")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="org.troy.system.entity.main")
public class SysGroup extends IdEntity {

	/** 描述  */
	private static final long serialVersionUID = -7324011210610828114L;
	
	/**
	 * 组织机构名称
	 */
	@NotBlank
	@Length(min=1, max=255)
	@Column(nullable=false, length=255)
	private String name;
	
	/**
	 * 组织机构描述
	 */
	@Length(max=255)
	@Column(length=255)
	private String description;
	
	/**
	 * 所属城区编码
	 */
	@Length(max=16)
	@Column(length=16)
	private String areaCode;
	
	/**
	 * 所属街道编码
	 */
	@Length(max=16)
	@Column(length=16)
	private String streetCode;

	/**
	 * 上级菜单
	 */
	@ManyToOne
	@JoinColumn(name="parentId")
	private SysGroup parent;
	
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="parent")
	private List<SysGroup> children = Lists.newArrayList();
	
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="group")
	private List<SysUser> users = Lists.newArrayList();

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
	 * 返回 areaCode 的值   
	 * @return areaCode  
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**  
	 * 设置 areaCode 的值  
	 * @param areaCode
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	/**  
	 * 返回 streetCode 的值   
	 * @return streetCode  
	 */
	public String getStreetCode() {
		return streetCode;
	}

	/**  
	 * 设置 streetCode 的值  
	 * @param streetCode
	 */
	public void setStreetCode(String streetCode) {
		this.streetCode = streetCode;
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
	 * 返回 parent 的值   
	 * @return parent  
	 */
	public SysGroup getParent() {
		return parent;
	}

	/**  
	 * 设置 parent 的值  
	 * @param parent
	 */
	public void setParent(SysGroup parent) {
		this.parent = parent;
	}

	/**  
	 * 返回 children 的值   
	 * @return children  
	 */
	public List<SysGroup> getChildren() {
		return children;
	}

	/**  
	 * 设置 children 的值  
	 * @param children
	 */
	public void setChildren(List<SysGroup> children) {
		this.children = children;
	}

	/**  
	 * 返回 users 的值   
	 * @return users  
	 */
	public List<SysUser> getUsers() {
		return users;
	}

	/**  
	 * 设置 users 的值  
	 * @param users
	 */
	public void setUsers(List<SysUser> users) {
		this.users = users;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(id)
				.addValue(name)
				.addValue(parent == null ? null:parent.getName())
				.addValue(children.size())
				.toString();
	}
}
