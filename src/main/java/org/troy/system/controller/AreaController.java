package org.troy.system.controller;

import java.util.ArrayList;
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
import org.troy.common.utils.StringUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysArea;
import org.troy.system.service.AreaService;


/** 
 * 行政区划Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/system/config/area")
public class AreaController extends BaseController{
	@Autowired
	private AreaService areaService;
	@Autowired
	private Validator validator;
	
	private static final String CREATE = "system/config/area/create";
	private static final String UPDATE = "system/config/area/update";
	private static final String LIST = "system/config/area/list";
	private static final String TREE = "system/config/area/tree";
	private static final String TREE_LIST = "system/config/area/tree_list";
	private static final String LOOK_AREA = "system/config/area/look_area";
	
	@RequiresPermissions("Area:save")
	@RequestMapping(value="/create/{parentId}", method=RequestMethod.GET)
	public String preCreate(@PathVariable Long parentId, Map<String, Object> map) {
		map.put("parentId", parentId);
		return CREATE;
	}
	
	@RequiresPermissions("Area:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(SysArea area) {
		evenName = "添加区域";
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, area);
			
		SysArea parentArea = areaService.get(area.getParent().getId());
		if (parentArea == null) {
			return AjaxObject.newError("添加区域失败：id=" + area.getParent().getId() + "的父区域不存在！").toString();
		}
		areaService.save(area);
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{area.getAreaName()}));
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Area:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		SysArea area = areaService.get(id);
		
		map.put("area", area);
		return UPDATE;
	}
	
	@RequiresPermissions("Area:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(SysArea area) {
		//调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
		BeanValidators.validateWithException(validator, area);
		AjaxObject ajaxObject = new AjaxObject("区域修改成功！");		
		if(area.getId().equals(area.getParent().getId())){
			ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
			ajaxObject.setMessage("区域修改失败：不可选择自身作为父级");
		}else{
			areaService.update(area);
		}
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{area.getAreaName()}));
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Area:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		evenName = "删除区域";
		SysArea area = areaService.get(id);
		areaService.delete(id);
			
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{area.getAreaName()}));
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		ajaxObject.setCallbackType("");
		ajaxObject.setRel("jbsxBox2Area");
		
		return ajaxObject.toString();
	}
	
	@RequiresPermissions("Area:view")
	@RequestMapping(value="/tree_list", method={RequestMethod.GET, RequestMethod.POST})
	public String tree_list(Map<String, Object> map) {
		String areaCode = "";
		initArea();
		SysArea area = areaService.getTree();
		List<SysArea> childList = area.getChildren();
		List<SysArea> child = new ArrayList<SysArea>();
		for(int i=0; i<childList.size(); i++){
			SysArea sa = childList.get(i);
			if(sa.getAreaCode().equals(this.areaCode)){
				areaCode = this.areaCode;
				child.add(sa);
				childList.removeAll(childList);
				break;
			}
		}
		if(StringUtil.isNotEmpty(child)){
			area.setChildren(child);
		}
		map.put("areaCode", areaCode);
		map.put("area", area);
		return TREE_LIST;
	}
	@RequiresPermissions("Area:view")
	@RequestMapping(value="/tree", method=RequestMethod.GET)
	public String tree(Map<String, Object> map) {
		return TREE;
	}
	
	@RequiresPermissions("Area:view")
	@RequestMapping(value="/list/{parentId}", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, @PathVariable Long parentId, String keywords, 
			Map<String, Object> map) {
		List<SysArea> areas = null;
		if (StringUtils.isNotBlank(keywords)) {
			areas = areaService.find(parentId, keywords, page);
		} else {
			areas = areaService.find(parentId, page);
		}
		
		map.put("page", page);
		map.put("areas", areas);
		map.put("keywords", keywords);
		map.put("parentId", parentId);

		return LIST;
	}
	
	/**
	 * 查找行政区划树
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/look_area", method={RequestMethod.GET})
	public String lookArea(Map<String, Object> map) {
		String areaCode = "";
		initArea();
		SysArea area = areaService.getTree();
		List<SysArea> childList = area.getChildren();
		List<SysArea> child = new ArrayList<SysArea>();
		for(int i=0; i<childList.size(); i++){
			SysArea sa = childList.get(i);
			if(sa.getAreaCode().equals(this.areaCode)){
				areaCode = this.areaCode;
				child.add(sa);
				childList.removeAll(childList);
				break;
			}
		}
		if(StringUtil.isNotEmpty(child)){
			area.setChildren(child);
		}
		map.put("areaCode", areaCode);
		map.put("area", area);
		return LOOK_AREA;
	}
	
	/**
	 * 查找行政区域，组装下拉框json数据
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/getjson", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getjson(String paramValue) {
		String streetCode = "";
		initArea();
		if(StringUtil.isNotEmpty(this.streetCode)){
			streetCode = this.streetCode;
		}
		evenName = "加载数据";
		List<SysArea> list = areaService.find(paramValue,streetCode);
		StringBuffer strBf = new StringBuffer();
		if(list!=null && list.size()>0 && list.get(0) != null){
			strBf.append("[");
			for(int i=0;i<list.size();i++){
				 list.get(i);
				strBf.append("[\""+list.get(i).getAreaCode()+"\",\""+list.get(i).getAreaName()+"\"]");
				if(i!=list.size()-1){
					strBf.append(",");
				}
			}
			strBf.append("]");
		}
		
        return strBf.toString();
	}
	
	/**
	 * SQL查询
	 * 查找行政区域，组装下拉框json数据
	 * @param areaLevel 区域级别
	 * @return
	 */
	@RequestMapping(value="/sysarea", method={RequestMethod.POST})
	public @ResponseBody String sysarea(String areaLevel,String areaCode) {
		boolean isGlobal = true;//判断登录用户所属机构是市级还是区级
		initArea();
		if(StringUtil.isNotEmpty(this.areaCode)){
			isGlobal = false;
		}
		evenName = "加载数据";
		List<Map<String, Object>> list = areaService.findByAreaLevelAndAreaCode(areaLevel,areaCode,isGlobal);
		StringBuffer strBf = new StringBuffer();
		if(list!=null && list.size()>0){
			strBf.append("[");
			for(int i=0;i<list.size();i++){
				Map<String,Object> map = list.get(i);
				strBf.append("[\""+map.get("area_code")+"\",\""+map.get("area_name")+"\"]");
				if(i!=list.size()-1){
					strBf.append(",");
				}
			}
			strBf.append("]");
		}
		
        return strBf.toString();
	}
	
}
