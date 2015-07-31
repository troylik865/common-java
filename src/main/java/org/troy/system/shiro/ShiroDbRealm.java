package org.troy.system.shiro;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.DigestUtils;
import org.troy.common.utils.Digests;
import org.troy.common.utils.Encodes;
import org.troy.system.entity.main.SysRoleModule;
import org.troy.system.entity.main.SysUser;
import org.troy.system.entity.main.SysUserRole;
import org.troy.system.service.RoleModuleService;
import org.troy.system.service.UserRoleService;
import org.troy.system.service.UserService;

import com.google.common.collect.Lists;

/**
 * 自定义权限认证
 * 
 * @author wangj
 * 2013-5-17
 */

public class ShiroDbRealm extends AuthorizingRealm {
	private static final int SALT_SIZE = 8;

	protected boolean useCaptcha = false;
	
	protected UserService userService;
	
	protected UserRoleService userRoleService;
	
	protected RoleModuleService roleModuleService;
	
	/**
	 * 给ShiroDbRealm提供编码信息，用于密码密码比对
	 * 描述
	 */	
	public ShiroDbRealm() {
		super();
		setCredentialsMatcher(new CustomCredentialsMatcher());
	}
	
	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		if (useCaptcha) {
			CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
			
			String parm = token.getCaptcha();
			String c = (String)SecurityUtils.getSubject().getSession()
					.getAttribute(SimpleCaptchaServlet.CAPTCHA_KEY);
			// 忽略大小写
			if (!parm.equalsIgnoreCase(c)) {
				throw new IncorrectCaptchaException("验证码错误！"); 
			} 
		} 
		
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		SysUser user = userService.get(token.getUsername());
		if (user != null) {
			if (user.getStatus().equals("disabled")) {
				throw new DisabledAccountException();
			}
            ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUsername(), user);
			if(StringUtils.isNotBlank(user.getSalt())){
                byte[] salt = Encodes.decodeHex(user.getSalt());
                // 这里可以缓存认证
                return new SimpleAuthenticationInfo(shiroUser, user.getPassword(),ByteSource.Util.bytes(salt), getName());
            } else {
                return new SimpleAuthenticationInfo(shiroUser, user.getPassword(),getName());
            }
		} else {
			return null;
		}
		
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName()).iterator().next();
		List<SysUserRole> userRoles = userRoleService.find(shiroUser.getId());
		shiroUser.getUser().setUserRoles(userRoles);
		
		if (!userRoles.isEmpty()) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (SysUserRole userRole : userRoles) {
				List<SysRoleModule> roleModules = roleModuleService.find(userRole.getRole().getId());
				List<String> permissionList = Lists.newArrayList();
				if(!roleModules.isEmpty()){
					for (SysRoleModule roleModule : roleModules) {
						permissionList.add(roleModule.getModule().getActions()+":"+roleModule.getModule().getMethods());
					}
				}
				//基于Permission的权限信息
				info.addStringPermissions(permissionList);
			}
			return info;
		} else {
			return null;
		}
	}
	
	public static class HashPassword {
		public String salt;
		public String password;
	}
	
	public HashPassword encrypt(String plainText) {
		HashPassword result = new HashPassword();
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		result.salt = Encodes.encodeHex(salt);
		result.password = DigestUtils.md5DigestAsHex((plainText+result.salt.toString()).getBytes());
		return result;

	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	
	/**  
	 * 设置 useCaptcha 的值  
	 * @param useCaptcha
	 */
	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**  
	 * 设置 userRoleService 的值  
	 * @param userRoleService
	 */
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	/**  
	 * 设置 roleModuleService 的值  
	 * @param roleModuleService
	 */
	public void setRoleModuleService(RoleModuleService roleModuleService) {
		this.roleModuleService = roleModuleService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {

		private static final long serialVersionUID = -1748602382963711884L;
		private Long id;
		private String loginName;
		private String ipAddress;
		private SysUser user;

		/**  
		 * 构造函数
		 * @param id
		 * @param loginName
		 */
		public ShiroUser(Long id, String loginName, SysUser user) {
			this.id = id;
			this.loginName = loginName;
			this.user = user;
		}

		/**  
		 * 返回 id 的值   
		 * @return id  
		 */
		public Long getId() {
			return id;
		}

		/**  
		 * 返回 loginName 的值   
		 * @return loginName  
		 */
		public String getLoginName() {
			return loginName;
		}

		public String getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}
		
		/**  
		 * 返回 user 的值   
		 * @return user  
		 */
		public SysUser getUser() {
			return user;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}
	}
}
