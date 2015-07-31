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
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysGroup;
import org.troy.system.service.GroupService;


/** 
 * 组织机构Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/system/security/group")
public class GroupController extends BaseController{
	@Autowired
	private GroupService groupService;
	@Autowired
	private Validator validator;
	
	private static final String CREATE = "system/security/group/create";
	private static final String UPDATE = "system/security/group/update";
	private static final String LIST = "system/security/group/list";
	private static final String TREE = "system/security/group/tree";
	private static final String TREE_LIST = "system/security/group/tree_list";
	private static final String LOOK_GROUP = "system/security/group/look_group";
	
	@RequiresPermissions("Group:save")
	@RequestMapping(value="/create/{parentId}", method=RequestMethod.GET)
	public String preCreate(@PathVariable Long parentId, Map<String, Object> map) {
		map.put("parentId", parentId);
		return CREATE;
	}
	
	@RequiresPermissions("Group:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(SysGroup group) {
		evenName = "添加组织";
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, group);
			
		SysGroup parentGroup = groupService.get(group.getParent().getId());
		if (parentGroup == null) {
			return AjaxObject.newError("添加组织失败：id=" + group.getParent().getId() + "的父组织不存在！").toString();
		}
		groupService.save(group);
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{group.getName()}));
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Group:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		SysGroup group = groupService.get(id);
		
		map.put("group", group);
		return UPDATE;
	}
	
	@RequiresPermissions("Group:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(SysGroup group) {
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, group);
		AjaxObject ajaxObject = new AjaxObject("组织修改成功！");		
		if(group.getId().equals(group.getParent().getId())){
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("组织修改失败：不可选择自身作为父级");
		}else{
			groupService.update(group);
		}
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{group.getName()}));
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Group:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		evenName = "删除组织";
		SysGroup group = groupService.get(id);
		groupService.delete(id);
			
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{group.getName()}));
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		ajaxObject.setCallbackType("");
		ajaxObject.setRel("jbsxBox2Group");
		
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Group:view")
	@RequestMapping(value="/tree_list", method={RequestMethod.GET, RequestMethod.POST})
	public String tree_list(Map<String, Object> map) {
		SysGroup group = groupService.getTree();
		map.put("group", group);
		return TREE_LIST;
	}
	@RequiresPermissions("Group:view")
	@RequestMapping(value="/tree", method=RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		return TREE;
	}
	
	@RequiresPermissions("Group:view")
	@RequestMapping(value="/list/{parentId}", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, @PathVariable Long parentId, String keywords, 
			Map<String, Object> map) {
		List<SysGroup> groups = null;
		if (StringUtils.isNotBlank(keywords)) {
			groups = groupService.find(parentId, keywords, page);
		} else {
			groups = groupService.find(parentId, page);
		}
		
		map.put("page", page);
		map.put("groups", groups);
		map.put("keywords", keywords);
		map.put("parentId", parentId);

		return LIST;
	}
	
	/**
	 * 查找组织机构树
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/look_group", method={RequestMethod.GET})
	public String lookup(Map<String, Object> map) {
		SysGroup group = groupService.getTree();
		
		map.put("group", group);
		return LOOK_GROUP;
	}
}
