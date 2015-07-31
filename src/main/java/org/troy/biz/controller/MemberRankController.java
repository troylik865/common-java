package org.troy.biz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.constant.BizConstant;
import org.troy.biz.entity.BizAttach;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizMemberRank;
import org.troy.biz.entity.BizTransRecordStatis;
import org.troy.biz.enums.AttachTypeEnum;
import org.troy.biz.enums.InvestDirectionEnum;
import org.troy.biz.service.AttachService;
import org.troy.biz.service.MemberRankService;
import org.troy.biz.service.MemberService;
import org.troy.biz.service.TransRecordStatisService;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.controller.ViewController;

/***
 * <p>Title:MemberRankController </p> 
 *
 * <p>Description:MemberRank 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Controller
@RequestMapping("/biz/memberRank")
public class MemberRankController extends ViewController {

    @Autowired
    private MemberRankService        memberRankService;

    @Autowired
    private MemberService            memberService;

    @Autowired
    private TransRecordStatisService transRecordStatisService;

    @Autowired
    private AttachService            attachService;

    private static final String      CREATE = "biz/memberRank/create";
    private static final String      UPDATE = "biz/memberRank/update";
    private static final String      LIST   = "biz/memberRank/list";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了memberRank,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(BizMemberRank memberRank) {
        evenName = "memberRank添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        memberRankService.save(memberRank);
        //记录日志
        LogUitl
            .putArgs(LogMessageObject.newWrite().setObjects(new Object[] { memberRank.getId() }));
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizMemberRank memberRank = memberRankService.get(id);
        map.put("memberRank", memberRank);
        return UPDATE;
    }

    @Log(message = "修改了memberRank,id:{0}。")
    @RequiresPermissions("MemberRank:edit")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(BizMemberRank memberRank) {
        evenName = "memberRank修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        memberRankService.update(memberRank);
        //记录日志
        LogUitl
            .putArgs(LogMessageObject.newWrite().setObjects(new Object[] { memberRank.getId() }));
        return ajaxObject.toString();
    }

    @Log(message = "删除了memberRank,id:{0}。")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Long id) {
        evenName = "memberRank删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizMemberRank memberRank = null;
        memberRank = memberRankService.get(id);
        memberRankService.delete(id);
        //记录日志
        LogUitl
            .putArgs(LogMessageObject.newWrite().setObjects(new Object[] { memberRank.getId() }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @Log(message = "删除了memberRank,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "memberRank删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] memberRankTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizMemberRank memberRank = memberRankService.get(ids[i]);
            memberRankService.delete(memberRank.getId());

            memberRankTypes[i] = memberRank.getId().toString();
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(memberRankTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        List<BizMemberRank> memberRanks = null;
        memberRanks = memberRankService.findAll(page);
        map.put("page", page);
        map.put("memberRanks", memberRanks);
        return LIST;
    }

    @RequestMapping(value = "/listTop", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String listTop(Page page, Map<String, Object> map) {
        List<BizMemberRank> memberRanks = memberRankService.listTop(page);
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        if (CollectionUtils.isNotEmpty(memberRanks)) {
            for (BizMemberRank rank : memberRanks) {
                Map<String, String> temp = new HashMap<String, String>();
                BizMember member = memberService.get(rank.getMemberId());
                if (null == member) {
                    continue;
                }
                String invest = InvestDirectionEnum.getInvestDirectionEnum(
                    rank.getInvestDirection()).getName();
                temp.put("name", "[" + invest + "]" + member.getName());
                mapList.add(temp);
            }
        }
        return PageUtils.toJsonString(page, mapList);
    }

    @RequestMapping(value = "/memberRank", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String memberRank(Page page, Map<String, Object> map,
                                           HttpServletRequest request) {
        String investType = request.getParameter("investType");
        List<BizTransRecordStatis> statisList = transRecordStatisService.rankMemberBySQL(page,
            investType);
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        if (!CollectionUtils.isEmpty(statisList)) {
            for (int i = 0; i < statisList.size(); i++) {
                BizTransRecordStatis statis = statisList.get(i);
                Map<String, String> temp = new HashMap<String, String>();
                BizMember member = memberService.get(statis.getMemberId());
                if (null == member) {
                    temp.put("no", "");
                    temp.put("name", "该用户已经删除！");
                } else {
                    temp.put("no", member.getMemberNo());
                    temp.put("name", member.getName());
                }
                temp.put("isValidated", StringUtils.equals(statis.getIsValidated(), "1") ? "1"
                    : "0");
                temp.put("origion", (statis.getOrigionValue() / 100.0) + "元");
                temp.put("out", (statis.getTotalOutcome() / 100.0) + "元");
                temp.put("in", (statis.getTotalIncome() / 100.0) + "元");
                temp.put("last", (statis.getLastestValue() / 100.0) + "元");
                temp.put("total",
                    Math.round(statis.getTotal() * 1.0 / statis.getOrigionValue() * 1.0 * 100)
                            + "%");
                temp.put("begin",
                    DateUtil.date2String(statis.getGmtCreate(), BizConstant.DATE_FORMAT));
                mapList.add(temp);
            }
        }
        return PageUtils.toJsonString(page, mapList);
    }

    @RequestMapping(value = "/memberReconmend", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String memberRecomend(Page page, Map<String, Object> map,
                                               HttpServletRequest request) {
        String investType = request.getParameter("type");
        List<BizMemberRank> rankList = memberRankService.listTopByInvestDirection(page, investType);
        if (CollectionUtils.isEmpty(rankList)) {
            return PageUtils.toJsonString(page, null);
        }
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (BizMemberRank rank : rankList) {
            BizMember member = memberService.get(rank.getMemberId());
            if (null == member) {
                continue;
            }
            List<BizAttach> attachs = attachService.findAttachByAttachTypeAndRefId(
                AttachTypeEnum.MEMBER.getValue(), member.getId());
            Map<String, String> temp = new HashMap<String, String>();
            if (CollectionUtils.isEmpty(attachs)) {
                temp.put("src", "/webresources/images/portfolio/03.jpg");
            } else {
                temp.put("src", "/biz/attach/download?fileId=" + attachs.get(0).getId());
            }
            temp.put("memberNo", member.getMemberNo());
            temp.put("name", member.getName());
            temp.put("desc", rank.getRankDesc());
            mapList.add(temp);
        }
        return PageUtils.toJsonString(page, mapList);
    }
}
