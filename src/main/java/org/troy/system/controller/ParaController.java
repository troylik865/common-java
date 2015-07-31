package org.troy.system.controller;

import java.util.Arrays;
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
import org.troy.system.entity.main.SysPara;
import org.troy.system.service.ParaService;

/**
 * 参数Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/system/config/para")
public class ParaController extends BaseController{

	@Autowired
	private ParaService paraService;
	
	@Autowired
	private Validator validator;
	
	private static final String CREATE = "system/config/para/create";
	private static final String UPDATE = "system/config/para/update";
	private static final String LIST = "system/config/para/list";
	
	@RequiresPermissions("Para:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了{0}系统参数。")
	@RequiresPermissions("Para:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(SysPara para) {
		evenName = "参数添加";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, para);
		
		paraService.save(para);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{para.getParaKey()+":"+para.getParaValue()}));
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Para:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		SysPara para = paraService.get(id);
		map.put("para", para);
		return UPDATE;
	}
	
	@Log(message="修改了{0}系统参数。")
	@RequiresPermissions("Para:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(SysPara para) {
		evenName = "参数修改";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, para);
		
		paraService.update(para);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{para.getParaKey()+":"+para.getParaValue()}));
		return ajaxObject.toString();
	}
	
	@Log(message="删除了{0}系统参数。")
	@RequiresPermissions("Para:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		evenName = "参数删除";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		SysPara para = null;
		para = paraService.get(id);
		paraService.delete(id);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{para.getParaKey()+":"+para.getParaValue()}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	@Log(message="删除了{0}系统参数。")
	@RequiresPermissions("Para:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		evenName = "参数删除";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		String[] paratypes = new String[ids.length];
		for (int i = 0; i < ids.length; i++) {
			SysPara para = paraService.get(ids[i]);
			paraService.delete(para.getId());
			
			paratypes[i] = para.getParaKey()+":"+para.getParaValue();
		}
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(paratypes)}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Para:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, String keywords, Map<String, Object> map) {
		List<SysPara> paras = null;
		if (StringUtils.isNotBlank(keywords)) {
			paras = paraService.find(page, keywords);
		} else {
			paras = paraService.findAll(page);
		}

		map.put("page", page);
		map.put("paras", paras);
		map.put("keywords", keywords);
		return LIST;
	}
		
}
