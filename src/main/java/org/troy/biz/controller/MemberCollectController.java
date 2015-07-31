package org.troy.biz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizMemberCollect;
import org.troy.biz.service.MemberCollectService;
import org.troy.biz.service.MemberService;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.entity.AjaxReturnInfo;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.controller.ViewController;

/**
 * 
 * 大师收藏相关
 *
 *
 * @author siren.lb
 * @version $Id: MemberCollectController.java,v 0.1 2014年9月15日 上午12:10:15 siren.lb Exp $
 */
@Controller
@RequestMapping("/biz/memberCollect")
public class MemberCollectController extends ViewController {

    @Autowired
    private MemberService        memberService;

    @Autowired
    private MemberCollectService memberCollectService;

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String listTop(Page page, Map<String, Object> map) {
        BizMember member = HttpReceiver.getCurrentMember();
        if (null == member) {
            return PageUtils.toJsonString(page, null);
        }
        List<BizMemberCollect> collectList = memberCollectService.findByMemberId(page,
            member.getId());
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (BizMemberCollect collect : collectList) {
            Map<String, String> tempMap = new HashMap<String, String>();
            BizMember mem = memberService.get(collect.getCollectedMemberId());
            tempMap.put("id", collect.getId() + "");
            tempMap.put("name", mem.getName());
            tempMap.put("memberNo", mem.getMemberNo());
            mapList.add(tempMap);
        }
        return PageUtils.toJsonString(page, mapList);
    }
    

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(HttpServletRequest request) {
        try {
            BizMember member = HttpReceiver.getCurrentMember();
            if (null == member) {
                return AjaxReturnInfo.returnSuc("请您先首页登录!");
            }
            String memberNo = request.getParameter("memberNo");
            BizMember tempMem = memberService.getBizMemberByMemberNo(memberNo);
            BizMemberCollect collect = new BizMemberCollect();
            collect.setMemberId(member.getId());
            collect.setCollectedMemberId(tempMem.getId());
            Date date = DateUtil.getNowDate();
            collect.setGmtCreate(date);
            collect.setGmtModified(date);
            memberCollectService.save(collect);
            return AjaxReturnInfo.returnSuc("添加大师收藏成功!");
        } catch (Exception e) {
            return AjaxReturnInfo.returnErr("添加大师收藏异常！");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String delete(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            memberCollectService.delete(Long.parseLong(id));
            return AjaxReturnInfo.returnSuc("取消大师收藏成功!");
        } catch (Exception e) {
            return AjaxReturnInfo.returnErr("取消大师收藏异常！");
        }
    }
}
