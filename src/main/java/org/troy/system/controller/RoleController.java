package org.troy.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.common.component.beanvalidator.BeanValidators;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysModule;
import org.troy.system.entity.main.SysRole;
import org.troy.system.entity.main.SysRoleModule;
import org.troy.system.service.ModuleService;
import org.troy.system.service.RoleModuleService;
import org.troy.system.service.RoleService;
import org.troy.system.util.SysConst;

import com.google.common.collect.Sets;

/**
 * 角色Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/system/security/role")
public class RoleController extends BaseController{

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private RoleModuleService roleModuleService;
	
	@Autowired
	private Validator validator;
	
	private static final String CREATE = "system/security/role/create";
	private static final String UPDATE = "system/security/role/update";
	private static final String LIST = "system/security/role/list";
	
	@RequiresPermissions("Role:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		Set<String> permissionSet = Sets.newHashSet();
		String treejsons =  getTreeJson(moduleService.getTree(),permissionSet);
		map.put(SysConst.TREE_JSON,treejsons.substring(0, treejsons.length()-1));
		return CREATE;
	}
	
	@RequiresPermissions("Role:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(SysRole role,String moduleIds) {
		evenName = "角色添加";
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, role);
				
		refactor(role,moduleIds);
		roleService.save(role);
		
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{role.getName()}));
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Role:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		SysRole role = roleService.get(id);
		
		// 得到所有权限
		Set<String> permissionSet = Sets.newHashSet();
		List<SysRoleModule> roleModules = roleModuleService.find(role.getId());
		if(!roleModules.isEmpty()){
			for (SysRoleModule roleModule : roleModules) {
				permissionSet.add(roleModule.getModule().getActions()+":"+roleModule.getModule().getMethods());
			}
		}
		String treejsons =  getTreeJson(moduleService.getTree(),permissionSet);
		map.put(SysConst.TREE_JSON,treejsons.substring(0, treejsons.length()-1));
		map.put("role", role);
		return UPDATE;
	}
	
	@RequiresPermissions("Role:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(SysRole role,String moduleIds) {
		evenName = "角色修改";
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, role);
				
		SysRole oldRole = roleService.get(role.getId());
		oldRole.setName(role.getName());
		oldRole.setDescription(role.getDescription());
		
		List<String> oldRoleModuleIds = new ArrayList<String>();
		for(int i=0;i<oldRole.getRoleModules().size();i++){
			oldRoleModuleIds.add(oldRole.getRoleModules().get(i).getModule().getId().toString());
		}
		if (!StringUtils.isBlank(moduleIds)) {
			if (moduleIds.contains(",")) {
				//选择多个菜单
				String[] arr = moduleIds.split(",");
				List<String> moduleIdList = Arrays.asList(arr);
				for(int i=0;i<arr.length;i++){
					SysRoleModule roleModule = roleModuleService.findByRoleIdAndModuleId(role.getId(),Long.parseLong(arr[i]));
					if(roleModule == null){
						roleModule = new SysRoleModule();
						SysModule module = moduleService.get(Long.parseLong(arr[i]));
						roleModule.setRole(role);
						roleModule.setModule(module);
						oldRole.getRoleModules().add(roleModule);
					} 
				}
				for(int i=0;i<oldRoleModuleIds.size();i++){
					if(!moduleIdList.contains(oldRoleModuleIds.get(i))){
						SysRoleModule roleModule = roleModuleService.findByRoleIdAndModuleId(role.getId(),Long.parseLong(oldRoleModuleIds.get(i)));
						oldRole.getRoleModules().remove(roleModule);
						roleModuleService.delete(roleModule.getId());
					}
				}
			} else {
				//只选一个菜单时
				SysRoleModule roleModule = roleModuleService.findByRoleIdAndModuleId(role.getId(),Long.parseLong(moduleIds));
				if(roleModule == null){
					roleModule = new SysRoleModule();
					SysModule module = moduleService.get(Long.parseLong(moduleIds));
					roleModule.setRole(role);
					roleModule.setModule(module);
					oldRole.getRoleModules().add(roleModule);
				} 
				for(int i=0;i<oldRoleModuleIds.size();i++){
					if(!moduleIds.equals(oldRoleModuleIds.get(i))){
						SysRoleModule rm = roleModuleService.findByRoleIdAndModuleId(role.getId(),Long.parseLong(oldRoleModuleIds.get(i)));
						oldRole.getRoleModules().remove(rm);
						roleModuleService.delete(rm.getId());
					}
				}
			}
		}
		roleService.update(oldRole);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{oldRole.getName()}));
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Role:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		evenName = "角色删除";
		SysRole role = roleService.get(id);
		roleService.delete(id);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{role.getName()}));
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Role:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, String keywords, Map<String, Object> map) {
		List<SysRole> roles = null;
		if (StringUtils.isNotBlank(keywords)) {
			roles = roleService.find(page, keywords);
		} else {
			roles = roleService.findAll(page);
		}

		map.put("page", page);
		map.put("roles", roles);
		map.put("keywords", keywords);
		return LIST;
	}

	
	/**
	 *  重新组装PermissionList（切分test:save,test:edit的形式）
	 * @param role
	 * @param moduleIds
	 */
		private void refactor(SysRole role,String moduleIds) {
			List<SysRoleModule> roleModules = new ArrayList<SysRoleModule>(0);
			if (StringUtils.isBlank(moduleIds)) {
				return;
			}
			if (moduleIds.contains(",")) {
				String[] arr = moduleIds.split(",");
				for(int i=0;i<arr.length;i++){
					SysRoleModule roleModule = new SysRoleModule();
					SysModule module = moduleService.get(Long.parseLong(arr[i]));
					roleModule.setRole(role);
					roleModule.setModule(module);
					roleModules.add(roleModule);
				}
				
			} else {
				SysRoleModule roleModule = new SysRoleModule();
				SysModule module = moduleService.get(Long.parseLong(moduleIds));
				roleModule.setRole(role);
				roleModule.setModule(module);
				roleModules.add(roleModule);
			}
			role.setRoleModules(roleModules);
		}
		
	 /**
	  * 获取ztree树结构json数据
	  * @param module
	  * @param permissionSet
	  * @return
	  */
	 private String getTreeJson(SysModule module,Set<String> permissionSet){
		    if (module.getChildren().isEmpty()) {
				return "";
			}
			StringBuffer buffer = new StringBuffer();
			
			for(SysModule o : module.getChildren()) {
				if(permissionSet.contains(o.getActions()+":"+o.getMethods())){
					buffer.append("{id:"+o.getId()+",pId:"+o.getParent().getId()+",name:\""+o.getName()+"\",checked:true}" + ",");
				}else{
					buffer.append("{id:"+o.getId()+",pId:"+o.getParent().getId()+",name:\""+o.getName()+"\"}" + ",");
				}
				if(!o.getChildren().isEmpty()){
					buffer.append(getTreeJson(o, permissionSet));
				}
			}
			return buffer.toString();
	 }
		
}
