package org.troy.system.service;

/**
 * 缓存业务接口
 * @author wangj
 * 2013-5-17
 */

public interface CacheService {
	/**
	 * 清除所有缓存
	 */
	void clearAllCache();
	
	/**
	 * 更新缓存（重新加载行政区域，组织机构，菜单权限等）
	 */
	void updateCache();
}
