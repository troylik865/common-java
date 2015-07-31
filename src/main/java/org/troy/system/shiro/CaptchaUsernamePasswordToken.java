package org.troy.system.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Shiro 表单认证，页面提交的用户名密码等信息，用 UsernamePasswordToken 类来接收
 * 扩展 UsernamePasswordToken加入验证码
 * 
 * @author wangj
 * 2013-5-17
 */

public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
	/** 描述 */
	private static final long serialVersionUID = -3178260335127476542L;
	/** 验证码 */
	private String captcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken() {
		super();
	}

	public CaptchaUsernamePasswordToken(String username, String password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

}
