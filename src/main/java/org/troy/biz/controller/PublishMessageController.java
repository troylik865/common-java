package org.troy.biz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.constant.BizConstant;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizPublishMessage;
import org.troy.biz.entity.BizPublishMessageDetail;
import org.troy.biz.service.PublishMessageDetailService;
import org.troy.biz.service.PublishMessageService;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.entity.AjaxReturnInfo;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.controller.ViewController;

/***
 * <p>Title:PublishMessageController </p> 
 *
 * <p>Description:PublishMessage 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
@Controller
@RequestMapping("/biz/publishMessage")
public class PublishMessageController extends ViewController {

    @Autowired
    private PublishMessageService       publishMessageService;

    @Autowired
    private PublishMessageDetailService publishMessageDetailService;

    private static final String         CREATE      = "biz/publishMessage/create";
    private static final String         UPDATE      = "biz/publishMessage/update";
    private static final String         LIST        = "biz/publishMessage/list";

    private static final String         DATE_FORMAT = BizConstant.DATE_FORMAT;

    private static final String         TIME_FORMAT = BizConstant.TIME_FORMAT;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了publishMessage,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(BizPublishMessage publishMessage) {
        try {
            HttpSession session = HttpReceiver.getHttpSession();
            BizMember member = (BizMember) session.getAttribute("member");
            if (null == member) {
                return AjaxReturnInfo.returnErr("请您先登陆！");
            }
            Date date = new Date();
            BizPublishMessage bizPublishMessage = new BizPublishMessage();
            bizPublishMessage.setGmtCreate(date);
            bizPublishMessage.setGmtModified(date);
            bizPublishMessage.setMemberId(member.getId());
            bizPublishMessage.setContent(HttpReceiver.getHttpServletReqeuest().getParameter(
                "content"));
            bizPublishMessage.setStatus("I");
            publishMessageService.saveWithDetail(bizPublishMessage);
            return AjaxReturnInfo.returnSuc("实盘信息发布成功，管理员审核通过后展示！");
        } catch (Exception e) {
            return AjaxReturnInfo.returnSuc("实盘信息发布失败！");
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizPublishMessage publishMessage = publishMessageService.get(id);
        map.put("publishMessage", publishMessage);
        return UPDATE;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(BizPublishMessage publishMessage) {
        try {
            long id = publishMessage.getId();
            BizPublishMessage tempMessage = publishMessageService.get(id);
            tempMessage.setContent(publishMessage.getContent());
            tempMessage.setGmtModified(DateUtil.getNowDate());
            tempMessage.setStatus("I");
            publishMessageService.update(tempMessage);
            return AjaxReturnInfo.returnSuc("修改发布信息成功！");
        } catch (Exception e) {
            return AjaxReturnInfo.returnErr("修改发布信息失败！");
        }
    }

    @Log(message = "删除了publishMessage,id:{0}。")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Long id) {
        publishMessageService.delete(id);
        return AjaxReturnInfo.returnSuc("删除发布信息成功！");
    }

    @Log(message = "删除了publishMessage,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "publishMessage删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] publishMessageTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizPublishMessage publishMessage = publishMessageService.get(ids[i]);
            publishMessageService.delete(publishMessage.getId());

            publishMessageTypes[i] = publishMessage.getId().toString();
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(publishMessageTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        List<BizPublishMessage> publishMessages = null;
        publishMessages = publishMessageService.findAll(page);
        map.put("page", page);
        map.put("publishMessages", publishMessages);
        return LIST;
    }

    @RequestMapping(value = "/listByMemberId", method = { RequestMethod.POST })
    public @ResponseBody String listByMemberId(Page page, Map<String, Object> map) {
        List<BizPublishMessage> bizPublishMessage = null;
        HttpSession session = HttpReceiver.getHttpSession();
        BizMember member = (BizMember) session.getAttribute("member");
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先登陆！");
        }
        bizPublishMessage = publishMessageService.findByMemberId(page, member.getId());
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (CollectionUtils.isNotEmpty(bizPublishMessage)) {
            for (BizPublishMessage message : bizPublishMessage) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("id", message.getId() + "");
                tempMap.put("content", message.getContent());
                tempMap.put("gmtCreateDate",
                    DateUtil.date2String(message.getGmtCreate(), DATE_FORMAT));
                tempMap.put("gmtCreateTime",
                    DateUtil.date2String(message.getGmtCreate(), TIME_FORMAT));
                tempMap.put("gmtCreateDateTime",
                    DateUtil.date2String(message.getGmtCreate(), DateUtil.PATTERN_STANDARD));
                List<BizPublishMessageDetail> temp = publishMessageDetailService
                    .findByMessageId(message.getId());
                tempMap.put("personNum", (null == temp ? 0 : temp.size()) + "人");
                tempMap.put("status", message.getStatus());
                tempMap.put("statusName", StringUtils.equals(message.getStatus(), "I") ? "审核中"
                    : (StringUtils.equals(message.getStatus(), "S") ? "审核通过" : "审核被驳回"));
                list.add(tempMap);
            }
        }
        return PageUtils.toJsonString(page, list);
    }
}
