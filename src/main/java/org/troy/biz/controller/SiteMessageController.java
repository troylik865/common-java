package org.troy.biz.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizSiteMessage;
import org.troy.biz.enums.InvestDirectionEnum;
import org.troy.biz.enums.MsgStatusEnum;
import org.troy.biz.enums.SiteMessageTypeEnum;
import org.troy.biz.service.MemberService;
import org.troy.biz.service.SiteMessageService;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.entity.AjaxReturnInfo;
import org.troy.system.controller.ViewController;

/**
 * 站内信相关的controller
 * 
 * @author troy
 * @version $Id: SiteMessageController.java, v 0.1 2014年7月19日 下午6:47:14 troy Exp $
 */
@Controller
@RequestMapping("/biz/siteMessage")
public class SiteMessageController extends ViewController {

    @Autowired
    private SiteMessageService siteMessageService;

    @Autowired
    private MemberService      memberService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(HttpServletRequest request) {

        try {
            //站内信息类型
            String messageType = request.getParameter("type");
            SiteMessageTypeEnum enums = SiteMessageTypeEnum.getEnum(messageType);
            if (null == enums) {
                return AjaxReturnInfo.returnErr("站内信类型不正确！");
            }
            BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
            if (null == member) {
                return AjaxReturnInfo.returnErr("请您先首页登录！");
            }

            //接受信息的会员Id
            String receiveMemberId = request.getParameter("receiveMemberId");
            if (StringUtils.isBlank(receiveMemberId)) {
                //如果没有接受信息的会员Id，默认站内信息给管理员
                BizMember mem = memberService.getAdminAccount();
                if (null != mem) {
                    receiveMemberId = mem.getId() + "";
                }
            }

            //站内信内容
            String messageContent = "";
            BizSiteMessage message = new BizSiteMessage();
            switch (enums) {
                case MEMBER_RANK:
                    messageContent = getContent(enums, member);
                    break;
                case MEMBER_APPLY:
                    messageContent = getContent(enums, member);
                    String[] invests = request.getParameterValues("investDirection");
                    if (null != invests && invests.length > 0) {
                        StringBuilder sb = new StringBuilder();
                        StringBuilder sb1 = new StringBuilder("[");
                        int length = invests.length;
                        for (int i = 0; i < length; i++) {
                            String temp = invests[i];
                            sb.append(temp);
                            sb1.append(InvestDirectionEnum.getInvestDirectionEnum(temp).getName());
                            if (i != length - 1) {
                                sb.append(",");
                                sb1.append(",");
                            }
                        }
                        sb1.append("]");
                        message.setExtendField(sb.toString());
                        messageContent += "参赛品种：" + sb1.toString();
                    }
                    break;
                case MEMBER_REPORT:
                    String relativedMemberId = request.getParameter("memberId");
                    messageContent = getContent(enums,
                        memberService.get(Long.parseLong(relativedMemberId)))
                                     + request.getParameter("content");
                    message.setRelatedMemberId(Long.parseLong(relativedMemberId));
                    break;
                default:
                    messageContent = "莫名其妙的信息！";
                    break;
            }
            message.setReceiveMemberId(Long.parseLong(receiveMemberId));
            message.setMemberId(member.getId());
            message.setMessageType(messageType);
            message.setMessageContent(messageContent);
            Date date = new Date();
            message.setGmtCreate(date);
            message.setGmtModified(date);
            message.setStatus(MsgStatusEnum.I.getValue());
            siteMessageService.save(message);
            return AjaxReturnInfo.returnSuc("提交成功，请静候佳音！");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxReturnInfo.returnErr("提交失败！");
        }
    }

    private String getContent(SiteMessageTypeEnum enums, BizMember member) {
        return StringUtils.replaceOnce(enums.getDesc(), "${}",
            member.getName() + "[编号：" + member.getMemberNo() + "]");
    }
}
