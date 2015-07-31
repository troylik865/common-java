package org.troy.system.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.troy.common.component.log.LogLevel;
import org.troy.system.entity.main.SysLog;

/**
 * 系统 日志dao
 * @author wangj
 * 2013-5-17
 */

public interface LogDao extends JpaRepository<SysLog, Long>,JpaSpecificationExecutor<SysLog> {
	/**
	 * 根据日志级别查找
	 * @param level
	 * @param pageable
	 * @return
	 */
	Page<SysLog> findByLogLevel(LogLevel level, Pageable pageable);
}
