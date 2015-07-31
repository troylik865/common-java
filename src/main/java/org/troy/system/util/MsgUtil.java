package org.troy.system.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.troy.system.entity.MsgSendResult;

/**
 * 
 * 
 * @author troy
 * @version $Id: MsgUtil.java, v 0.1 2014年6月25日 下午5:55:07 troy Exp $
 */
public class MsgUtil {

    private static final Logger logger         = Logger.getLogger(MsgUtil.class);

    /**
     * 短信发送的调用地址
     */
    private static final String URL            = PropertiesUtil.getValue("sms_url");

    /**
     * 短信账号的用户名
     */
    private static final String USER_NAME      = PropertiesUtil.getValue("sms_user_name");

    /**
     * 短信账号的密码
     */
    private static final String PASSWORD       = PropertiesUtil.getValue("sms_password");

    /**
     * 是否启动真实的短信
     */
    private static final String IS_USE_MESSAGE = PropertiesUtil.getValue("is_use_message");

    public static MsgSendResult sendMsg(final String msgContent, String phoneNumber) {
        MsgSendResult result = new MsgSendResult();

        //如果不需要开始真实短信，则直接返回结果对象
        if (!StringUtils.equals(IS_USE_MESSAGE, "1")) {
            return result;
        }
        try {
            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod(URL);
            HttpClientParams params = client.getParams();
            client.getParams().setContentCharset("UTF-8");
            params.setSoTimeout(60000);
            params.setConnectionManagerTimeout(30000);
            method.setRequestHeader("ContentType",
                "application/x-www-form-urlencoded;charset=UTF-8");
            NameValuePair[] data = {//提交短信
            new NameValuePair("account", USER_NAME), new NameValuePair("password", PASSWORD),
                    new NameValuePair("mobile", phoneNumber),
                    new NameValuePair("content", msgContent) };
            method.setRequestBody(data);
            client.executeMethod(method);
            String submitResult = method.getResponseBodyAsString();
            System.out.println(submitResult);
            buildResult(submitResult, result);
        } catch (Exception e) {
            logger.error("短信发送异常!", e);
            result.setSuc(false);
        }
        return result;
    }

    /**
     * 组装短信发送应答对象
     * 
     * @param xml                   应答内容
     * @param result                结果
     * @throws DocumentException    文档解析异常
     */
    private static void buildResult(String xml, MsgSendResult result) throws DocumentException {
        Document doc = DocumentHelper.parseText(xml);
        Element root = doc.getRootElement();
        String code = root.elementText("code");
        if (code == "2") {
            String smsid = root.elementText("smsid");
            result.setSuc(true);
            result.setMsgId(smsid);
            result.setResultCode(code);
            result.setResultMsg("短信提交成功");
        } else {
            result.setSuc(false);
            result.setResultCode(code);
            result.setResultMsg(root.elementText("msg"));
        }
    }
}
