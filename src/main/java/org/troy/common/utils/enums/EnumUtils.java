package org.troy.common.utils.enums;

import java.util.LinkedHashMap;

/**
 * <strong>Title : EnumUtils<br></strong>
 * 枚举工具类
 * @author wangj
 * 2013-6-25
 * @param <K>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EnumUtils {
	/**
	 * 将BaseEnum.getCode()作为Key,BaseEnum.getName()作为value,存放在Map中并返回
	 * @param <T>
	 * @param values
	 * @return
	 */
	public static <T extends BaseEnum> LinkedHashMap toMap(Class<? extends BaseEnum> enumClass) {
		return toMap(enumClass.getEnumConstants());
	}

	/**
	 * 将BaseEnum.getCode()作为Key,BaseEnum.getName()作为value,存放在Map中并返回
	 * @param <T>
	 * @param values
	 * @return
	 */
	public static <T extends BaseEnum> LinkedHashMap toMap(T[] values) {
		LinkedHashMap map = new LinkedHashMap();
		for (BaseEnum item : values) {
			map.put(item.getValue(), item.getName());
		}
		return map;
	}

	public static <T extends BaseEnum> Object getCode(T kv) {
		if (kv == null)
			return null;
		return kv.getValue();
	}

	public static <T extends BaseEnum> String getName(T kv) {
		if (kv == null)
			return null;
		return kv.getName();
	}

	public static <T extends Enum> String getName(T kv) {
		if (kv == null)
			return null;
		return kv.name();
	}

	/**
	 * 根据code查找得到Enum
	 * @param code
	 * @param values
	 * @return
	 */
	public static <T extends BaseEnum> T getByCode(Object code, Class<? extends BaseEnum> enumClass) {
		return (T) getByCode(code, enumClass.getEnumConstants());
	}

	/**
	 * 根据code查找得到Enum
	 * @param code
	 * @param values
	 * @return
	 */
	public static <T extends BaseEnum> T getByCode(Object code, T[] values) {
		if (code == null)
			return null;
		for (T item : values) {
			if (item.getValue().equals(code)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 根据code查找得到Enum
	 * @param code
	 * @param values
	 * @return
	 */
	public static <T extends BaseEnum> T getRequiredByCode(Object code, Class<? extends BaseEnum> enumClass) {
		return (T) getRequiredByCode(code, enumClass.getEnumConstants());
	}

	/**
	 * 根据code得到Enum,找不到则抛异常
	 * @param <T>
	 * @param code
	 * @param values
	 * @return
	 * @throws IllegalArgumentException 根据code得到Enum,找不到则抛异常
	 */
	public static <T extends BaseEnum> T getRequiredByCode(Object code, T[] values) throws IllegalArgumentException {
		BaseEnum v = getByCode(code, values);
		if (v == null) {
			if (values.length > 0) {
				String className = values[0].getClass().getName();
				throw new IllegalArgumentException("not found " + className + " value by code:" + code);
			} else {
				throw new IllegalArgumentException("not found Enum by code:" + code);
			}
		}
		return (T) v;
	}

}
