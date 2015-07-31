package org.troy.biz.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.constant.BizConstant;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizMemberSearch;
import org.troy.biz.service.MemberSearchService;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.entity.AjaxReturnInfo;
import org.troy.common.utils.StringUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.controller.ViewController;

/***
 * <p>Title:MemberSearchController </p> 
 *
 * <p>Description:MemberSearch 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Controller
@RequestMapping("/biz/memberSearch")
public class MemberSearchController extends ViewController {

    @Autowired
    private MemberSearchService memberSearchService;

    private static final String CREATE = "biz/memberSearch/create";
    private static final String UPDATE = "biz/memberSearch/update";
    private static final String LIST   = "biz/memberSearch/list";

    @RequiresPermissions("MemberSearch:save")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了memberSearch,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    String create(BizMemberSearch memberSearch, HttpServletRequest request) {
        String show = "memberSearch添加成功！";
        HttpSession session = HttpReceiver.getHttpSession();
        BizMember member = (BizMember) session.getAttribute("member");
        //如果有用户登录了的情况，将用户Id记录到联系信息里面
        if (null != member) {
            memberSearch.setMemberId(member.getId());
        }
        String[] strs = request.getParameterValues("investDirection");
        member.setInvestDirection(StringUtil.joinWithoutNull(strs, BizConstant.SPLIT));
        Date date = new Date();
        memberSearch.setGmtCreate(date);
        memberSearch.setGmtModified(date);
        memberSearchService.save(memberSearch);
        return AjaxReturnInfo.returnSuc(show);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizMemberSearch memberSearch = memberSearchService.get(id);
        map.put("memberSearch", memberSearch);
        return UPDATE;
    }

    @Log(message = "修改了memberSearch,id:{0}。")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    String update(BizMemberSearch memberSearch) {
        evenName = "memberSearch修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        memberSearchService.update(memberSearch);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { memberSearch.getId() }));
        return ajaxObject.toString();
    }

    @Log(message = "删除了memberSearch,id:{0}。")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable Long id) {
        evenName = "memberSearch删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizMemberSearch memberSearch = null;
        memberSearch = memberSearchService.get(id);
        memberSearchService.delete(id);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { memberSearch.getId() }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @Log(message = "删除了memberSearch,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    String deleteMany(Long[] ids) {
        evenName = "memberSearch删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] memberSearchTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizMemberSearch memberSearch = memberSearchService.get(ids[i]);
            memberSearchService.delete(memberSearch.getId());

            memberSearchTypes[i] = memberSearch.getId().toString();
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(memberSearchTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        List<BizMemberSearch> memberSearchs = null;
        memberSearchs = memberSearchService.findAll(page);
        map.put("page", page);
        map.put("memberSearchs", memberSearchs);
        return LIST;
    }

    @RequestMapping(value = "/listTop", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody
    String listTop(Page page, Map<String, Object> map) {
        List<BizMemberSearch> memberSearchs = memberSearchService.findAllByGmtCreateDesc(page);
        return PageUtils.toJsonString(page, memberSearchs);
    }

}
