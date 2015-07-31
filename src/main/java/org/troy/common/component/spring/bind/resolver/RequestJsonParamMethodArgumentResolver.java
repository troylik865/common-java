/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.troy.common.component.spring.bind.resolver;

import java.lang.reflect.Type;

import javax.servlet.ServletException;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.troy.common.component.spring.bind.annotation.RequestJsonParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 解析请求参数json字符串 
 * 
 * @author Zhang Kaitao
 * @since 3.1
 */
public class RequestJsonParamMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver implements WebArgumentResolver {
    
	public RequestJsonParamMethodArgumentResolver() {
		super(null);
	}
	
	public boolean supportsParameter(MethodParameter parameter) {
	    if (parameter.hasParameterAnnotation(RequestJsonParam.class)) {
		    return true;
		}
		return false;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
	    RequestJsonParam annotation = parameter.getParameterAnnotation(RequestJsonParam.class);
		return new RequestJsonParamNamedValueInfo(annotation); 
				
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
		RequestJsonParam annotation = parameter.getParameterAnnotation(RequestJsonParam.class);
		JSONObject paramValue = (JSONObject)webRequest.getAttribute(annotation.value(), 0);
        try {
        	Type type = parameter.getGenericParameterType();
    	    return JSON.parseObject(JSON.toJSONString(paramValue),type);
        } catch (Exception e) {
            throw new Exception("Could not read request json parameter", e);
        }
	}
	

	@Override
	protected void handleMissingValue(String paramName, MethodParameter parameter) throws ServletException {
	    String paramType = parameter.getParameterType().getName();
	    throw new ServletRequestBindingException(
                "Missing request json parameter '" + paramName + "' for method parameter type [" + paramType + "]");
	}

	
	
	private class RequestJsonParamNamedValueInfo extends NamedValueInfo {

		private RequestJsonParamNamedValueInfo() {
			super("", false, null);
		}
		
		private RequestJsonParamNamedValueInfo(RequestJsonParam annotation) {
			super(annotation.value(), annotation.required(), null);
		}
	}

	/**
	 * spring 3.1之前
	 */
    @Override
    public Object resolveArgument(MethodParameter parameter, NativeWebRequest request) throws Exception {
        if(!supportsParameter(parameter)) {
            return WebArgumentResolver.UNRESOLVED;
        }
        return resolveArgument(parameter, null, request, null);
    }
}