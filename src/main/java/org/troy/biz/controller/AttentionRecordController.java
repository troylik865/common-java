package org.troy.biz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.troy.biz.entity.BizAttentionRecord;
import org.troy.biz.entity.BizMember;
import org.troy.biz.service.AttentionRecordService;
import org.troy.biz.service.MemberService;
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
 * <p>Title:AttentionRecordController </p> 
 *
 * <p>Description:AttentionRecord 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
@Controller
@RequestMapping("/biz/attentionRecord")
public class AttentionRecordController extends ViewController {

    @Autowired
    private AttentionRecordService attentionRecordService;

    @Autowired
    private MemberService          memberService;

    private static final String    CREATE = "biz/attentionRecord/create";
    private static final String    UPDATE = "biz/attentionRecord/update";
    private static final String    LIST   = "biz/attentionRecord/list";

    private static final String    BEGIN  = "2000-01-01";
    private static final String    END    = "3000-01-01";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了attentionRecord,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(BizAttentionRecord attentionRecord) {
        evenName = "attentionRecord添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        attentionRecordService.save(attentionRecord);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { attentionRecord.getId() }));
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizAttentionRecord attentionRecord = attentionRecordService.get(id);
        map.put("attentionRecord", attentionRecord);
        return UPDATE;
    }

    @Log(message = "修改了attentionRecord,id:{0}。")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(BizAttentionRecord attentionRecord) {
        evenName = "attentionRecord修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        attentionRecordService.update(attentionRecord);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { attentionRecord.getId() }));
        return ajaxObject.toString();
    }

