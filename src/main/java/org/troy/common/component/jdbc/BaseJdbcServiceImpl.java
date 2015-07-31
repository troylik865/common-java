package org.troy.common.component.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.troy.biz.entity.BizTransRecord;
import org.troy.common.component.jdbc.page.MysqlPageOptimize;
import org.troy.common.utils.BeanUtil;
import org.troy.common.utils.StringUtil;
import org.troy.common.utils.dwz.Page;


/**
 * 對SPRING的JdbcTemplete進行再封裝的實現
 * <p>
 * <strong>className類初使化方法</strong>
 * <ul>
 * <li>方法1.
 * <li>方法2.
 * </ul>
 * @author wangj
 * 2013-5-30
 */
public class BaseJdbcServiceImpl implements BaseJdbcService{
	private static Log LOG = LogFactory.getLog(BaseJdbcServiceImpl.class);

	@Autowired
	public JdbcTemplate jdbcTemplate;


    public int update(String sql,Object... args){
        return jdbcTemplate.update(sql, args);
    }
	/**
	 *  執行不帶參數的查詢方法返回HashMap数组对象集合
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>>  executeQueryForMapList(String sql) throws SQLException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = jdbcTemplate.queryForList(sql);
		return list;
	}
	
	 /**
     * @param sql
     * @return
     */
	@SuppressWarnings("deprecation")
	public int queryForInt(String sql, Object... args) {
        long start = System.currentTimeMillis();
        int count = jdbcTemplate.queryForInt(sql,args);
        long cost = System.currentTimeMillis() - start;
        if (cost > 500) {
            LOG.debug("Count SQL ----> cost : " + cost + "ms  count : " + count + "\t" + sql);
        }
        return count;
    }
    
	
	/**
	 * 分頁查詢數據返回List列表,sql不帶參數
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param page
	 *            Page 描述

	 * @throws Exception
	 *             如果...將拋出exceptionType異常.
	 * @return List<?>
	 */
	@SuppressWarnings("deprecation")
	public List<Map<String,Object>> executeQueryPageForMapList(Page page,String sql) throws Exception{
		if(page==null){
			return jdbcTemplate.queryForList(sql);
		}
		List<Map<String,Object>> list = null;
		/*// 读取总记录数
		int recordCount =0;
		ResultSet rs=queryForResultSet(sql);
		boolean bFlg = rs.last();
        if (bFlg)
        	recordCount = rs.getRow();
		page.setTotalCount(recordCount);*/
		
		MysqlPageOptimize po = new MysqlPageOptimize();
		//计算总记录数
        page.setTotalCount(jdbcTemplate.queryForLong(po.getCountQuerySQL(sql)));

		if (page.getTotalCount() <= 0) {
			// 如果取回的數值還是零,那么直接返回
		} else {
			// 優化查詢sql以提高查詢速度
			String osql = "";
			osql = po.getOptimizeSQLString(page, sql);

			list = jdbcTemplate.queryForList(osql);
		}
		return list;
	}
	
