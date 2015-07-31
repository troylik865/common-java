package org.troy.test.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.component.log.LogLevel;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.dao.LogDao;
import org.troy.system.entity.main.SysLog;
import org.troy.test.service.TestService;

/**
 * 系统用户业务接口实现类
 * @author wangj
 * 2013-5-17
 */
@Service
public class TestServiceImpl extends BaseJdbcServiceImpl implements TestService {
	@Autowired
	private LogDao logDao;
	
	public List<Object[]> findLists() {
		List<Object[]> users = new ArrayList<Object[]>();
		String sql ="SELECT id,username,realname,phone,email FROM sys_user order by id desc";
		try {
			  users=this.executeQueryForObjList(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}

	public List<Map<String,Object>> findMapLists() {
		List<Map<String,Object>> users = new ArrayList<Map<String,Object>>();
		String sql ="SELECT id,username,realname,phone,email FROM sys_user order by id desc";
		try {
			  users=this.executeQueryForMapList(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public List<Map<String,Object>> findMapLists(String sql) {
		List<Map<String,Object>> users = new ArrayList<Map<String,Object>>();
		try {
			  users=this.executeQueryForMapList(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public List<Map<String,Object>> findLogLists(Page page) {
		List<Map<String,Object>> logs = new ArrayList<Map<String,Object>>();
		String sql ="SELECT * FROM sys_log order by id desc";
		try {
			logs=this.executeQueryPageForMapList(page,sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return logs;
	}

	public List<SysLog> findByLogLevel(LogLevel logLevel, Page page) {
		org.springframework.data.domain.Page<SysLog> springDataPage = 
				logDao.findByLogLevel(logLevel, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.cnnct.test.service.TestService#updateSql(java.lang.String)
	 */
	@Override
	public void updateSql(String sql) {
          try {
			this.executeUpdateForCommit(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
