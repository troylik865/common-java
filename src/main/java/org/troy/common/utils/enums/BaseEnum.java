package org.troy.common.utils.enums;

/**
 * <strong>Title : BaseEnum<br></strong>
 * 基础业务枚举类,K为泛型,为Code字段的类型定义 
 * @author wangj
 * 2013-6-25
 * @param <K>
 */
public interface BaseEnum<K> {
	
	/**
	 * 得到存入Db/或者代表的值
	 * @return
	 */
	K getValue();
	
	/**
	 * 显示文本名称
	 * @return
	 */
	String getName();
	
	/**
	 * 描述信息
	 * @return
	 */
	String getDesc();
	
    
}
