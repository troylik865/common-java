package org.troy.common.component.log.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.troy.biz.work.util.LogConst;
import org.troy.common.component.log.LogLevel;
import org.troy.system.entity.main.SysLog;
import org.troy.system.service.LogService;
import org.troy.system.shiro.ShiroDbRealm;

import com.google.common.collect.Maps;

/** 
 * 全局日志等级<包日志等级<类和方法日志等级
 * @author wangj
 * 2013-6-7
 */
public class LogHelper extends LogAdapter {
	
	private LogLevel rootLogLevel = LogLevel.ERROR;
	
	private LogService logService;
	
	private Map<String, LogLevel> customLogLevel = Maps.newHashMap();

	/**
	 * 
	 * @param message
	 * @param objects
	 * @param logLevel  
	 * @see com.cnnct.common.component.log.impl.LogAdapter#log(java.lang.String, java.lang.Object[], com.cnnct.common.component.log.LogLevel)
	 */
	@Override
	public void log(String message, Object[] objects, LogLevel logLevel) {	
		
		MessageFormat mFormat = new MessageFormat(message);
		String result = mFormat.format(objects);
		
		if (!StringUtils.isNotBlank(result)) {
			return;
		}
		
		Subject subject = SecurityUtils.getSubject();
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser)subject.getPrincipal();
		
		SysLog sysLog = new SysLog();
		sysLog.setCreateTime(new Date());
		
		sysLog.setUsername(shiroUser.getLoginName());
		String[] s = result.split(LogConst.LOG_SPLIT);
		if(StringUtils.isNotBlank(s[0]) && s.length == 2){
			sysLog.setLogType(s[0]);
		}
		if(s.length == 1){
			sysLog.setMessage(s[0]);
		}else{
			sysLog.setMessage(s[1]);
		}
		sysLog.setIpAddress(shiroUser.getIpAddress());
		sysLog.setLogLevel(logLevel);
		
		logService.save(sysLog);
	}

	public void setRootLogLevel(LogLevel rootLogLevel) {
		this.rootLogLevel = rootLogLevel;
	}

	/**   
	 * 
	 * @return  
	 * @see com.cnnct.common.component.log.LogTemplate#getRootLogLevel()  
	 */
	@Override
	public LogLevel getRootLogLevel() {
		return rootLogLevel;
	}
	
	public void setCustomLogLevel(Map<String, LogLevel> customLogLevel) {
		this.customLogLevel = customLogLevel;
	}
	
	@Override
	public Map<String, LogLevel> getCustomLogLevel() {
		return customLogLevel;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
}
