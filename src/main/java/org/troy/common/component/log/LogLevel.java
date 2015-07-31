package org.troy.common.component.log;

/** 
 * 日志等级：值越大，等级越高。 	
 * 
 * @author wangj
 * 2013-6-7
 */
public enum LogLevel {
	TRACE("操作"),
	
	LOGON("登录"),
	
	DEBUG("调试"),
	
	INFO("信息"),
	
	WARN("警告"),
	
	ERROR("错误");
	
	private String value;
	
	LogLevel(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
	
	public String getValue() {
		return this.value;
	}
}
