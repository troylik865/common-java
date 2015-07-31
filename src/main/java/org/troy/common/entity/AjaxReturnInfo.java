package org.troy.common.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;

/**
 * AJAX请求的返回内容  
 *  是一个内容是json格式的对象
 * 
 * @author troy
 * @version $Id: AjaxReturnInfo.java, v 0.1 2014年6月27日 上午10:20:08 troy Exp $
 */
public class AjaxReturnInfo {

    /**
     * 标示成功
     */
    private static final String SUCCESS     = "200";

    /**
     * 标示失败
     */
    private static final String ERROR       = "503";

    private String              resultCode  = SUCCESS;

    private static final String SUCCESS_MSG = "成功";

    private static final String ERROR_MSG   = "成功";

    private String              resultMsg   = SUCCESS_MSG;

    private Object              data;

    public AjaxReturnInfo(String resultCode, String resultMsg, Object data) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = data;
    }

    /**
     * 对象转换成json格式
     * @see java.lang.Object#toString()
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", this.resultCode);
        jsonObject.put("resultMsg", this.resultMsg);
        jsonObject.put("data", this.data);
        return jsonObject.toJSONString();
    }

    public static String returnSuc() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", SUCCESS);
        jsonObject.put("resultMsg", SUCCESS_MSG);
        return jsonObject.toJSONString();
    }

    public static String returnSuc(String successMsg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", SUCCESS);
        jsonObject.put("resultMsg", successMsg);
        return jsonObject.toJSONString();
    }

    public static String returnSuc(String successMsg, Object obj) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", SUCCESS);
        jsonObject.put("resultMsg", successMsg);
        jsonObject.put("data", obj);
        return jsonObject.toJSONString();
    }

    public static String returnSuc(Object obj) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", SUCCESS);
        jsonObject.put("resultMsg", SUCCESS_MSG);
        jsonObject.put("data", obj);
        return jsonObject.toJSONString();
    }

    public static String returnErr() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", ERROR);
        jsonObject.put("resultMsg", ERROR_MSG);
        return jsonObject.toJSONString();
    }

    public static String returnErr(String errorMsg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", ERROR);
        jsonObject.put("resultMsg", errorMsg);
        return jsonObject.toJSONString();
    }

    public static String returnNotLogin() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", ERROR);
        jsonObject.put("resultMsg", "请您先首页登录！");
        return jsonObject.toJSONString();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
