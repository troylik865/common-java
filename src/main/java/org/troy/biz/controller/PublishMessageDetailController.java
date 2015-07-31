package org.troy.biz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
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
import org.troy.biz.service.MemberService;
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
 * <p>Title:PublishMessageDetailController </p> 
 *
 * <p>Description:PublishMessageDetail 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
@Controller
@RequestMapping("/biz/publishMessageDetail")
public class PublishMessageDetailController extends ViewController {

    @Autowired
    private PublishMessageDetailService publishMessageDetailService;

    @Autowired
    private PublishMessageService       publishMessageService;

    @Autowired
    private MemberService               memberService;

    private static final String         BEGIN                   = "2000-01-01";
    private static final String         END                     = "3000-01-01";

    private static final String         CREATE                  = "biz/publishMessageDetail/create";
    private static final String         UPDATE                  = "biz/publishMessageDetail/update";
    private static final String         LIST                    = "biz/publishMessageDetail/list";

    private static final String         DATE_FORMAT             = BizConstant.DATE_FORMAT;

    private static final String         TIME_FORMAT_WITHOUT_SEC = "HH:mm";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了publishMessageDetail,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(BizPublishMessageDetail publishMessageDetail) {
        evenName = "publishMessageDetail添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        publishMessageDetailService.save(publishMessageDetail);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { publishMessageDetail.getId() }));
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizPublishMessageDetail publishMessageDetail = publishMessageDetailService.get(id);
        map.put("publishMessageDetail", publishMessageDetail);
        return UPDATE;
    }

    @Log(message = "修改了publishMessageDetail,id:{0}。")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(BizPublishMessageDetail publishMessageDetail) {
        evenName = "publishMessageDetail修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        publishMessageDetailService.update(publishMessageDetail);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { publishMessageDetail.getId() }));
        return ajaxObject.toString();
    }

    @Log(message = "删除了publishMessageDetail,id:{0}。")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Long id) {
        evenName = "publishMessageDetail删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizPublishMessageDetail publishMessageDetail = null;
        publishMessageDetail = publishMessageDetailService.get(id);
        publishMessageDetailService.delete(id);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { publishMessageDetail.getId() }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @Log(message = "删除了publishMessageDetail,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "publishMessageDetail删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] publishMessageDetailTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizPublishMessageDetail publishMessageDetail = publishMessageDetailService.get(ids[i]);
            publishMessageDetailService.delete(publishMessageDetail.getId());

            publishMessageDetailTypes[i] = publishMessageDetail.getId().toString();
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(publishMessageDetailTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        List<BizPublishMessageDetail> publishMessageDetails = null;
        publishMessageDetails = publishMessageDetailService.findAll(page);
        map.put("page", page);
        map.put("publishMessageDetails", publishMessageDetails);
        return LIST;
    }

    @RequestMapping(value = "/listByAcceptMemberId", method = { RequestMethod.POST })
    public @ResponseBody String listByAcceptMemberId(Page page, Map<String, Object> map,
                                                     String memberId, String beginDate,
                                                     String endDate) {
        List<BizPublishMessageDetail> publishMessageDetails = null;
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        publishMessageDetails = publishMessageDetailService
            .findByAcceptMemberIdAndPublishMemberIdAndStatusAndGmtCreateBetween(page,
                member.getId(), StringUtils.isBlank(memberId) ? null : Long.parseLong(memberId),
                "S", StringUtils.defaultIfBlank(beginDate, BEGIN),
                StringUtils.defaultIfBlank(endDate, END));
        if (CollectionUtils.isEmpty(publishMessageDetails)) {
            return PageUtils.toJsonString(page, null);
        }
        List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
        int i = 0;
        for (BizPublishMessageDetail detail : publishMessageDetails) {
            long messageId = detail.getMessageId();
            BizPublishMessage message = publishMessageService.get(messageId);
            if (null == message) {
                continue;
            }
            long tempId = message.getMemberId();
            BizMember tempMem = memberService.get(tempId);
            Map<String, String> tempMap = new HashMap<String, String>();
            Date create = detail.getGmtCreate();
            tempMap.put("id", detail.getId() + "");
            tempMap.put("no", ++i + "");
            tempMap.put("acceptDate", DateUtil.date2String(create, DATE_FORMAT));
            tempMap.put("acceptTime", DateUtil.date2String(create, TIME_FORMAT_WITHOUT_SEC));
            tempMap.put("source", tempMem.getName());
            tempMap.put("content", message.getContent());
            tempMap.put("status", StringUtils.equals(detail.getStatus(), "1") ? "已查看" : "未查看");
            temp.add(tempMap);
        }
        return PageUtils.toJsonString(page, temp);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody String del(HttpServletRequest request) {
        String id = request.getParameter("id");
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        if (StringUtils.isEmpty(id)) {
            return AjaxReturnInfo.returnErr("参数异常，删除失败!");
        }
        try {
            publishMessageDetailService.delete(Long.parseLong(id));
        } catch (Exception e) {
            return AjaxReturnInfo.returnErr("删除失败!");
        }
        return AjaxReturnInfo.returnSuc("删除成功！");
    }
}