    @Log(message = "删除了attentionRecord,id:{0}。")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Long id) {
        evenName = "attentionRecord删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizAttentionRecord attentionRecord = null;
        attentionRecord = attentionRecordService.get(id);
        attentionRecordService.delete(id);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { attentionRecord.getId() }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @Log(message = "删除了attentionRecord,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "attentionRecord删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] attentionRecordTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizAttentionRecord attentionRecord = attentionRecordService.get(ids[i]);
            attentionRecordService.delete(attentionRecord.getId());

            attentionRecordTypes[i] = attentionRecord.getId().toString();
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(attentionRecordTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        List<BizAttentionRecord> attentionRecords = null;
        attentionRecords = attentionRecordService.findAll(page);
        map.put("page", page);
        map.put("attentionRecords", attentionRecords);
        return LIST;
    }

    @RequestMapping(value = "/findAll", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String findAll(Page page, Map<String, Object> map) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        List<BizAttentionRecord> list = attentionRecordService.findByMemberId(page, member.getId());
        if (CollectionUtils.isEmpty(list)) {
            return PageUtils.toJsonString(page, new ArrayList<Map<String, String>>());
        }

        List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
        for (BizAttentionRecord record : list) {
            Map<String, String> tempMap = new HashMap<String, String>();
            //被关注的大师Id
            long attentMemberId = record.getAttentedMemberId();
            BizMember attentMember = memberService.get(attentMemberId);
            if (null == attentMember) {
                continue;
            }
            tempMap.put("id", record.getId() + "");
            tempMap.put("memberNo", attentMember.getMemberNo());
            tempMap.put("name", attentMember.getName());
            tempMap.put("left", record.getLeftDay() + "天");
            tempMap.put("starCount", StringUtils.defaultIfBlank(record.getStarCount(), "0") + "星");
            tempMap.put("cost", attentMember.getAttentDailyCost() + "");
            tempMap.put("score", record.getStarCount());
            tempMap.put("content", StringUtils.defaultString(record.getMemberDesc()));
            tempMap.put("link", StringUtils.defaultString(attentMember.getMemberLink()));
            temp.add(tempMap);
        }
        return PageUtils.toJsonString(page, temp);
    }

    @RequestMapping(value = "/check", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String check(HttpServletRequest request) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            return "{\"info\":\"请您先首页登录！\",\"status\":\"n\"}";
        }
        String memberNo = request.getParameter("param");
        BizMember temp = memberService.getBizMemberByMemberNo(memberNo);
        if (null == temp) {
            return "{\"info\":\"没有找到对应的大师，请正确填写！\",\"status\":\"n\"}";
        }
        BizAttentionRecord record = attentionRecordService.findByMemberIdAndAttentedMemberId(
            member.getId(), temp.getId());
        if (null != record) {
            return "{\"info\":\"您已经关注了此大师，请使用大师延长关注的功能!\",\"status\":\"n\"}";
        }
        return "{\"info\":\"验证通过！\",\"status\":\"y\"}";
    }

    @RequestMapping(value = "/findAllWithVisityHistory", method = { RequestMethod.GET,
            RequestMethod.POST })
    public @ResponseBody String findAllWithVisityHistory(Page page, Map<String, Object> map) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        List<BizAttentionRecord> list = attentionRecordService.findByAttentedMemberId(page,
            member.getId());
        if (CollectionUtils.isEmpty(list)) {
            return PageUtils.toJsonString(page, new ArrayList<Map<String, String>>());
        }

        List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
        int i = 0;
        for (BizAttentionRecord record : list) {
            Map<String, String> tempMap = new HashMap<String, String>();

            long memberId = record.getMemberId();
            BizMember attentMember = memberService.get(memberId);
            if (null == attentMember) {
                continue;
            }
            tempMap.put("no", ++i + "");
            tempMap.put("lastVisit",
                DateUtil.date2String(record.getGmtModified(), BizConstant.DATE_FORMAT));
            tempMap.put("name", attentMember.getName());
            tempMap.put("left", record.getLeftDay() + "天");
            temp.add(tempMap);
        }
        return PageUtils.toJsonString(page, temp);
    }

    @RequestMapping(value = "/findByMember", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String findByMember(Page page, Map<String, Object> map) {
        BizMember member = null;
        String memberNo = HttpReceiver.getHttpServletReqeuest().getParameter("memberNo");
        if (StringUtils.isBlank(memberNo)) {
            member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        } else {
            member = memberService.getBizMemberByMemberNo(memberNo);
        }
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        List<BizAttentionRecord> list = attentionRecordService.findByAttentedMemberId(page,
            member.getId());
        if (CollectionUtils.isEmpty(list)) {
            return PageUtils.toJsonString(page, new ArrayList<Map<String, String>>());
        }

        List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
        int i = 0;
        for (BizAttentionRecord record : list) {
            Map<String, String> tempMap = new HashMap<String, String>();

            long memberId = record.getMemberId();
            BizMember attentMember = memberService.get(memberId);
            String str = ++i + ". 评价人["
                         + ((null == attentMember) ? "该用户已经删除" : attentMember.getName())
                         + "],评价内容：[" + StringUtils.defaultString(record.getMemberDesc(), "")
                         + "]，评分：[" + StringUtils.defaultString(record.getStarCount(), "") + "] 星";
            tempMap.put("content", str);
            temp.add(tempMap);
        }
        return PageUtils.toJsonString(page, temp);
    }

    @RequestMapping(value = "/findAllWithRealVisityHistory", method = { RequestMethod.GET,
            RequestMethod.POST })
    public @ResponseBody String findAllWithRealVisityHistory(Page page, Map<String, Object> map) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        HttpServletRequest request = HttpReceiver.getHttpServletReqeuest();
        String begin = StringUtils.defaultIfBlank(request.getParameter("beginDate"), BEGIN);
        String end = StringUtils.defaultIfBlank(request.getParameter("endDate"), END);
        List<BizAttentionRecord> list = attentionRecordService
            .findByAttentedMemberIdAndGmtModifiedBetween(page, member.getId(), begin, end);
        if (CollectionUtils.isEmpty(list)) {
            return PageUtils.toJsonString(page, new ArrayList<Map<String, String>>());
        }

        List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
        int i = 0;
        for (BizAttentionRecord record : list) {
            Map<String, String> tempMap = new HashMap<String, String>();

            long memberId = record.getMemberId();
            BizMember attentMember = memberService.get(memberId);
            if (null == attentMember) {
                continue;
            }
            tempMap.put("no", ++i + "");
            tempMap.put("lastVisit",
                DateUtil.date2String(record.getGmtModified(), BizConstant.DATE_FORMAT));
            tempMap.put("name", attentMember.getName());
            tempMap.put("left", record.getLeftDay() + "天");
            temp.add(tempMap);
        }
        return PageUtils.toJsonString(page, temp);
    }

    @RequestMapping(value = "/delAttented", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String delAttented() {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        HttpServletRequest request = HttpReceiver.getHttpServletReqeuest();
        String id = request.getParameter("id");
        attentionRecordService.delete(Long.parseLong(id));
        return AjaxReturnInfo.returnSuc("删除成功！");
    }

    @RequestMapping(value = "/initSelect", method = RequestMethod.POST)
    public @ResponseBody String initSelect(HttpServletRequest request) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        try {
            List<BizAttentionRecord> list = attentionRecordService.findByMemberId(member.getId());
            if (CollectionUtils.isEmpty(list)) {
                return AjaxReturnInfo.returnSuc("查询成功！");
            }
            List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Set<Long> set = new HashSet<Long>();
            for (BizAttentionRecord record : list) {
                if (set.add(record.getAttentedMemberId())) {
                    BizMember mem = memberService.get(record.getAttentedMemberId());
                    if (null == mem) {
                        continue;
                    }
                    Map<String, String> temp = new HashMap<String, String>();
                    temp.put("name", mem.getName());
                    temp.put("id", mem.getId() + "");
                    mapList.add(temp);
                }
            }
            return AjaxReturnInfo.returnSuc(mapList);
        } catch (Exception e) {
            return AjaxReturnInfo.returnErr("查询失败！");
        }
    }
}
