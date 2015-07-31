package org.troy.biz.service;

import java.util.List;
import java.util.Map;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;

/***
 * <p>Title:BlockService </p> 
 *
 * <p>Description:通用接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2013 </p>
 * 
 * <p>CreateDate: 2013-11-05 16:04 </p>
 *
 */
public interface CommonService extends BaseJdbcService{

    public int update(String sql,Object... args);
	/**
	 * 执行存储过程，不返回结果集
	 * 
	 * @param sql
	 *            存储过程的调用语句，格式：{call <ProcName>(<ParamList>)}
	 */
	public void execProcedure(String sql) throws ServiceException;
	/**
	 * 执行存储过程，返回结果
	 * 
	 * @param sql
	 * @param in 传入参数
	 * @param out 输出参数类型
	 *            存储过程的调用语句，格式：{call <ProcName>(<ParamList>)}
	 */
	public Object[] callProcedure(String sql,Object[] in,Object[] out) throws ServiceException;
	/**
	 * 批量执行sql语句
	 * @param sql
	 */
	public void batchSql(String[] sql) throws ServiceException;
	/**
	 * 执行insert或update的sql语句，返回结果集
	 * @param sql
	 */
	public int execSql(String sql) throws ServiceException;
	/**
	 * 执行sql语句，返回结果集
	 * @param sql
	 */
	public List<Object[]> execQuaryForObject(String sql) throws ServiceException;
	/**
	 * 保存日志
	 * @param str
	 */
	public void savelog(String str) throws ServiceException;
	/**
	 * 保存日志
	 * @param str
	 */
	public void savelog(String str,String logType) throws ServiceException;
	
	 /**
     * 插入数据，返回自增INT主键值
     * 
     * @param insertSql
     *            插入数据SQL【INSERT INTO TEST(field_without_primarykey) values(.....)】
     * @param primaryKey
     *            主键名称
     * @return
     */
    public int save(final String insertSql, final String primaryKey, final Object... parameters);
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
    public <T> List<T> findBySQL(String sql, Class<T> c, Object... paramValues);
    
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
    public <T> T findOneBySQL(String sql, Class<T> c, Object... paramValues);
    
    public List<Map<String,Object>> queryForList(String sql,Object... args);
}
