package org.troy.system.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 扩展异常类，为了在页面能更精准显示错误提示信息。
 * 
 * @author wangj
 * 2013-5-17
 */

public class IncorrectCaptchaException extends AuthenticationException {
	/** 描述  */
	private static final long serialVersionUID = 6146451562810994591L;

	public IncorrectCaptchaException() {
		super();
	}

	public IncorrectCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectCaptchaException(String message) {
		super(message);
	}

	public IncorrectCaptchaException(Throwable cause) {
		super(cause);
	}

}
