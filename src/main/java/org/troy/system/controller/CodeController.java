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
import org.troy.system.entity.main.SysCode;
import org.troy.system.service.CodeService;

/**
 * 代码Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/system/config/code")
public class CodeController extends BaseController{

	@Autowired
	private CodeService codeService;
	
	@Autowired
	private Validator validator;
	
	private static final String CREATE = "system/config/code/create";
	private static final String UPDATE = "system/config/code/update";
	private static final String LIST = "system/config/code/list";
	
	@RequiresPermissions("Code:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了{0}数据字典代码。")
	@RequiresPermissions("Code:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(SysCode code) {
		evenName = "代码添加";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, code);
		
		codeService.save(code);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{code.getCodeType()+":"+code.getCodeValue()}));
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Code:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		SysCode code = codeService.get(id);
		map.put("code", code);
		return UPDATE;
	}
	
	@Log(message="修改了{0}数据字典代码。")
	@RequiresPermissions("Code:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(SysCode code) {
		evenName = "代码修改";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, code);
		
		codeService.update(code);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{code.getCodeType()+":"+code.getCodeValue()}));
		return ajaxObject.toString();
	}
	
	@Log(message="删除了{0}数据字典代码。")
	@RequiresPermissions("Code:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		evenName = "代码删除";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		SysCode code = null;
		code = codeService.get(id);
		codeService.delete(id);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{code.getCodeType()+":"+code.getCodeValue()}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	@Log(message="删除了{0}数据字典代码。")
	@RequiresPermissions("Code:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		evenName = "代码删除";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		String[] codetypes = new String[ids.length];
		for (int i = 0; i < ids.length; i++) {
			SysCode code = codeService.get(ids[i]);
			codeService.delete(code.getId());
			
			codetypes[i] = code.getCodeType()+":"+code.getCodeValue();
		}
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(codetypes)}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Code:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, String keywords, Map<String, Object> map) {
		List<SysCode> codes = null;
		if (StringUtils.isNotBlank(keywords)) {
			codes = codeService.find(page, keywords);
		} else {
			codes = codeService.findAll(page);
		}

		map.put("page", page);
		map.put("codes", codes);
		map.put("keywords", keywords);
		return LIST;
	}
		

	/**
	 * 查找数据字典，组装下拉框json数据
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/code_list", method={RequestMethod.POST})
	public @ResponseBody String codelist(String tableName,String where,String orderBy,
			String valuefield,String displayfield) {
		evenName = "加载数据";
		List<String> list = codeService.findCodeListBySql(tableName, where, orderBy, valuefield, displayfield);
		StringBuffer strBf = new StringBuffer();
		if(list!=null && list.size()>0){
			strBf.append("[");
			for(int i=0;i<list.size();i++){
				 list.get(i);
				strBf.append("["+list.get(i)+"]");
				if(i!=list.size()-1){
					strBf.append(",");
				}
			}
			strBf.append("]");
		}
		
        return strBf.toString();
	}
	
	/**
	 * HQL查询
	 * 查找数据字典，组装下拉框json数据
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/syscode", method={RequestMethod.POST})
	public @ResponseBody String syscode(String codeType,String notIn) {
		evenName = "加载数据";
		List<SysCode> list = codeService.findCodeListWithCache(codeType, notIn);
		StringBuffer strBf = new StringBuffer();
		if(list!=null && list.size()>0){
			strBf.append("[");
			for(int i=0;i<list.size();i++){
				 list.get(i);
				strBf.append("[\""+list.get(i).getCodeValue()+"\",\""+list.get(i).getCodeName()+"\"]");
				if(i!=list.size()-1){
					strBf.append(",");
				}
			}
			strBf.append("]");
		}
		
        return strBf.toString();
	}
	
}
