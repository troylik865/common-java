package org.troy.system.controller;

import java.util.Map;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogLevel;
import org.troy.common.utils.dwz.AjaxObject;


/**
 * 系统后台登陆Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
	private static final String LOGIN_PAGE = "login";
	private static final String LOGIN_DIALOG = "system/index/loginDialog";
	private static final String INDEX = "system/index";

	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return LOGIN_PAGE;
	}

	@RequestMapping(method = {RequestMethod.GET}, params="ajax=true")
	public @ResponseBody
	String loginDialog2AJAX() {
		return loginDialog();
	}
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.HEAD }, headers = "x-requested-with=XMLHttpRequest")
	public @ResponseBody
	String loginDialog() {
		AjaxObject ajaxObject = new AjaxObject("会话超时，请重新登录。");
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_TIMEOUT);
		ajaxObject.setCallbackType(AjaxObject.CALLBACK_TYPE_CLOSE_CURRENT);

		return ajaxObject.toString();
	}

	@RequestMapping(value = "/timeout", method = { RequestMethod.GET })
	public String timeout() {
		return LOGIN_DIALOG;
	}

	@Log(message="会话超时， 该用户重新登录系统。", level=LogLevel.LOGON)
	@RequestMapping(value="/timeout/success", method={RequestMethod.GET, RequestMethod.POST})
	public  String timeoutSuccess() {
		return INDEX;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String login(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username,	Map<String, Object> map) {
		String msg = parseException();
		map.put("msg", msg);
		map.put("username", username);
		return LOGIN_PAGE;
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.HEAD }, headers = "x-requested-with=XMLHttpRequest")
	public @ResponseBody
	String failDialog() {
		String msg = parseException();
		
		AjaxObject ajaxObject = new AjaxObject(msg);
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FAILURE);
		ajaxObject.setCallbackType("");

		return ajaxObject.toString();
	}

	private String parseException() {
		String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String msg = "已有账号登录！点主页直接进入";
		if (error != null) {
			if ("org.apache.shiro.authc.UnknownAccountException".equals(error))
				msg = "未知帐号错误！";
			else if ("org.apache.shiro.authc.IncorrectCredentialsException"
					.equals(error))
				msg = "密码错误！";
			else if ("com.cnnct.system.shiro.IncorrectCaptchaException"
					.equals(error))
				msg = "验证码错误！";
			else if ("org.apache.shiro.authc.AuthenticationException"
					.equals(error))
				msg = "认证失败！";
			else if ("org.apache.shiro.authc.DisabledAccountException"
					.equals(error))
				msg = "账号被冻结！";
		}
		return "登录失败，" + msg;
	}
}
