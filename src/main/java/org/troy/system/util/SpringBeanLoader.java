package org.troy.system.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * SpringBwan加载器<br>
 * (1)、使用此加载器可以获得一个Spring容器的ApplicationContext实例,通过此实例你就可以方便的使用getBean()
 * 方法获取SpringBean.<br>
 * (2)、您也可以直接通过我们提供的getSpringBean()方法活得SpringBean。
 * 
 * @author wangj
 * @since 2013-07-08
 */
public class SpringBeanLoader{
	private static Log log = LogFactory.getLog(SpringBeanLoader.class);
	private static ApplicationContext applicationContext;

//	static {
//		try {
//			initApplicationContext();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 初始化ApplicationContext对象
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private static void initApplicationContext() throws Exception {
		try {
			log.info("系统正在初始化服务容器...");
			applicationContext = new ClassPathXmlApplicationContext("spring\\applicationContext.xml");
			log.info("容器初始化成功啦，您的托管Bean已经被实例化。");
		} catch (Exception e) {
			log.error("服务容器初始化失败.");
			log.error(SysConst.Exception_Head + "初始化服务容器发生错误,请仔细检查您的配置文件喔!\n" + e.getMessage());
			e.printStackTrace();
			System.exit(0);
			throw e;
		}
	}

	/**
	 * 返回ApplicationContext对象
	 * 
	 * @return ApplicationContext 返回的ApplicationContext实例
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		SpringBeanLoader.applicationContext = applicationContext;
	}

	/**
	 * 获取一个SpringBean服务
	 * 
	 * @param pBeanId
	 *            Spring配置文件名中配置的SpringID号
	 * @return Object 返回的SpringBean实例
	 */
	public static Object getSpringBean(String pBeanId) {
		Object springBean = null;
		try {
			springBean = applicationContext.getBean(pBeanId);
		} catch (NoSuchBeanDefinitionException e) {
			log.error(SysConst.Exception_Head + "Spring配置文件中没有匹配到ID号为:[" + pBeanId + "]的SpringBean组件,请检查!");
			log.error(e.getMessage());
		}
		return springBean;
	}
}
