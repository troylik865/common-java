package org.troy.biz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.entity.BizLinkMessage;
import org.troy.biz.entity.BizMember;
import org.troy.biz.enums.InvestDirectionEnum;
import org.troy.biz.service.LinkMessageService;
import org.troy.biz.service.MemberService;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.entity.AjaxReturnInfo;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

/***
 * <p>Title:LinkMessageController </p> 
 *
 * <p>Description:LinkMessage 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Controller
@RequestMapping("/biz/linkMessage")
public class LinkMessageController extends ViewController {

    @Autowired
    private LinkMessageService  linkMessageService;

    @Autowired
    private MemberService       memberService;

    private static final String CREATE = "biz/linkMessage/create";
    private static final String UPDATE = "biz/linkMessage/update";
    private static final String LIST   = "biz/linkMessage/list";

    @RequiresPermissions("LinkMessage:save")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了linkMessage,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(BizLinkMessage linkMessage) {
        String show = "linkMessage添加成功！";
        HttpSession session = HttpReceiver.getHttpSession();
        BizMember member = (BizMember) session.getAttribute("member");

        //如果有用户登录了的情况，将用户Id记录到联系信息里面
        if (null != member) {
            linkMessage.setMemberId(member.getId());
        }

        HttpServletRequest request = HttpReceiver.getHttpServletReqeuest();
        String from = request.getParameter("from");

        //如果是大师寻找中过来的，开始组装内容
        if (StringUtils.equals(from, "search")) {
            StringBuffer sb = new StringBuffer();
            String email = request.getParameter("email");
            String qq = request.getParameter("qq");
            String[] invest = request.getParameterValues("investDirection");
            String highestTransAccountValue = request.getParameter("highestTransAccountValue");
            String intentTransAccountValue = request.getParameter("intentTransAccountValue");
            if (!StringUtils.isBlank(email)) {
                sb.append("  邮箱：[" + email + "]\n");
            }
            if (!StringUtils.isBlank(qq)) {
                sb.append("  qq：[" + qq + "]\n");
            }
            if (null != invest && invest.length > 0) {
                StringBuffer temp = new StringBuffer();
                for (String inv : invest) {
                    temp.append(InvestDirectionEnum.getInvestDirectionEnum(inv).getName() + " ");
                }
                sb.append("  投资方向：[" + temp.toString()+"]");
            }
            if (!StringUtils.isBlank(highestTransAccountValue)) {
                sb.append("  最高交易账户：[" + highestTransAccountValue + "]\n");
            }
            if (!StringUtils.isBlank(intentTransAccountValue)) {
                sb.append("  意向交易账户：[" + intentTransAccountValue+"]");
            }
            linkMessage.setMessage(sb.toString());
        }

        Date date = new Date();
        linkMessage.setGmtCreate(date);
        linkMessage.setGmtModified(date);
        linkMessageService.save(linkMessage);
        return AjaxReturnInfo.returnSuc(show);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizLinkMessage linkMessage = linkMessageService.get(id);
        map.put("linkMessage", linkMessage);
        return UPDATE;
    }

    @Log(message = "修改了linkMessage,id:{0}。")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(BizLinkMessage linkMessage) {
        evenName = "linkMessage修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        linkMessageService.update(linkMessage);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite()
            .setObjects(new Object[] { linkMessage.getId() }));
        return ajaxObject.toString();
    }

    @Log(message = "删除了linkMessage,id:{0}。")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Long id) {
        evenName = "linkMessage删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizLinkMessage linkMessage = null;
        linkMessage = linkMessageService.get(id);
        linkMessageService.delete(id);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite()
            .setObjects(new Object[] { linkMessage.getId() }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @Log(message = "删除了linkMessage,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "联系我们信息删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] linkMessageTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizLinkMessage linkMessage = linkMessageService.get(ids[i]);
            linkMessageService.delete(linkMessage.getId());
            linkMessageTypes[i] = linkMessage.getId().toString();
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(linkMessageTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        List<BizLinkMessage> linkMessages = null;
        linkMessages = linkMessageService.findAll(page);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        if (!CollectionUtils.isEmpty(linkMessages)) {
            for (BizLinkMessage message : linkMessages) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("id", message.getId() + "");
                tempMap.put("name", message.getName());
                tempMap.put("phoneNumber", message.getPhoneNumber());
                tempMap.put("content", message.getMessage());
                tempMap.put("gmtCreate",
                    DateUtil.date2String(message.getGmtCreate(), DateUtil.PATTERN_STANDARD));
                data.add(tempMap);
            }
        }
        map.put("page", page);
        map.put("data", data);
        return LIST;
    }
}
