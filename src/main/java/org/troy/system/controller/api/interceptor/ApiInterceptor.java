package org.troy.system.controller.api.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.troy.biz.controller.api.APIConst;
import org.troy.common.utils.RSAUtil;
import org.troy.system.cache.SecretkeyCache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
/**
 * 自定义拦截器,处理API接口的解密和参数转换
 * @author 杨磊
 * @since 2014-5-21 上午12:04:59
 * Copyright © 2013 - 2014 CNNCT
 */
public class ApiInterceptor implements HandlerInterceptor {
    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     *
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
	@Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
        Map<String,Object> responseMap = Maps.newConcurrentMap();
        String cipher = request.getParameter("cipher");
        String source =  request.getParameter("source");
        String sign =  request.getParameter("sign");
        
        //这里可以加入接口调用方的ip限制,也可以根据source的不同设置相应的接口访问权限
        
        if(StringUtils.isBlank(cipher)||StringUtils.isBlank(source)||StringUtils.isBlank(sign)){
            responseMap.put(APIConst.KEY_RESULT_CODE, HttpStatus.SC_BAD_REQUEST);
            responseMap.put(APIConst.KEY_RESULT_MSG,"参数缺失,请检查!");
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            response.getWriter().println(JSON.toJSONString(responseMap));
            return false;
        }
        
        //取得商户的公钥
        String publicKey = SecretkeyCache.getPublicKeyString(source);
        if(StringUtils.isBlank(publicKey)){
            responseMap.put(APIConst.KEY_RESULT_CODE,HttpStatus.SC_INTERNAL_SERVER_ERROR);
            responseMap.put(APIConst.KEY_RESULT_MSG,"用户密钥无效,请联系系统管理员!");
            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(JSON.toJSONString(responseMap));
            return false;
        }
        // 验证签名
        boolean isVerifyed = RSAUtil.verify(cipher, publicKey, sign);
        if(isVerifyed){
            //公钥解密
            String decodedData = RSAUtil.decryptByPublicKey(cipher, publicKey);
            JSONObject json = JSON.parseObject(decodedData).getJSONObject(APIConst.JSON_ROOT_KEY);
            request.setAttribute(APIConst.JSON_DATA_KEY, json);
            return true;
        } else {
            responseMap.put(APIConst.KEY_RESULT_CODE,HttpStatus.SC_INTERNAL_SERVER_ERROR);
            responseMap.put(APIConst.KEY_RESULT_MSG,"数据解密失败,请检查参数!");
            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(JSON.toJSONString(responseMap));
            return false;
        }
    }

    /***
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     *   当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
