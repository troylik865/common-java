package org.troy.system.controller;

import java.util.List;
import java.util.Map;

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
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysModule;
import org.troy.system.service.ModuleService;


/**
 * 功能模块菜单Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/system/security/module")
public class ModuleController extends BaseController{
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private Validator validator;
	
	private static final String CREATE = "system/security/module/create";
	private static final String UPDATE = "system/security/module/update";
	private static final String LIST = "system/security/module/list";
	private static final String TREE = "system/security/module/tree";
	private static final String TREE_LIST = "system/security/module/tree_list";
	private static final String LOOK_MODULE = "system/security/module/look_module";
	
	@RequiresPermissions("Module:save")
	@RequestMapping(value="/create/{parentId}", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map, @PathVariable Long parentId) {
		map.put("parentId", parentId);
		return CREATE;
	}
	
	@Log(message="添加了{0}模块。")
	@RequiresPermissions("Module:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(SysModule module) {
		evenName = "模块添加";
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, module);
		SysModule parentModule = moduleService.get(module.getParent().getId());
		if (parentModule == null) {
			return AjaxObject.newError("模块添加失败：id=" + module.getParent().getId() + "的父模块不存在！").toString();
		}
		module.setParent(parentModule);
		moduleService.save(module);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{module.getName()}));
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Module:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		SysModule module = moduleService.get(id);
		
		map.put("module", module);
		return UPDATE;
	}
	
	@Log(message="修改了{0}模块的信息。")
	@RequiresPermissions("Module:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(SysModule module) {
		evenName = "模块修改";
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, module);
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		if(module.getId().equals(module.getParent().getId())){
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("模块修改失败：不可选择自身作为父级");
		}else{
			moduleService.update(module);
		}
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{module.getName()}));
		return ajaxObject.toString();
	}
	
	@Log(message="删除了{0}模块。")
	@RequiresPermissions("Module:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		evenName = "模块删除";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		SysModule module = moduleService.get(id);
		moduleService.delete(id);
		//记录日志	
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{module.getName()}));
		
		ajaxObject.setCallbackType("");
		ajaxObject.setRel("jbsxBox2module");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/tree_list", method={RequestMethod.GET, RequestMethod.POST})
	public String tree_list(Map<String, Object> map) {
		SysModule module = moduleService.getTree();
		
		map.put("module", module);
		return TREE_LIST;
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/tree", method=RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		return TREE;
	}
	
	@RequiresPermissions("Module:view")
	@RequestMapping(value="/list/{parentId}", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, @PathVariable Long parentId, String keywords, 
			Map<String, Object> map) {
		List<SysModule> modules = null;
		if (StringUtils.isNotBlank(keywords)) {
			modules = moduleService.find(parentId, keywords, page);
		} else {
			modules = moduleService.find(parentId, page);
		}
		
		map.put("page", page);
		map.put("modules", modules);
		map.put("keywords", keywords);
		map.put("parentId", parentId);
		
		return LIST;
	}
	
	/**
	 * 查找功能模块树
	 * @param map
	 * @return
	 */
	@RequiresPermissions("Module:edit")
	@RequestMapping(value="/look_module", method={RequestMethod.GET})
	public String lookup(Map<String, Object> map) {
		SysModule module = moduleService.getTree();
		
		map.put("module", module);
		return LOOK_MODULE;
	}
}
