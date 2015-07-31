package org.troy.system.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.component.log.LogLevel;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysLog;

/**
 * 系统日志业务接口
 * @author wangj
 * 2013-5-17
 */

public interface LogService extends BaseJdbcService{
	
	void save(SysLog log);
	
	void delete(Long id);
	
	void update(SysLog log);
	
	SysLog get(Long id);
	
	/**
	 * 分页查询日志
	 * @param page
	 * @param logLevel
	 * @return
	 */
    List<SysLog> findByLogLevel(LogLevel logLevel, Page page);
	
	List<SysLog> findByExample(Specification<SysLog> specification, Page page);
}
