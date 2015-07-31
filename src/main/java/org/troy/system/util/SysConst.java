package org.troy.system.util;


/**
 * 系统配置类
 * 
 * @author wangj
 * 2013-5-17
 */

public interface SysConst {
	/**
	 * 登录用户
	 */
	public final static String LOGIN_USER = "user";
	/**
	 * 系统用户
	 */
	public final static String SYSTEM_USER = "admin";
	/**
	 * 系统IP
	 */
	public final static String SYSTEM_IP = "127.0.0.1";
	/**
	 * 存放tree的json字符串
	 */
	public final static String TREE_JSON = "tree_json";    
	
	/**
	 * 存放tree的根节点
	 */
	public final static String TREE_ROOTID = "1";    
	/**
	 * 根节点ID
	 */
	public final static Long ROOTID = 1l;   
	
	/**
	 * 日志参数
	 */
	public final static String LOG_ARGUMENTS = "log_arguments";
	
	/**
	 * 异常信息统一头信息<br>
	 * 非常遗憾的通知您,程序发生了异常
	 */
	public static final String Exception_Head = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS. ";
	
}
