package org.troy.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.common.component.log.Log;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.system.service.CacheService;

/**
 * 缓存Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/system/security/cacheManage")
public class CacheManageController extends BaseController{
	@Autowired
	private CacheService cacheService;
	
	private static final String INDEX = "system/security/cacheManage/index";
	
	@RequiresPermissions("CacheManage:view")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index() {
		return INDEX;
	}
	
	@Log(message="进行了缓存清理。")
	@RequiresPermissions("CacheManage:edit")
	@RequestMapping(value="/clear", method=RequestMethod.POST)
	public @ResponseBody String clear() {
		cacheService.clearAllCache();
		
		AjaxObject ajaxObject = new AjaxObject("清除缓存成功！");
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	@Log(message="进行了缓存更新。")
	@RequiresPermissions("CacheManage:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update() {
		cacheService.updateCache();
		
		AjaxObject ajaxObject = new AjaxObject("缓存更新成功！");
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
}
