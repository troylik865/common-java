package org.troy.system.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.troy.common.utils.DateUtil;
import org.troy.system.service.AreaService;
import org.troy.system.service.GroupService;

/**
 * 系统启动监听器
 * 
 * @author wangj
 * @since 2013-07-08
 */
public class SystemInitListener implements ServletContextListener  {
	private static Log log = LogFactory.getLog(SystemInitListener.class);
	private boolean success = true;
	private ContextLoader contextLoader; 
	private ApplicationContext wac = null;

    /**
     * Initialize the root web application context.
     */ 
    public void contextInitialized(ServletContextEvent event) { 
        this.contextLoader = createContextLoader(); 
        systemStartup(event.getServletContext());
    } 
 
    protected ContextLoader createContextLoader() { 
        return new ContextLoader(); 
    } 
 
    public ContextLoader getContextLoader() { 
        return this.contextLoader; 
    } 
 
    public void contextDestroyed(ServletContextEvent event) { 
        if (this.contextLoader != null) { 
            this.contextLoader.closeWebApplicationContext(event.getServletContext()); 
        } 
    } 
	/**
	 * 应用平台启动
	 */
	private void systemStartup(ServletContext servletContext) {
		long start = System.currentTimeMillis();
		log.info("*******************************************************");
		log.info("系统开始启动...");
		log.info("*******************************************************");
		try {
			log.info("系统正在初始化服务容器...");
			wac = this.contextLoader.initWebApplicationContext(servletContext);
			SpringBeanLoader.setApplicationContext(wac);
			log.info("容器初始化成功啦，您的托管Bean已经被实例化。");
			
		} catch (Exception e) {
			success = false;
			log.error("服务容器初始化失败.");
			log.error(SysConst.Exception_Head + "初始化服务容器发生错误,请仔细检查您的配置文件喔!\n" + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		if (success) {
			log.info("系统开始启动组织机构装载程序...");
			log.info("开始加载组织机构...");
			GroupService groupService = (GroupService)this.getSpringBean("groupServiceImpl");
			try {
				groupService.getTree();//查询组织机构
				log.info("组织机构加载成功!");
			} catch (Exception e) {
				success = false;
				log.error("组织机构加载失败!");
				e.printStackTrace();
			}
		}
		if (success) {
			log.info("系统开始启动行政区划装载程序...");
			log.info("开始加载行政区划...");
			AreaService areaService = (AreaService)this.getSpringBean("areaServiceImpl");
			try {
				areaService.getTree();//查询行政区划
				log.info("行政区划加载成功!");
			} catch (Exception e) {
				success = false;
				log.error("行政区划加载失败!");
				e.printStackTrace();
			}
		}
		long timeSec = (System.currentTimeMillis() - start) / 1000;
		log.info("********************************************");
		if (success) {
			log.info("系统启动成功[" + DateUtil.getCurrentTime() + "]");
			log.info("启动总耗时: " + timeSec / 60 + "分 " + timeSec % 60 + "秒 ");
		} else {
			log.error("系统启动失败[" + DateUtil.getCurrentTime() + "]");
			log.error("启动总耗时: " + timeSec / 60 + "分" + timeSec % 60 + "秒");
		}
		log.info("********************************************");
	}

	/**
	 * 获取一个SpringBean服务
	 * 
	 * @param pBeanId
	 *            Spring配置文件名中配置的SpringID号
	 * @return Object 返回的SpringBean实例
	 */
	public Object getSpringBean(String pBeanId) {
		Object springBean = null;
		try {
			springBean = wac.getBean(pBeanId);
		} catch (NoSuchBeanDefinitionException e) {
			log.error(SysConst.Exception_Head + "Spring配置文件中没有匹配到ID号为:[" + pBeanId + "]的SpringBean组件,请检查!");
			log.error(e.getMessage());
		}
		return springBean;
	}
}