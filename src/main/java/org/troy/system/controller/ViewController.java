package org.troy.system.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.editor.DateEditor;
import org.troy.common.utils.editor.DoubleEditor;
import org.troy.common.utils.editor.IntegerEditor;
import org.troy.common.utils.editor.LongEditor;

/**
 * Controller通用的方法类
 * 
 * @author troy
 * @version $Id: ViewController.java, v 0.1 2014年6月22日 上午11:37:40 troy Exp $
 */
public abstract class ViewController {
	
	/** 操作名称  **/
	protected String evenName = "";
	
    private String pre;

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
    
    public String createView(String view) {
        return this.getPre() + "/" + view;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

	public String getEvenName() {
		return evenName;
	}

	public void setEvenName(String evenName) {
		this.evenName = evenName;
	}
}