	/**
	 *  執行不帶參數的查詢方法返回object数组对象集合
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<Object[]>  executeQueryForObjList(String sql) throws SQLException {
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] obj = null;
		int iCol = 0, i = 0;
		SqlRowSet srs = null;
		SqlRowSetMetaData rsmd = null;
		srs = (SqlRowSet)jdbcTemplate.query(sql, new SqlRowSetResultSetExtractor());
		rsmd = srs.getMetaData();
		iCol = rsmd.getColumnCount();
		while (srs.next()) {
			obj = new Object[iCol];
			for (i = 0; i < iCol; i++) {
				Object o = srs.getObject(i + 1);
				obj[i] = o;
			}
			list.add(obj);
		}
		return list;
	}
	
	/***********************************************************************************************/
	/**
	 * 執行不帶參數的查詢方法返回帶列名的二維數組
	 * <p>
	 * 
	 * @param sql
	 *            type sql語句
	 * @param param2
	 *            type 描述

	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public String[][] executeQuery(String sql) throws SQLException {
		String sTmp;
		String[][] sArrRtn = null;
		int iCol = 0, i = 0, j = 0;
		boolean bFlg = false;
		SqlRowSet srs = null;
		SqlRowSetMetaData rsmd = null;
		srs = (SqlRowSet)jdbcTemplate.query(sql, new SqlRowSetResultSetExtractor());
		rsmd = srs.getMetaData();
		iCol = rsmd.getColumnCount();
		bFlg = srs.last();
		if (bFlg) {
			i = srs.getRow();
		}
		srs.beforeFirst();
		sArrRtn = new String[i + 1][iCol];
		for (i = 0; i < iCol; i++) {
			sTmp = rsmd.getColumnName(i + 1).trim();
			sArrRtn[0][i] = sTmp;
		}
		while (srs.next()) {
			j = j + 1;
			for (i = 0; i < iCol; i++) {
				Object o = srs.getObject(i + 1);
				sTmp = String.valueOf(o == null ? "" : o);
				sTmp = sTmp.equalsIgnoreCase("null") ? "" : sTmp;
				sArrRtn[j][i] = sTmp;
			}
		}
		return sArrRtn;
	}

	/**
	 * 執行帶參數的查詢方法返回帶列名的二維數組
	 * <p>
	 * 
	 * @param sql
	 *            type sql語句
	 * @param Object
	 *            type 綁定變量數組

	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public String[][] executeQuery(String sql, Object[] args) throws SQLException {
		String sTmp;
		String[][] sArrRtn = null;
		int iCol = 0, i = 0, j = 0;
		boolean bFlg = false;
		SqlRowSet srs = null;
		SqlRowSetMetaData rsmd = null;
		srs = (SqlRowSet)jdbcTemplate.query(sql,args, new SqlRowSetResultSetExtractor());
		rsmd = srs.getMetaData();
		iCol = rsmd.getColumnCount();
		bFlg = srs.last();
		if (bFlg) {
			i = srs.getRow();
		}
		srs.beforeFirst();
		sArrRtn = new String[i + 1][iCol];
		for (i = 0; i < iCol; i++) {
			sTmp = rsmd.getColumnName(i + 1).trim();
			sArrRtn[0][i] = sTmp;
		}
		while (srs.next()) {
			j = j + 1;
			for (i = 0; i < iCol; i++) {
				Object o = srs.getObject(i + 1);
				sTmp = String.valueOf(o == null ? "" : o);
				sTmp = sTmp.equalsIgnoreCase("null") ? "" : sTmp;
				sArrRtn[j][i] = sTmp;
			}
		}
		return sArrRtn;
	}

	/**
	 * 執行不帶參數的查詢方法返回一個BEAN_LIST
	 * <p>
	 * 
	 * @param param1
	 *            type 描述
	 * @param param2
	 *            type 描述
	 * @see package.className[#method([param])]
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public List<?> queryForList(String sql, Object object) throws Exception {
		SqlRowSet srs = null;
		srs = (SqlRowSet) jdbcTemplate.query(sql, new SqlRowSetResultSetExtractor());
		List<?> list = BeanUtil.bindList(srs, BeanUtil.getClassName(object));
		return list;
	}
	

	/**
	 * 執行帶參數的查詢方法返回一個BEAN_LIST
	 * <p>
	 * 
	 * @param param1
	 *            type 描述
	 * @param param2
	 *            type 描述
	 * @see package.className[#method([param])]
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public List<?> queryForList(String sql, Object[] args, Object object) throws Exception {
		SqlRowSet srs = null;
		// srs = jdbcTemplate.queryForRowSet(sql, args);
		srs = (SqlRowSet) jdbcTemplate.query(sql, args, new SqlRowSetResultSetExtractor());
		List<?> list = BeanUtil.bindList(srs, BeanUtil.getClassName(object));
		return list;
	}

	@Override
	public List<?> queryForList(String sql, Object[] param, Class<?> c)
			throws Exception {
		SqlRowSet srs = null;
		srs = (SqlRowSet) jdbcTemplate.query(sql, param, new SqlRowSetResultSetExtractor());
		List<?> list = BeanUtil.bindList(srs, BeanUtil.getClassName(c.getName()));
		return list;
	}
	/**
	 * 執行不帶參數的查詢方法返回一個Object對象
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public Object queryForObject(String sql, Object object) throws Exception {
		SqlRowSet srs = null;
		// srs = jdbcTemplate.queryForRowSet(sql);
		srs = (SqlRowSet) jdbcTemplate.query(sql, new SqlRowSetResultSetExtractor());
		Object obj = BeanUtil.bindObject(srs, BeanUtil.getClassName(object));
		return obj;
	}

	/**
	 * 執行帶參數的查詢方法返回一個Object對象
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 * @throws Exception
	 */
	public Object queryForObject(String sql, Object[] args, Object object) throws Exception {
		SqlRowSet srs = null;
		// srs = jdbcTemplate.queryForRowSet(sql, args);
		srs = (SqlRowSet) jdbcTemplate.query(sql, args, new SqlRowSetResultSetExtractor());
		Object obj = BeanUtil.bindObject(srs, BeanUtil.getClassName(object));
		return obj;
	}

