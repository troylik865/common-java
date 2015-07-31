package org.troy.biz.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizMobileMsg;
import org.troy.biz.enums.MsgUseEnum;
import org.troy.biz.service.MemberService;
import org.troy.biz.service.MobileMsgService;
import org.troy.common.entity.AjaxReturnInfo;
import org.troy.system.controller.ViewController;
import org.troy.system.entity.MsgSendResult;
import org.troy.system.util.MsgUtil;

/**
 * 生成验证码
 * 
 * @author troy
 * @version $Id: ValidController.java, v 0.1 2014年6月26日 下午4:43:46 troy Exp $
 */
@Controller
@RequestMapping("/biz/mobile")
public class MobileMsgController extends ViewController {

    @Autowired
    private MobileMsgService mobileMsgService;

    @Autowired
    private MemberService    memberService;

    @RequestMapping(value = "/create")
    public @ResponseBody String create(HttpServletRequest request) {
        String phoneNumber = request.getParameter("phoneNumber");

        //如果页面上没有
        if (StringUtils.isBlank(phoneNumber)) {
            String userName = request.getParameter("userName");
            BizMember member = memberService.getBizMemberByUserName(userName);
            if (null == member || StringUtils.isBlank(phoneNumber = member.getPhoneNumber())) {
                return AjaxReturnInfo.returnErr("请填写正确的手机号码/该用户没绑定手机号！");
            }
        }

        try {
            int i = (int) (Math.random() * 10000);
            String msgContent = "您的验证码是：" + i + "。请不要把验证码泄露给其他人。";
            //  注释真正发送短信的逻辑代码   
            MsgSendResult result = MsgUtil.sendMsg(msgContent, phoneNumber); //TODO
            result.setSuc(true);

            if (result.isSuc()) {
                System.out.println("发送的短信内容：" + msgContent);
                BizMobileMsg bizMobileMsg = new BizMobileMsg();
                String type = request.getParameter("type");
                if (null == MsgUseEnum.getMsgUseEnum(type)) {
                    return AjaxReturnInfo.returnErr("该短信类型不支持，请联系工作人员！");
                }
                bizMobileMsg.setMsgUse(type);
                bizMobileMsg.setPhoneNumber(phoneNumber);
                Date date = new Date();
                bizMobileMsg.setGmtCreate(date);
                bizMobileMsg.setGmtModified(date);
                bizMobileMsg.setMsgContent(msgContent);
                mobileMsgService.save(bizMobileMsg);
                HttpSession session = request.getSession();
                session.setAttribute(session.getId(), i + "");
                return AjaxReturnInfo.returnSuc("短信发送成功！", msgContent);
            } else {
                return AjaxReturnInfo.returnErr("短信发送异常，请稍后重试！");
            }
        } catch (Exception e) {
            return AjaxReturnInfo.returnErr("短信发送异常，请稍后重试！");
        }
    }
}
