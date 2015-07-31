package org.troy.test.service;

import java.util.List;
import java.util.Map;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.component.log.LogLevel;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysLog;

/**
 * 系统用户业务接口
 * @author wangj
 * 2013-5-17
 */

public interface TestService extends BaseJdbcService{

	List<Object[]> findLists();
	List<Map<String,Object>> findMapLists();
	List<Map<String,Object>> findMapLists(String sql);	
	List<Map<String,Object>> findLogLists(Page page);
	List<SysLog> findByLogLevel(LogLevel logLevel, Page page);
	void updateSql(String sql);
}
