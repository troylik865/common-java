package org.troy.system.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.StringUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.editor.DateEditor;
import org.troy.common.utils.editor.DoubleEditor;
import org.troy.common.utils.editor.IntegerEditor;
import org.troy.common.utils.editor.LongEditor;
import org.troy.system.entity.main.SysUser;
import org.troy.system.util.SysConst;

import freemarker.template.Configuration;

/**
 * 基础Controller
 * @author wangj
 * 2013-5-17
 */
public class BaseController {

	@Autowired
	protected HttpServletRequest request;

	/** 操作名称  **/
	protected String evenName = "";
	/** freemarker配置文件类  **/
	protected Configuration cfg;
	/** 城区编码  **/
	protected String areaCode = "";
	/** 街道编码  **/
	protected String streetCode = "";
	/**
	 * 日期类型转换
	 * @param binder
	 */
	@InitBinder 
	protected void initBinder(WebDataBinder binder) { 
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(long.class, new LongEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());
		binder.registerCustomEditor(Date.class, new DateEditor());
		if(cfg == null){
			//初始化freemark的配置创建一个Configuration 实例
			cfg = new Configuration();
			//设置编码
			cfg.setDefaultEncoding("UTF-8");
			//设置模板文件的位置
			cfg.setServletContextForTemplateLoading(request.getSession().getServletContext(), "ftls");
		}
	}
	
	/** 
     * 异常页面控制 
     * @param ex
     * @return 
     */  
	@ExceptionHandler(value = { ServiceException.class })
	public ResponseEntity<String> handleException(ServiceException ex) {
		AjaxObject ajaxObject = AjaxObject.newError(evenName + ex.getMessage());
		return new ResponseEntity<String>(ajaxObject.toString(), HttpStatus.OK);
	}
	
	/**
	 * 初始化行政区划，查询使用
	 */
	protected void initArea(){
		SysUser user = (SysUser)request.getSession().getAttribute(SysConst.LOGIN_USER);
		areaCode = StringUtil.processNull(user.getGroup().getAreaCode());
		streetCode = StringUtil.processNull(user.getGroup().getStreetCode());
	}
}
