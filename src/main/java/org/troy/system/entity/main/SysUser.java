package org.troy.system.entity.main;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.troy.system.entity.IdEntity;

import com.google.common.collect.Lists;

/**
 * 系统：用户类
 * @author wangj
 * 2013-5-17
 */
@Entity
@Table(name="sys_user")
//默认的缓存策略.
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="org.troy.system.entity.main")
public class SysUser extends IdEntity{

	/** 描述  */
	private static final long serialVersionUID = -4277639149589431277L;
	
	/**
	 * 真实姓名
	 */
	@NotBlank
	@Length(min=1, max=64)
	@Column(nullable=false, length=64, updatable=false)
	private String realname;

	/**
	 * 登录名
	 */
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32, unique=true, updatable=false)
	private String username;
	
	/**
	 * MD5(明文密码+随机码)
	 */
	@Column(nullable=false, length=64)
	private String password;
	
	/**
	 * 明文密码
	 */
	@Transient
	private String plainPassword;
	
	/**
	 * 随机码
	 */
	@Column(nullable=false, length=32)
	private String salt;
	
	/**
	 * 电话号码
	 */
	@Length(max=32)
	@Column(length=32)
	private String phone;
	
	/**
	 * 电子邮箱
	 */
	@Email
	@Length(max=128)
	@Column(length=128)
	private String email;
	
	/**
	 * 帐号创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date createTime;
	
	/**
	 * 使用状态disabled，enabled
	 */
	@NotBlank
	@Length(max=16)
	@Column(nullable=false, length=16)
	private String status = "enabled";
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	@OrderBy("priority ASC")
	private List<SysUserRole> userRoles = Lists.newArrayList();
	
	@ManyToOne
	@JoinColumn(name="groupId")
	private SysGroup group;

	/**  
	 * 返回 realname 的值   
	 * @return realname  
	 */
	public String getRealname() {
		return realname;
	}

	/**  
	 * 设置 realname 的值  
	 * @param realname
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}

	/**  
	 * 返回 username 的值   
	 * @return username  
	 */
	public String getUsername() {
		return username;
	}

	/**  
	 * 设置 username 的值  
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**  
	 * 返回 password 的值   
	 * @return password  
	 */
	public String getPassword() {
		return password;
	}

	/**  
	 * 设置 password 的值  
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**  
	 * 返回 createTime 的值   
	 * @return createTime  
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**  
	 * 设置 createTime 的值  
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**  
	 * 返回 status 的值   
	 * @return status  
	 */
	public String getStatus() {
		return status;
	}

	/**  
	 * 设置 status 的值  
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**  
	 * 返回 plainPassword 的值   
	 * @return plainPassword  
	 */
	public String getPlainPassword() {
		return plainPassword;
	}

	/**  
	 * 设置 plainPassword 的值  
	 * @param plainPassword
	 */
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	/**  
	 * 返回 salt 的值   
	 * @return salt  
	 */
	public String getSalt() {
		return salt;
	}

	/**  
	 * 设置 salt 的值  
	 * @param salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	/**  
	 * 返回 email 的值   
	 * @return email  
	 */
	public String getEmail() {
		return email;
	}

	/**  
	 * 设置 email 的值  
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * 返回 phone 的值   
	 * @return phone  
	 */
	public String getPhone() {
		return phone;
	}

	/**  
	 * 设置 phone 的值  
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**  
	 * 返回 group 的值   
	 * @return group  
	 */
	public SysGroup getGroup() {
		return group;
	}

	/**  
	 * 设置 group 的值  
	 * @param group
	 */
	public void setGroup(SysGroup group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
