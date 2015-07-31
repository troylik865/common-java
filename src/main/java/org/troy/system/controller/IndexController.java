package org.troy.system.controller;

import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.work.util.LogConst;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogLevel;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.system.entity.main.SysModule;
import org.troy.system.entity.main.SysRoleModule;
import org.troy.system.entity.main.SysUser;
import org.troy.system.entity.main.SysUserRole;
import org.troy.system.service.ModuleService;
import org.troy.system.service.RoleModuleService;
import org.troy.system.service.UserRoleService;
import org.troy.system.service.UserService;
import org.troy.system.shiro.ShiroDbRealm;
import org.troy.system.util.SysConst;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 系统后台首页Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/system/index")
public class IndexController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private RoleModuleService roleModuleService;
	
	private static final String INDEX = "system/index/index";
	private static final String UPDATE_PASSWORD = "system/index/updatePwd";
	private static final String UPDATE_BASE = "system/index/updateBase";
	
	@Log(message=LogConst.Handle_Login+LogConst.LOG_SPLIT+"{0}登录了系统。", level=LogLevel.LOGON)
	@RequiresUser
	@RequestMapping(value="", method={RequestMethod.GET, RequestMethod.POST})
	public String index() {
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser)subject.getPrincipal();
		// 加入ipAddress
		shiroUser.setIpAddress(request.getRemoteAddr());
		//User user = userService.get(shiroUser.getLoginName());
		List<SysUserRole> userRoles = userRoleService.find(shiroUser.getId());
		shiroUser.getUser().setUserRoles(userRoles);
		
		SysModule menuModule = getMenuModule(userRoles);

		// 这个是放入user还是shiroUser呢？
		request.getSession().setAttribute(SysConst.LOGIN_USER, shiroUser.getUser());
		request.setAttribute("menuModule", menuModule);
		
		//记录日志	
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{shiroUser.getUser().getRealname()}));
		
		return INDEX;
	}
	
	private SysModule getMenuModule(List<SysUserRole> userRoles) {
		// 得到所有权限
		Set<String> permissionSet = Sets.newHashSet();
		for (SysUserRole userRole : userRoles) {
			List<SysRoleModule> roleModules = roleModuleService.find(userRole.getRole().getId());
			if(!roleModules.isEmpty()){
				for (SysRoleModule roleModule : roleModules) {
					permissionSet.add(roleModule.getModule().getActions()+":"+roleModule.getModule().getMethods());
				}
			}
		} 
		
		// 组装菜单,获取二级、三级菜单
		SysModule rootModule = moduleService.getTree();
		List<SysModule> list1 = Lists.newArrayList();
		for (SysModule m1 : rootModule.getChildren()) {
			// 只加入拥有view权限的Module
			if (permissionSet.contains(m1.getActions() + ":" + m1.getMethods())) {
				List<SysModule> list2 = Lists.newArrayList();
				for (SysModule m2 : m1.getChildren()) {
					if (permissionSet.contains(m2.getActions() + ":" + m2.getMethods())) {
						List<SysModule> list3 = Lists.newArrayList();
						for (SysModule m3 : m2.getChildren()) {
							if (permissionSet.contains(m3.getActions() + ":" + m3.getMethods())) {
								list3.add(m3);
							}
						}
						m2.setChildren(list3);
						list2.add(m2);
					}
				}
				m1.setChildren(list2);
				list1.add(m1);
			}
		}
		rootModule.setChildren(list1);
		
		return rootModule;
	}
	
	
	@RequestMapping(value="/updatePwd", method=RequestMethod.GET)
	public String updatePassword() {
		return UPDATE_PASSWORD;
	}
	
	@RequestMapping(value="/updatePwd", method=RequestMethod.POST)
	public @ResponseBody String updatePassword(String oldPassword, 
			String plainPassword, String rPassword) {
		SysUser user = (SysUser)request.getSession().getAttribute(SysConst.LOGIN_USER);
		
		if (plainPassword.equals(rPassword)) {
			user.setPlainPassword(plainPassword);
			userService.update(user);
			
			LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
			AjaxObject ajaxObject = new AjaxObject("密码修改成功！");
			return ajaxObject.toString();
		}
		
		AjaxObject ajaxObject = new AjaxObject("密码修改失败！");
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
		ajaxObject.setCallbackType("");
		
		return ajaxObject.toString();
	}
	
	@RequestMapping(value="/updateBase", method=RequestMethod.GET)
	public String preUpdate() {
		return UPDATE_BASE;
	}
	
	@RequestMapping(value="/updateBase", method=RequestMethod.POST)
	public @ResponseBody String update(SysUser user) {
		SysUser loginUser = (SysUser)request.getSession().getAttribute(SysConst.LOGIN_USER);
		
		loginUser.setPhone(user.getPhone());
		loginUser.setEmail(user.getEmail());

		userService.update(loginUser);

		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
		AjaxObject ajaxObject = new AjaxObject("详细信息修改成功！");
		return ajaxObject.toString();
	}
}
