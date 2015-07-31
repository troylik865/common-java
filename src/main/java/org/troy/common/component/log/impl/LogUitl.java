package org.troy.common.component.log.impl;

import javax.servlet.http.HttpServletRequest;

import org.troy.common.component.log.LogMessageObject;
import org.troy.system.util.SysConst;

/** 
 * 将request放入ThreadLocal用于LOG_ARGUMENTS注入。	
 * @author wangj
 * 2013-6-7
 */
public abstract class LogUitl {
	// 用于存储每个线程的request请求
	private static final ThreadLocal<HttpServletRequest> LOCAL_REQUEST = new ThreadLocal<HttpServletRequest>();
	
	public static void putRequest(HttpServletRequest request) {
		LOCAL_REQUEST.set(request);
	}
	
	public static HttpServletRequest getRequest() {
		return LOCAL_REQUEST.get();
	}
	
	public static void removeRequest() {
		LOCAL_REQUEST.remove();
	}
	
	/**
	 * 将LogMessageObject放入LOG_ARGUMENTS。
	 * 描述
	 * @param logMessageObject
	 */
	public static void putArgs(LogMessageObject logMessageObject) {
//		HttpServletRequest request = getRequest();
//		request.setAttribute(SysConst.LOG_ARGUMENTS, logMessageObject);
	}
	
	/**
	 * 得到LogMessageObject。
	 * 描述
	 * @param logMessageObject
	 */
	public static LogMessageObject getArgs() {
		HttpServletRequest request = getRequest();
		return (LogMessageObject)request.getAttribute(SysConst.LOG_ARGUMENTS);
	}
}
