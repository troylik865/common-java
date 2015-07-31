package org.troy.biz.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.troy.common.utils.DateUtil;

/**
 * 业务相关的启动监听器
 * 
 * @author troy
 * @version $Id: BizInitListener.java, v 0.1 2014年7月1日 下午11:20:11 troy Exp $
 */
public class BizInitListener implements ServletContextListener {
    private static Log    log     = LogFactory.getLog(BizInitListener.class);
    private boolean       success = true;
    private ContextLoader contextLoader;

    /**
     * Initialize the root web application context.
     */
    public void contextInitialized(ServletContextEvent event) {
        this.contextLoader = createContextLoader();
        bizStartup(event.getServletContext());
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
    private void bizStartup(ServletContext servletContext) {
        long start = System.currentTimeMillis();
        log.info("*******************************************************");
        log.info("业务监听开始启动...");
        log.info("*******************************************************");

        //执行业务信息初始化

        long timeSec = (System.currentTimeMillis() - start) / 1000;
        log.info("********************************************");
        if (success) {
            log.info("业务监听成功[" + DateUtil.getCurrentTime() + "]");
            log.info("启动总耗时: " + timeSec / 60 + "分 " + timeSec % 60 + "秒 ");
        } else {
            log.error("业务监听启动失败[" + DateUtil.getCurrentTime() + "]");
            log.error("启动总耗时: " + timeSec / 60 + "分" + timeSec % 60 + "秒");
        }
        log.info("********************************************");
    }

}