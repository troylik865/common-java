package org.troy.common.component.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.troy.common.utils.dwz.Page;

/**
 * 對SPRING的JdbcTemplete進行再封裝接口
 *<p>
 * <ul>
 * <li>方法1.不帶綁定變量
 * <li>方法2.帶綁定變量
 * </ul>
 * @author wangj
 * 2013-5-30
 */

public interface BaseJdbcService {
	
	/**
	 *  執行不帶參數的查詢方法返回HashMap集合
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> executeQueryForMapList(String sql) throws SQLException;
	
	/**
	 *  分页 查詢方法返回HashMap集合
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> executeQueryPageForMapList(Page page,String sql) throws Exception;
	
	/**
	 *  執行不帶參數的查詢方法返回object数组对象集合
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<Object[]> executeQueryForObjList(String sql) throws SQLException;

	/*******************************************************************************/
	/**
	 * 執行不帶參數的查詢方法返回帶列名的二維數組
	 * <p>
	 * 
	 * @param sql
	 *            type sql語句
	 * @param param2
	 *            type 描述
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public String[][] executeQuery(String sql) throws SQLException;
	/**
	 * 執行帶參數的查詢方法返回帶列名的二維數組
	 * <p>
	 * 
	 * @param sql
	 *            type sql語句
	 * @param Object
	 *            type 綁定變量數組 
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public String[][] executeQuery(String sql,Object[] args) throws SQLException;
	
	public int queryForInt(String sql, Object... args);
	
	
	/**
	 * 執行帶參數的查詢方法返回一個BEAN_LIST
	 * @param sql sql语句
	 * @param param 参数
	 * @param c 需要返回class类型
	 * @return
	 * @throws Exception
	 */
	
	public List<?> queryForList(String sql,Object[] param,Class<?> c) throws Exception;
	
	/**
	 * 執行帶參數的查詢方法返回一個BEAN_LIST
	 * <p>
	 * 
	 * @param param1
	 *            type 描述
	 * @param param2
	 *            type 描述
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public List<?> queryForList(String sql,Object[] args,Object object) throws Exception;
	
	
	
	/**
	 * 執行不帶參數的查詢方法返回一個Object對象
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public Object queryForObject(String sql,Object object) throws Exception;
	
	/**
	 * 執行帶參數的查詢方法返回一個Object對象
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public Object queryForObject(String sql,Object[] args,Object object) throws Exception;
	
	/**
	 * 執行不帶參數的查詢方法返回一個Integer類型數據，可以用于查詢某個整形字段的值
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public int queryForInt(String sql) throws Exception;
	
	/**
	 * 執行不帶參數的查詢方法返回一個Long類型數據，可以用于查詢某個整形字段的值,也可用于統計某個SQL語句的數目count(*)
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public long queryForLong(String sql) throws Exception;
	
	
	/**
	 * 執行帶參數的查詢方法返回一個Long類型數據，可以用于查詢某個整形字段的值,也可用于統計某個SQL語句的數目count(*)
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public long queryForLong(String sql,Object[] args) throws Exception;
	
	/**
	 * 執行不帶參數的查詢方法返回一個String類型數據，可以用于查詢數據表中具體某一字符型字段的值，注：只能返回一個值
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public String queryForString(String sql) throws Exception;
	
	/**
	 * 執行帶參數的查詢方法返回一個String類型數據，可以用于查詢數據表中具體某一字符型字段的值，注：只能返回一個值
	 * <p>
	 * 
	 * @param sql
	 *            String sql語句
	 * @param object
	 *            Object 返回對象實例
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public String queryForString(String sql,Object[] args) throws Exception;
	
	
	/**
	 * 獲取sequence的值，synchronized
	 * <p>
	 * 
	 * @param param1
	 *            type 描述
	 * @param param2
	 *            type 描述
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public long getSequencesNextValue(String sequencesName) throws SQLException;
	
	/**
	 * 執行不帶參數的數據更新操作
	 * <p>
	 * 
	 * @param param1
	 *            type 描述
	 * @param param2
	 *            type 描述
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public void executeUpdate(String sql) throws Exception;
	
	/**
	 * 執行帶參數的數據更新操作
	 * <p>
	 * 
	 * @param param1
	 *            type 描述
	 * @param param2
	 *            type 描述
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public void executeUpdate(String sql,Object[] args) throws SQLException;
	
	/**
	 * 批處理執行多條SQL語句，注：SQL語句中沒有參數
	 * <p>
	 * 例子：
	 * String[] sql=new String[2];
	 * sql[0] = "update table1 set column1=2 where id=1";
	 * sql[1] = "update table2 set column3=3 where id=4";
	 * 
	 * @param param1
	 *            type 描述
	 * @param param2
	 *            type 描述
	 *            <ul>
	 *            <li>value1描述
	 *            <li>value2描述
	 *            </ul>
	 * @throws exceptionType
	 *             如果...將拋出exceptionType異常.
	 * @return returnType 返回類型描述
	 */
	public void batchUpdate(String[] sql) throws SQLException;
	
	/**
	 * 
	 * 打印SQL語句時使用的方法，args可以為空
	 * @param sql String sql語句
	 * @param args Object[] 一維數組
	 * @throws exceptionType 如果...將拋出exceptionType異常.
	 * @return String 返回整合參數之后的sql語句
	 */
	public String toSqlString(String sql, Object[] args) throws SQLException;
	
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
	public String toSqlStringBatch(String sql, Object[][] args) throws Exception;
	
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
	public ResultSet queryForResultSet(String sql) throws Exception;
	
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
	public ResultSet queryForResultSet(String sql, Object[] args) throws Exception;
	
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
	public int executeUpdateForRow(String sql) throws Exception;
	
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
	public int executeUpdateForRow(String sql,Object[] args) throws SQLException;
	
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
	public void executeUpdateForCommit(String sql) throws Exception;
	
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
	public void executeUpdateForCommit(String sql,Object[] args) throws SQLException;
	
	/**
	 * 执行存储过程，不返回结果集
	 * 
	 * @param sql
	 *            存储过程的调用语句，格式：{call <ProcName>(<ParamList>)}
	 */
	public void execStoredProcedure(String sql) throws SQLException;
	
	/**
	 * 执行存储过程，返回结果
	 * 
	 * @param sql
	 * @param Object[] 传入参数
	 * @param Object[] 输出参数类型
	 *            存储过程的调用语句，格式：{call <ProcName>(<ParamList>)}
	 */
	public Object[] callStoredProcedure(String sql,Object[] in,Object[] out) throws SQLException;


    public Map<String, Object> queryForMap(String sql,Object... args);
	
}
