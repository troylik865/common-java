package org.troy.biz.controller.backstage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizPublishMessage;
import org.troy.biz.entity.BizPublishMessageDetail;
import org.troy.biz.service.MemberService;
import org.troy.biz.service.PublishMessageDetailService;
import org.troy.biz.service.PublishMessageService;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

/**
 * 
 * 交易的后台管理功能
 *
 *
 * @author siren.lb
 * @version $Id: BizPublishMessageController.java,v 0.1 2014年9月5日 上午11:34:47 siren.lb Exp $
 */
@Controller
@RequestMapping("/biz/backstage/bizPublishMessage")
public class BizPublishMessageController extends ViewController {

    @Autowired
    private MemberService               memberService;

    @Autowired
    private PublishMessageService       publishMessageService;

    @Autowired
    private PublishMessageDetailService publishMessageDetailService;

    private static final String         LIST   = "biz/backstage/bizPublishMessage/list";
    private static final String         MANAGE = "biz/backstage/bizPublishMessage/manage";
    private static final String         UPDATE = "biz/backstage/bizPublishMessage/update";
    private static final String         MODIFY = "biz/backstage/bizPublishMessage/modify";

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map, BizMember member) {
        List<BizPublishMessage> messages = publishMessageService.findByStatus(page, "I");
        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        Map<Long, String> nameMap = new HashMap<Long, String>();
        Map<Long, String> memberNoMap = new HashMap<Long, String>();
        if (!CollectionUtils.isEmpty(messages)) {
            for (BizPublishMessage message : messages) {
                Map<String, String> tempMap = new HashMap<String, String>();
                long memberId = message.getMemberId();
                String name = nameMap.get(memberId);
                String memberNo = memberNoMap.get(memberId);
                if (StringUtils.isBlank(name)) {
                    BizMember mem = memberService.get(memberId);
                    if (null == mem) {
                        name = "该会员已经被删除！";
                    } else {
                        name = mem.getName();
                        memberNo = mem.getMemberNo();
                    }
                    nameMap.put(memberId, name);
                    memberNoMap.put(memberId, memberNo);
                }
                tempMap.put("id", message.getId() + "");
                tempMap.put("name", name);
                tempMap.put("memberNo", memberNo);
                tempMap.put("content", message.getContent());
                tempMap.put("gmtCreate",
                    DateUtil.date2String(message.getGmtCreate(), DateUtil.PATTERN_STANDARD));
                dataMapList.add(tempMap);
            }
        }
        map.put("page", page);
        map.put("member", member);
        map.put("messages", dataMapList);
        return LIST;
    }

    @RequestMapping(value = "/manage", method = { RequestMethod.GET, RequestMethod.POST })
    public String manage(Page page, Map<String, Object> map, BizMember member) {
        List<BizPublishMessage> messages = publishMessageService.findAll(page);
        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        Map<Long, String> nameMap = new HashMap<Long, String>();
        Map<Long, String> memberNoMap = new HashMap<Long, String>();
        if (!CollectionUtils.isEmpty(messages)) {
            for (BizPublishMessage message : messages) {
                Map<String, String> tempMap = new HashMap<String, String>();
                long memberId = message.getMemberId();
                String name = nameMap.get(memberId);
                String memberNo = memberNoMap.get(memberId);
                if (StringUtils.isBlank(name)) {
                    BizMember mem = memberService.get(memberId);
                    if (null == mem) {
                        name = "该会员已经被删除！";
                    } else {
                        name = mem.getName();
                        memberNo = mem.getMemberNo();
                    }
                    nameMap.put(memberId, name);
                    memberNoMap.put(memberId, memberNo);
                }
                tempMap.put("id", message.getId() + "");
                tempMap.put("name", name);
                tempMap.put("memberNo", memberNo);
                tempMap.put("content", message.getContent());
                tempMap.put("gmtCreate",
                    DateUtil.date2String(message.getGmtCreate(), DateUtil.PATTERN_STANDARD));
                dataMapList.add(tempMap);
            }
        }
        map.put("page", page);
        map.put("member", member);
        map.put("messages", dataMapList);
        return MANAGE;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        map.put("messageId", id);
        return UPDATE;
    }

    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String modify(@PathVariable Long id, Map<String, Object> map) {
        BizPublishMessage message = publishMessageService.get(id);
        if (null != message) {
            String content = message.getContent();
            map.put("content", content);
            map.put("messageId", message.getId()+"");
        }
        return MODIFY;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String delete(Long[] ids) {
        evenName = "删除大师发布信息 ";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            List<Long> longIds = new ArrayList<Long>();
            for (Long id : ids) {
                longIds.add(id);
            }
            publishMessageService.deleteAll(longIds);
            ajaxObject.setMessage(evenName + "成功！");
        } catch (Exception e) {
            ajaxObject.setMessage(evenName + "失败！");
        }
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(HttpServletRequest request) {
        evenName = "发布信息修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            String messageId = request.getParameter("messageId");
            String status = request.getParameter("status");
            BizPublishMessage message = publishMessageService.get(Long.parseLong(messageId));
            Date date = DateUtil.getNowDate();
            message.setGmtValidated(date);
            message.setGmtModified(date);
            message.setStatus(status);
            publishMessageService.update(message);
            List<BizPublishMessageDetail> details = publishMessageDetailService
                .findByMessageId(message.getId());
            if (!CollectionUtils.isEmpty(details)) {
                for (BizPublishMessageDetail detail : details) {
                    detail.setGmtModified(date);
                    detail.setStatus(status);
                    publishMessageDetailService.update(detail);
                }
            }
            //记录日志
            LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { messageId }));
        } catch (Exception e) {
            ajaxObject = new AjaxObject(evenName + "失败！");
        }
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public @ResponseBody String modify(HttpServletRequest request) {
        evenName = "发布信息修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            String messageId = request.getParameter("messageId");
            String content = request.getParameter("content");
            BizPublishMessage message = publishMessageService.get(Long.parseLong(messageId));
            Date date = DateUtil.getNowDate();
            message.setGmtModified(date);
            message.setContent(content);
            publishMessageService.update(message);
            //记录日志
            LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { messageId }));
        } catch (Exception e) {
            ajaxObject = new AjaxObject(evenName + "失败！");
        }
        return ajaxObject.toString();
    }
}
