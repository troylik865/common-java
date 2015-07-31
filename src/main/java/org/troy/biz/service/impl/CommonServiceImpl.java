/**
 * 
 */
package org.troy.biz.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.biz.service.CommonService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.component.log.LogLevel;
import org.troy.common.exception.ServiceException;
import org.troy.system.entity.main.SysLog;
import org.troy.system.service.LogService;
import org.troy.system.util.SysConst;

/**
 * @author jiangb
 * 2013-11-5
 */
@Service
public class CommonServiceImpl extends BaseJdbcServiceImpl implements CommonService {
	
	@Autowired
	private LogService logService;

    @Transactional(rollbackFor = Exception.class)
    public int update(String sql,Object... args){
        return super.update(sql, args);
    }
	/* (non-Javadoc)
	 * @see com.cnnct.tcsf.service.CommonService#execProcedure(java.lang.String)
	 */
	@Override
	public void execProcedure(String sql) throws ServiceException {
		try {
			this.execStoredProcedure(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/* (non-Javadoc)
	 * @see com.cnnct.tcsf.service.CommonService#callProcedure(java.lang.String)
	 */
	@Override
	public Object[] callProcedure(String sql,Object[] in,Object[] out) throws ServiceException {
		Object[] back = null;
		try {
			back = this.callStoredProcedure(sql, in, out);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return back;
	}
	/* (non-Javadoc)
	 * @see com.cnnct.tcsf.service.CommonService#batchSql(java.lang.String)
	 */
	@Override
	public void batchSql(String[] sql) throws ServiceException {
		try {
			this.batchUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/* (non-Javadoc)
	 * @see com.cnnct.tcsf.service.CommonService#execSql(java.lang.String)
	 */
	@Override
	public int execSql(String sql) throws ServiceException {
		int i = -1;
		try {
			i = this.executeUpdateForRow(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	/* (non-Javadoc)
	 * @see com.cnnct.tcsf.service.CommonService#execQuaryForObject(java.lang.String)
	 */
	@Override
	public List<Object[]> execQuaryForObject(String sql) throws ServiceException {
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list= this.executeQueryForObjList(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.cnnct.tcsf.service.CommonService#savelog(java.lang.String)
	 */
	@Override
	public void savelog(String str) throws ServiceException {
		savelog(str,null);
	}
	/* (non-Javadoc)
	 * @see com.cnnct.tcsf.service.CommonService#savelog(java.lang.String)
	 */
	@Override
	public void savelog(String str,String logType) throws ServiceException {
		SysLog sysLog = new SysLog();
		sysLog.setCreateTime(new Date());
		
		sysLog.setUsername(SysConst.SYSTEM_USER);
		sysLog.setMessage(str);
		sysLog.setIpAddress(SysConst.SYSTEM_IP);
		sysLog.setLogLevel(LogLevel.TRACE);
		sysLog.setLogType(logType);
		
		logService.save(sysLog);
	}
	@Override
	public int save(final String insertSql, final String primaryKey, final Object... parameters) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = null;
                try {
                    ps = connection.prepareStatement(insertSql, new String[] { primaryKey });
                    if (parameters != null) {
                        for (int i = 0; i < parameters.length; i++) {
                            ps.setObject(i + 1, parameters[i]);
                        }
                    }
                    return ps;
                } catch (DataAccessException e) {
                   e.printStackTrace();
                   return null;
                }
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
	/**
     * 根据SQL查询对象列表
     * 
     * @param sql
     * @param c
     *            JavaBean对象
     * @param paramValues
     *            参数值
     * @return
     */
    public <T> List<T> findBySQL(String sql, Class<T> c, Object... paramValues) {
        RowMapper<T> rm = ParameterizedBeanPropertyRowMapper.newInstance(c);
        return jdbcTemplate.query(sql, rm, paramValues);
    }
    
    /**
     * 根据SQL查询对象
     * 
     * @param sql
     * @param c
     *            JavaBean对象
     * @param paramValues
     *            参数值
     * @return
     */
    public <T> T findOneBySQL(String sql, Class<T> c, Object... paramValues) {
        List<T> list = findBySQL(sql, c, paramValues);
        if (CollectionUtils.isNotEmpty(list)) { return list.get(0); }
        return null;
    }
    
    /**
    *
    * @param sql
    * @param args
    * @return
    */
   public List<Map<String,Object>> queryForList(String sql,Object... args){
       return jdbcTemplate.queryForList(sql,args);
   }
}
