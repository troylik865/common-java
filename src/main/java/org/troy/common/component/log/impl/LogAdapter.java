package org.troy.common.component.log.impl;

import java.util.Map;

import org.troy.common.component.log.LogAPI;
import org.troy.common.component.log.LogLevel;

import com.google.common.collect.Maps;

/**
 * 
 * @author wangj
 * 2013-6-7
 */

public class LogAdapter implements LogAPI {

	/**   
	 * @param message
	 * @param logLevel  
	 * @see com.cnnct.common.component.log.LogAPI#log(java.lang.String, com.cnnct.common.component.log.LogLevel)  
	 */
	public void log(String message, LogLevel logLevel) {
		log(message, null, logLevel);
	}

	/**   
	 * @param message
	 * @param objects
	 * @param logLevel  
	 * @see com.cnnct.common.component.log.LogAPI#log(java.lang.String, java.lang.Object[], com.cnnct.common.component.log.LogLevel)  
	 */
	public void log(String message, Object[] objects, LogLevel logLevel) {
		
	}
	
	/**   
	 * @return  
	 * @see com.cnnct.common.component.log.LogAPI#getRootLogLevel()  
	 */
	public LogLevel getRootLogLevel() {
		return LogLevel.ERROR;
	}

	/**   
	 * @return  
	 * @see com.cnnct.common.component.log.LogAPI#getCustomLogLevel()  
	 */
	public Map<String, LogLevel> getCustomLogLevel() {
		return Maps.newHashMap();
	}
}
