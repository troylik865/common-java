package org.troy.system.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.common.component.beanvalidator.BeanValidators;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysRole;
import org.troy.system.entity.main.SysUser;
import org.troy.system.entity.main.SysUserRole;
import org.troy.system.service.RoleService;
import org.troy.system.service.UserRoleService;
import org.troy.system.service.UserService;

import com.google.common.collect.Lists;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 系统用户Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/system/security/user")
public class UserController extends BaseController{

	@Autowired
	private UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private Validator validator;
	
	private static final String CREATE = "system/security/user/create";
	private static final String UPDATE = "system/security/user/update";
	private static final String LIST = "system/security/user/list";
	private static final String LOOK_UP_ROLE = "system/security/user/assign_role";
	private static final String LOOK_USER_ROLE = "system/security/user/delete_user_role";
	
	@RequiresPermissions("User:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate() {
		return CREATE;
	}
	
	@RequiresPermissions("User:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(SysUser user) {	
		evenName = "用户添加";
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, user);
				
		user.setCreateTime(new Date());
		
		userService.save(user);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));

		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		return ajaxObject.toString();
	}
	
	@ModelAttribute("preloadUser")
	public SysUser getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			SysUser user = userService.get(id);
			user.setGroup(null);
			return user;
		}
		return null;
	}
	
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		SysUser user = userService.get(id);
		
		map.put("user", user);
		return UPDATE;
	}
	
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@ModelAttribute("preloadUser")SysUser user) {
		evenName = "用户修改";
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, user);

		userService.update(user);
		//记录日志	
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
		
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("User:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		evenName = "用户删除";
		SysUser user = null;
		user = userService.get(id);
		userService.delete(id);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername()}));
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("User:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		evenName = "用户删除";
		String[] usernames = new String[ids.length];
		for (int i = 0; i < ids.length; i++) {
			SysUser user = userService.get(ids[i]);
			userService.delete(user.getId());
			usernames[i] = user.getUsername();
		}
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(usernames)}));

		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	@RequiresPermissions("User:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, String keywords, Map<String, Object> map) {
		List<SysUser> users;
		if (StringUtils.isNotBlank(keywords)) {
			users = userService.find(page, keywords);
		} else {
			users = userService.findAll(page);
		}
		
		map.put("page", page);
		map.put("users", users);
		map.put("keywords", keywords);
		return LIST;
	}
	
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/reset/{type}/{userId}", method=RequestMethod.POST)
	public @ResponseBody String reset(@PathVariable String type, @PathVariable Long userId) {
		SysUser user = userService.get(userId);
		AjaxObject ajaxObject = new AjaxObject();
		if (type.equals("password")) {
			user.setPlainPassword("123456");
			
			ajaxObject.setMessage("重置密码成功，默认密码为123456！"); 
		} else if (type.equals("status")) {
			if (user.getStatus().equals("enabled")) {
				user.setStatus("disabled");
			} else {
				user.setStatus("enabled");
			}
			
			ajaxObject.setMessage("更新状态为" + user.getStatus());
		}
		
		userService.update(user);
		ajaxObject.setCallbackType("");
		
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername(), ajaxObject.getMessage()}));
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("User:save")
	@RequestMapping(value="/create/userRole", method={RequestMethod.POST})
	public @ResponseBody void assignRole(SysUserRole userRole) {
		userRoleService.save(userRole);
		
		SysUser user = userService.get(userRole.getUser().getId());
		SysRole role = roleService.get(userRole.getRole().getId());
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUsername(), role.getName()}));
	}
	
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/lookup2create/userRole/{userId}", method={RequestMethod.GET, RequestMethod.POST})
	public String listUnassignRole(Map<String, Object> map, @PathVariable Long userId) {
		Page page = new Page();
		page.setNumPerPage(Integer.MAX_VALUE);
		
		List<SysUserRole> userRoles = userRoleService.find(userId);
		List<SysRole> roles = roleService.findAll(page);
		
		List<SysRole> hasList = Lists.newArrayList();
		List<SysRole> allRoles = Lists.newArrayList(roles);
		// 删除已分配roles
		for (SysUserRole ur : userRoles) {
			for (SysRole role : roles) {
				if (ur.getRole().getId().equals(role.getId())) {
					hasList.add(role);
					break;
				}
			}
		}
		
		allRoles.removeAll(hasList);
		
		map.put("userRoles", userRoles);
		map.put("roles", allRoles);
		
		map.put("userId", userId);
		return LOOK_UP_ROLE;
	}
	
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/lookup2delete/userRole/{userId}", method={RequestMethod.GET, RequestMethod.POST})
	public String listUserRole(Map<String, Object> map, @PathVariable Long userId) {
		List<SysUserRole> userRoles = userRoleService.find(userId);
		map.put("userRoles", userRoles);
		return LOOK_USER_ROLE;
	}
	
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/delete/userRole/{userRoleId}", method={RequestMethod.POST})
	public @ResponseBody void deleteUserRole(@PathVariable Long userRoleId) {
		SysUserRole userRole = userRoleService.get(userRoleId);
		String userName = userRole.getUser().getUsername();
		String roleName = userRole.getRole().getName();
		
		userRoleService.delete(userRoleId);
		
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{userName, roleName}));
		
	}
	
	@RequestMapping("exportExcel")
	public String exportExcel(HttpServletResponse response,
			String type,Page page, String keywords, Map<String, Object> map) {
		List<SysUser> users;
		if("all".equalsIgnoreCase(type)){//全导出
			users = userService.findAll();
		}else{
			if (StringUtils.isNotBlank(keywords)) {
				users = userService.find(page, keywords);
			} else {
				users = userService.findAll(page);
			}
		}
		Map<String,Object> root = new HashMap<String,Object>();
		Writer out = null;
		root.put("users", users);
		Template t;
		try {
			t = cfg.getTemplate("users.ftl");
		     //使用模板文件的charset作为本页面的charset
		    response.setContentType("text/html; charset="+t.getEncoding());
		    String filename = "用户管理";
		    response.setHeader("Content-disposition","attachment; filename="+java.net.URLEncoder.encode(filename, "UTF-8")+".xls");  

			 //合并数据和模板，并将结果输出到out中
		    out = response.getWriter();
			 
			t.process(root, out); 
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return null;
	}
}
