package org.troy.biz.work.enums;

import org.troy.common.utils.enums.BaseEnum;

/**
 * @author jiangb
 * 2013-11-20
 */
public enum LogType implements BaseEnum<String> {
	/**管理员卡*/
	LOGTYPE_01("01","管理员卡"),
	/**用户登录*/
	LOGTYPE_11("11","用户登录"),
	/**用户退出*/
	LOGTYPE_12("12","用户退出");

	private String value;
	private String name;
	
	private LogType(String value,String name){
		this.value = value;
		this.name = name;
	}
	
	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDesc() {
		return null;
	}

}
