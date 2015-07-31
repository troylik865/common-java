package org.troy.common.component.log;

import java.util.Map;

/**
 * 全局日志操作接口api
 * @author wangj
 * 2013-6-7
 */
public interface LogAPI {
	void log(String message, LogLevel logLevel);
	
	void log(String message, Object[] objects, LogLevel logLevel);
	
	/**
	 * 
	 * 得到全局日志等级
	 * @return
	 */
	LogLevel getRootLogLevel();
	
	/**
	 * 
	 * 得到自定义包的日志等级
	 * @return
	 */
	Map<String, LogLevel> getCustomLogLevel();
}
