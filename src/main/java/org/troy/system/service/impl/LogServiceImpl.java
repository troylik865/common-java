package org.troy.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.component.log.LogLevel;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.dao.LogDao;
import org.troy.system.entity.main.SysLog;
import org.troy.system.service.LogService;

/**
 * 系统日志接口实现类
 * @author wangj
 * 2013-5-17
 */
@Service
public class LogServiceImpl extends BaseJdbcServiceImpl implements LogService {
	
	@Autowired
	private LogDao logDao;
	
	/** (non-Javadoc)
	 * @see org.troy.system.service.service.LogService#save(com.cnnct.system.entity.main.SysLog)
	 */
	@Transactional
	public void save(SysLog log) {
		logDao.save(log);		
	}

	/** (non-Javadoc)
	 * @see org.troy.system.service.service.LogService#delete(java.lang.Long)
	 */
	@Transactional
	public void delete(Long id) {
		logDao.delete(id);		
	}

	/** (non-Javadoc)
	 * @see org.troy.system.service.service.LogService#update(com.cnnct.system.entity.main.SysLog)
	 */
	@Transactional
	public void update(SysLog log) {
		logDao.save(log);	
	}

	/** (non-Javadoc)
	 * @see org.troy.system.service.service.LogService#get(java.lang.Long)
	 */
	@Transactional(readOnly=true)
	public SysLog get(Long id) {
		return logDao.findOne(id);
	}
	
	/** (non-Javadoc)
	 * @see org.troy.system.service.service.LogService#findByLogLevel(com.cnnct.common.component.log.LogLevel, com.cnnct.common.utils.dwz.Page)
	 */
	@Transactional(readOnly=true)
	public List<SysLog> findByLogLevel(LogLevel logLevel, Page page) {
		org.springframework.data.domain.Page<SysLog> springDataPage = 
				logDao.findByLogLevel(logLevel, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/** (non-Javadoc)
	 * @see org.troy.system.service.service.LogService#findByExample(org.springframework.data.jpa.domain.Specification, com.cnnct.common.utils.dwz.Page)
	 */
	@Transactional(readOnly=true)
	public List<SysLog> findByExample(Specification<SysLog> specification,
			Page page) {
		org.springframework.data.domain.Page<SysLog> springDataPage = 
				logDao.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

}
