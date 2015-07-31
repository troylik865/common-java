 
package org.troy.system.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * Shiro Security filter 
 * 
 * @author wangj
 * 2013-5-17
 */
public class BaseFormAuthenticationFilter extends FormAuthenticationFilter {

	//private static final Logger log = LoggerFactory.getLogger(BaseFormAuthenticationFilter.class); 
	
	/**
	 * 覆盖默认实现，用sendRedirect直接跳出框架，以免造成js框架重复加载js出错。
	 * @param token
	 * @param subject
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception  
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
		//issueSuccessRedirect(request, response);
		//we handled the success redirect directly, prevent the chain from continuing:
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) 
				|| request.getParameter("ajax") == null) {// 不是ajax请求
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + this.getSuccessUrl());
		} else {
			httpServletRequest.getRequestDispatcher("/login/timeout/success").forward(httpServletRequest, httpServletResponse);
		}
		
		return false;
	}
	
	
	/**      
	 * 
	 * * 解决多账号同时登陆 异常，支持同时登陆  
	 * * @param request      
	 * * @param response      
	 * * @param mappedValue      
	 * * @return        
	 * * @see org.apache.shiro.web.filter.authc.AuthenticatingFilter#isAccessAllowed(javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.Object)      
	 *     
	@Override    
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		try {             
			// 先判断是否是登录操作             
			if (isLoginSubmission(request, response)) { 
				if (log.isTraceEnabled()) { 
					log.trace("Login submission detected.  Attempting to execute login."); 
				}              
				  return false;       
			   }      
			} catch (Exception e) { 
				log.error(Exceptions.getStackTraceAsString(e)); 
			}          
		     return super.isAccessAllowed(request, response, mappedValue); 
	}*/
}
