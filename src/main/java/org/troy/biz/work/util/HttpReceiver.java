package org.troy.biz.work.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.troy.biz.entity.BizMember;

/**
 * 获取HttpServletRequest等类的类
 * 
 * @author troy
 * @version $Id: HttpReceiver.java, v 0.1 2014年7月1日 上午9:42:36 troy Exp $
 */
public class HttpReceiver {

    /**
     * 获取当前的请求对象
     * 
     * @return
     */
    public static HttpServletRequest getHttpServletReqeuest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 获取session
     * 
     * @return
     */
    public static HttpSession getHttpSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();
        return request.getSession();
    }

    /**
     * 获取当前登陆人
     * 
     * @return
     */
    public static BizMember getCurrentMember() {
        return (BizMember) getHttpSession().getAttribute("member");
    }

    public static String getParameter(String key) {
        return getHttpServletReqeuest().getParameter(key);
    }
}