	/**
	 * 執行不帶參數的查詢方法返回一個Integer類型數據，可以用于查詢某個整形字段的值
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public int queryForInt(String sql) throws Exception {
		//直接調用此方法，在查不出數據的情況下會報錯
		//int a = jdbcTemplate.queryForInt(sql);
		//自己寫的方法
		int a=0;
		String object=StringUtil.trimString(queryForObject(sql,Integer.class));
		if(object.length()>0){
			a=Integer.parseInt(object);
		}
		
		return a;
	}

	/**
	 * 執行不帶參數的查詢方法返回一個Long類型數據，可以用于查詢某個整形字段的值,也可用于統計某個SQL語句的數目count(*)
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public long queryForLong(String sql) throws Exception {
		long a=0l;
		String object=StringUtil.trimString(queryForObject(sql,Long.class));
		if(object.length()>0){
			a=Long.parseLong(object);
		}
		return a;
	}

	/**
	 * 執行帶參數的查詢方法返回一個Long類型數據，可以用于查詢某個整形字段的值,也可用于統計某個SQL語句的數目count(*)
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public long queryForLong(String sql, Object[] args) throws Exception {
		long a=0l;
		String object=StringUtil.trimString(queryForObject(sql,args,Long.class));
		if(object.length()>0){
			a=Long.parseLong(object);
		}
		return a;
	}

	/**
	 * 執行不帶參數的查詢方法返回一個String類型數據，可以用于查詢數據表中具體某一字符型字段的值，注：只能返回一個值
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public String queryForString(String sql) throws Exception {
		String str = StringUtil.trimString(queryForObject(sql, String.class));
		return str;
	}

	/**
	 * 執行帶參數的查詢方法返回一個String類型數據，可以用于查詢數據表中具體某一字符型字段的值，注：只能返回一個值
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public String queryForString(String sql, Object[] args) throws Exception {
		String str = StringUtil.trimString(queryForObject(sql, args, String.class));
		return str;
	}

	/**
	 * 獲取sequence的值，synchronized
	 * <p>
	 * 
	 * @param param1
	 *            type 描述
	 * @param param2
	 *            type 描述
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	@SuppressWarnings("deprecation")
	public long getSequencesNextValue(String sequencesName) throws SQLException {
		long sequencesValue = -1;
		String sql = "select  " + sequencesName + ".nextval from dual";
		sequencesValue = jdbcTemplate.queryForLong(sql);
		return sequencesValue;
	}

	/**
	 * 執行不帶參數的數據更新操作
	 * <p>
	 * 
	 * @param param1
	 *            type 描述
	 * @param param2
	 *            type 描述
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public void executeUpdate(String sql) throws SQLException {
		DataSource dataSource = jdbcTemplate.getDataSource();
		Connection conn  =  dataSource.getConnection();
		// 设定不自动提交   
		conn.setAutoCommit(false); 
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(sql);			
			conn.commit();
			
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
	}

	/**
	 * 執行帶參數的數據更新操作
	 * <p>
	 * 
	 * @param param1
	 *            type 描述
	 * @param param2
	 *            type 描述
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public void executeUpdate(String sql, Object[] args) throws SQLException {
		String[] columnNames = null;
		if (args != null) {
			columnNames = new String[args.length];
			for (int i = 0; i < args.length; i++) {
				args[i]=StringUtil.trimObject(args[i]);
				columnNames[i] = args[i].toString();
			}
		}
		DataSource dataSource = jdbcTemplate.getDataSource();
		Connection conn  =  dataSource.getConnection();
		// 设定不自动提交   
		conn.setAutoCommit(false); 
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(sql, columnNames);		
			conn.commit();
			
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		
	}

	/**
	 * 批處理執行多條SQL語句，注：SQL語句中沒有參數
	 * <p>
	 * 例子： String[] sql=new String[2]; sql[0] = "update table1 set column1=2
	 * where id=1"; sql[1] = "update table2 set column3=3 where id=4";
	 * 
	 * @param param1
	 *            type 描述
	 * @param param2
	 *            type 描述
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public void batchUpdate(String[] sql) throws SQLException {
		DataSource dataSource = jdbcTemplate.getDataSource();
		Connection conn  =  dataSource.getConnection();
		// 设定不自动提交   
		conn.setAutoCommit(false); 
		Statement statement = null;
		try {
			statement = conn.createStatement();
			if(sql!=null && sql.length>0){
				for (int i = 0; i < sql.length; i++) {
					statement.addBatch(sql[i]);
				}
			}
			statement.executeBatch();
			conn.commit();
			
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} finally {
			if (statement != null) {
				statement.clearBatch();
				statement.close();
				statement = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
	}

	/**
	 * 
	 * 打印SQL語句時使用的方法，args可以為空
	 * 
	 * @param sql
	 *            String sql語句
	 * @param args
	 *            Object[] 一維數組
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public String toSqlString(String sql, Object[] args) throws SQLException {
		String str = sql;
		try {
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					try {
						str = str.replaceFirst("\\?", "'" + args[i] + "'");
					} catch (PatternSyntaxException e) {
						e.printStackTrace();
					}
				}
			} else {
				LOG.error("DB.toSqlString:pram is null");
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		LOG.info(str);
		return str;
	}
	
	/**
	 * 打印批處理SQL語句時使用的方法，args可以為空---批處理
	 * <p>
	 * 
	 * @param sql 
	 * 		String sql語句
	 * @param args 
	 * 		Object[][] 二維數組
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return String 返回整合參數之后的sql語句
	 */
	public String toSqlStringBatch(String sql, Object[][] args) throws Exception{
		String str = "";
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				Object[] objects = args[i];
				str=toSqlString(sql,objects)+";"+str;
			}
		}else{
			LOG.info(sql);
		}
		return str;
	}
	
	/**
	 * 返回ResultSet結果集
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return ResultSet
	 */
	public ResultSet queryForResultSet(String sql) throws Exception{
		 
		ResultSetWrappingSqlRowSet srs = (ResultSetWrappingSqlRowSet) jdbcTemplate.query(sql, new SqlRowSetResultSetExtractor());
		return srs.getResultSet();
	}
	
	/**
	 * 返回ResultSet結果集，參數帶變量
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param args
	 *            Object[] 參數變量
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return ResultSet
	 */
	public ResultSet queryForResultSet(String sql, Object[] args) throws Exception{
		
		ResultSetWrappingSqlRowSet srs = (ResultSetWrappingSqlRowSet) jdbcTemplate.query(sql,args, new SqlRowSetResultSetExtractor());
		return srs.getResultSet();
	}
	
	
	/**
	 * 更新方法，執行成功返回受影響的記錄數,sql不帶參數
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return int 返回受影響的記錄數
	 */
	public int executeUpdateForRow(String sql) throws Exception{
		DataSource dataSource = jdbcTemplate.getDataSource();
		Connection conn  =  dataSource.getConnection();
		// 设定不自动提交   
		conn.setAutoCommit(false); 
		Statement statement = null;
		int i = -1;
		try {
			statement = conn.createStatement();
			i = statement.executeUpdate(sql);			
			conn.commit();
			
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return i;
	}
	
	/**
	 * 更新方法，執行成功返回受影響的記錄數,sql帶參數
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param args
	 *            Object[] sql參數變量
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return int 返回受影響的記錄數
	 */
	public int executeUpdateForRow(String sql,Object[] args) throws SQLException{
		String[] columnNames = null;
		if (args != null) {
			columnNames = new String[args.length];
			for (int i = 0; i < args.length; i++) {
				args[i]=StringUtil.trimObject(args[i]);
				columnNames[i] = args[i].toString();
			}
		}
		DataSource dataSource = jdbcTemplate.getDataSource();
		Connection conn  =  dataSource.getConnection();
		// 设定不自动提交   
		conn.setAutoCommit(false); 
		Statement statement = null;
		int i = -1;
		try {
			statement = conn.createStatement();
			i = statement.executeUpdate(sql, columnNames);		
			conn.commit();
			
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return i;
	}
	
	/**
	 * 更新方法，含手動提交功能,sql不帶參數
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return void
	 */
	public void executeUpdateForCommit(String sql) throws Exception{
		DataSource dataSource = jdbcTemplate.getDataSource();
		Connection conn  =  dataSource.getConnection();
		// 设定不自动提交   
		conn.setAutoCommit(false); 
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(sql);			
			conn.commit();
			
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
	}
	
	/**
	 * 更新方法，含手動提交功能,sql帶參數
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param args
	 *            Object[] sql參數變量
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return void
	 */
	public void executeUpdateForCommit(String sql,Object[] args) throws SQLException{
		PreparedStatement preparedStatement = null;
		DataSource dataSource = jdbcTemplate.getDataSource();
		Connection conn  =  dataSource.getConnection();
		// 设定不自动提交   
		conn.setAutoCommit(false); 
		try {			
			preparedStatement = conn.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setObject(i + 1, args[i]);
				}
			}
			preparedStatement.executeUpdate();
			conn.commit();
			
		} catch (SQLException e) {			
			conn.rollback();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
	}

	public void execStoredProcedure(String sql) throws SQLException {
		DataSource dataSource = jdbcTemplate.getDataSource();
		Connection conn  =  dataSource.getConnection();
		Statement statement = null;
		try {
			statement = conn.createStatement();
			statement.executeUpdate(sql);

			if (statement != null) {
				statement.close();
			}

		} catch (SQLException ex) {
			conn.rollback();
			throw ex;
		}finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cnnct.common.component.jdbc.BaseJdbcService#callProcedure(java.lang.String, java.lang.Object[], java.lang.Object[])
	 */
	@Override
	public Object[] callStoredProcedure(String sql, Object[] in, Object[] out) throws SQLException {
		Object[] back = null;
		DataSource dataSource = jdbcTemplate.getDataSource();
		Connection conn = dataSource.getConnection();
		CallableStatement cstm = null;
		try {
			cstm = conn.prepareCall(sql);
			//入参设置
			int a = 1;
			if(in != null && in.length >=1){
				for(int i=0; i<in.length; i++){
					if(in[i] instanceof String){
						cstm.setString(i+1, in[i].toString());
					}else if(in[i] instanceof Integer){
						cstm.setInt(i+1, Integer.valueOf(in[i].toString()));
					}else if(in[i] instanceof Double){
						cstm.setDouble(i+1, Integer.valueOf(in[i].toString()));
					}else{
						cstm.setString(i+1, null);
					}
					a = i+2;
				}
			}
			//出参设置
			if(out != null && out.length >=1){
				for(int j=0; j<out.length; j++){
					if(out[j] instanceof String){
						cstm.registerOutParameter(j+a, Types.VARCHAR);
					}else if(out[j] instanceof Integer){
						cstm.registerOutParameter(j+a, Types.INTEGER);
					}else if(out[j] instanceof Double){
						cstm.registerOutParameter(j+a, Types.DOUBLE);
					}else{
						cstm.registerOutParameter(j+a, Types.NULL);
					}
				}
			}
			
			cstm.execute();
			
			if(out != null && out.length >=1){
				back = new String[out.length];
				for(int k=0; k<out.length; k++){
					if(out[k] instanceof String){
						back[k] = cstm.getString(k+a);
					}else if(out[k] instanceof Integer){
						back[k] = cstm.getInt(k+a);
					}else if(out[k] instanceof Double){
						back[k] = cstm.getDouble(k+a);
					}else{
						back[k] = null;
					}
				}
			}
			
			if (cstm != null) {
				cstm.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (cstm != null) {
					cstm.close();
				}
			} catch (Exception e) {
			}
		}
		return back;
	}


    public Map<String, Object> queryForMap(String sql,Object... args){
        return jdbcTemplate.queryForMap(sql,args);
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
    
    public static void main(String[] args){
    	BizTransRecord record=new BizTransRecord();
    	System.out.println(BeanUtil.getClassName(record));
    	System.out.println(BeanUtil.getClassName(BizTransRecord.class));
    	
    }

}
