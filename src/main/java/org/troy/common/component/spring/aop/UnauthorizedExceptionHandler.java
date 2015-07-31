package org.troy.common.component.spring.aop;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.troy.common.utils.dwz.AjaxObject;
/**
 * 处理权限不足导致UnauthorizedException异常的类。返回带错误信息和状态码的ajaxobject字符串。
 * 	
 * @author wangj
 * 2013-5-19
 */
@ControllerAdvice
public class UnauthorizedExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { UnauthorizedException.class })
	public final ResponseEntity<String> handleException(UnauthorizedException ex, WebRequest request) {
		AjaxObject ajaxObject = AjaxObject.newError("用户权限不足。");
		ajaxObject.setStatusCode(AjaxObject.STATUS_CODE_FORBIDDEN);
		return new ResponseEntity<String>(ajaxObject.toString(), HttpStatus.OK);
	}
	
}
