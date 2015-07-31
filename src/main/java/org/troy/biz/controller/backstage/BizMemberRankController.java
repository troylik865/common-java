package org.troy.biz.controller.backstage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizMemberRank;
import org.troy.biz.enums.InvestDirectionEnum;
import org.troy.biz.service.MemberRankService;
import org.troy.biz.service.MemberService;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

/**
 * 
 * 后台大师排名管理
 *
 * @author siren.lb
 * @version $Id: BizMemberRankController.java,v 0.1 2014年8月26日 下午2:32:56 siren.lb Exp $
 */
@Controller
@RequestMapping("/biz/backstage/bizmemberRank")
public class BizMemberRankController extends ViewController {

    @Autowired
    private MemberRankService   memberRankService;

    @Autowired
    private MemberService       memberService;

    private static final String CREATE = "biz/backstage/bizmemberRank/create";
    private static final String UPDATE = "biz/backstage/bizmemberRank/update";
    private static final String LIST   = "biz/backstage/bizmemberRank/list";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        List<Map<String, String>> investList = new ArrayList<Map<String, String>>();
        for (InvestDirectionEnum en : InvestDirectionEnum.values()) {
            Map<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("name", en.getName());
            tempMap.put("value", en.getValue());
            investList.add(tempMap);
        }
        map.put("data", investList);
        return CREATE;
    }

    @Log(message = "添加了会员,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(HttpServletRequest request) {
        evenName = "大师排名添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            String investDirection = request.getParameter("investDirection");
            String rank = request.getParameter("rank");
            String memberNo = request.getParameter("memberNo");
            BizMember member = memberService.getBizMemberByMemberNo(memberNo);
            BizMemberRank memberRank = new BizMemberRank();
            memberRank.setRankDesc(request.getParameter("desc"));
            memberRank.setMemberId(member.getId());
            Date date = DateUtil.getNowDate();
            memberRank.setGmtCreate(date);
            memberRank.setGmtModified(date);
            memberRank.setInvestDirection(investDirection);
            memberRank.setRank(Integer.parseInt(rank));
            memberRankService.save(memberRank);
            ajaxObject.setMessage(evenName + "成功！");
        } catch (Exception e) {
            ajaxObject.setMessage(evenName + "失败！");
        }
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizMemberRank rank = memberRankService.get(id);
        map.put("investDirection",
            InvestDirectionEnum.getInvestDirectionEnum(rank.getInvestDirection()).getName());
        map.put("id", id + "");
        map.put("rank", rank.getRank() + "");
        map.put("desc", rank.getRankDesc());
        return UPDATE;
    }

    @Log(message = "修改了大师排名,id:{0}。")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(HttpServletRequest request) {
        evenName = "修改了大师排名";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            String id = request.getParameter("id");
            BizMemberRank ranInfo = memberRankService.get(Long.parseLong(id));
            String desc = request.getParameter("desc");
            String rank = request.getParameter("rank");
            ranInfo.setRankDesc(desc);
            ranInfo.setRank(Integer.parseInt(rank));
            ranInfo.setGmtModified(DateUtil.getNowDate());
            memberRankService.update(ranInfo);
            //记录日志
            LogUitl.putArgs(LogMessageObject.newWrite()
                .setObjects(new Object[] { ranInfo.getId() }));
        } catch (Exception e) {
            ajaxObject = new AjaxObject(evenName + "失败！");
        }
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map, BizMember member) {
        List<BizMemberRank> memberRankList = memberRankService.findAll(page);
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (BizMemberRank rank : memberRankList) {
            Map<String, String> tempMap = new HashMap<String, String>();
            BizMember tempMem = memberService.get(rank.getMemberId());
            tempMap.put("id", rank.getId() + "");
            tempMap.put("memberId", tempMem.getId() + "");
            tempMap.put("investType",
                InvestDirectionEnum.getInvestDirectionEnum(rank.getInvestDirection()).getName());
            tempMap.put("memberNo", tempMem.getMemberNo());
            tempMap.put("name", tempMem.getName());
            tempMap.put("phone", tempMem.getPhoneNumber());
            tempMap.put("rank", rank.getRank() + "");
            mapList.add(tempMap);
        }
        map.put("page", page);
        map.put("data", mapList);
        return LIST;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String delete(Long[] ids) {
        evenName = "删除大师排名信息 ";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            List<Long> longIds = new ArrayList<Long>();
            for (Long id : ids) {
                longIds.add(id);
            }
            memberRankService.deleteAll(longIds);
            ajaxObject.setMessage(evenName + "成功！");
        } catch (Exception e) {
            ajaxObject.setMessage(evenName + "失败！");
        }
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }
}
