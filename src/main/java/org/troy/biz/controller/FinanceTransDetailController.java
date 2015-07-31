package org.troy.biz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.troy.biz.entity.BizFinanceTransDetail;
import org.troy.biz.entity.BizMember;
import org.troy.biz.enums.TransTypeEnum;
import org.troy.biz.enums.TransUserEnum;
import org.troy.biz.service.FinanceTransDetailService;
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
 * <p>Title:FinanceTransDetailController </p> 
 *
 * <p>Description:FinanceTransDetail 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Controller
@RequestMapping("/biz/financeTransDetail")
public class FinanceTransDetailController extends ViewController {

    @Autowired
    private FinanceTransDetailService financeTransDetailService;

    private static final String       CREATE = "biz/financeTransDetail/create";
    private static final String       UPDATE = "biz/financeTransDetail/update";

    @Autowired
    private MemberService             memberService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了financeTransDetail,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(BizFinanceTransDetail financeTransDetail) {
        evenName = "financeTransDetail添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        financeTransDetailService.save(financeTransDetail);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { financeTransDetail.getId() }));
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizFinanceTransDetail financeTransDetail = financeTransDetailService.get(id);
        map.put("financeTransDetail", financeTransDetail);
        return UPDATE;
    }

    @Log(message = "修改了financeTransDetail,id:{0}。")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(BizFinanceTransDetail financeTransDetail) {
        evenName = "financeTransDetail修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        financeTransDetailService.update(financeTransDetail);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { financeTransDetail.getId() }));
        return ajaxObject.toString();
    }

    @Log(message = "删除了financeTransDetail,id:{0}。")
    @RequiresPermissions("FinanceTransDetail:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Long id) {
        evenName = "financeTransDetail删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizFinanceTransDetail financeTransDetail = null;
        financeTransDetail = financeTransDetailService.get(id);
        financeTransDetailService.delete(id);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { financeTransDetail.getId() }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @Log(message = "删除了financeTransDetail,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "financeTransDetail删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] financeTransDetailTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizFinanceTransDetail financeTransDetail = financeTransDetailService.get(ids[i]);
            financeTransDetailService.delete(financeTransDetail.getId());

            financeTransDetailTypes[i] = financeTransDetail.getId().toString();
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(financeTransDetailTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String list(Page page, Map<String, Object> map) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        List<BizFinanceTransDetail> list = financeTransDetailService.findByMemberId(page,
            member.getId());
        List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
        Map<Long, BizMember> cacheMap = new HashMap<Long, BizMember>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (BizFinanceTransDetail detail : list) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("id", detail.getId() + "");
                tempMap.put("date",
                    DateUtil.date2String(detail.getGmtCreate(), BizConstant.DATE_FORMAT));
                tempMap.put("time",
                    DateUtil.date2String(detail.getGmtCreate(), BizConstant.TIME_FORMAT));
                TransUserEnum enums = TransUserEnum.getEnum(detail.getTransUse());
                if (enums == (TransUserEnum.MEMBER_ATTENT)
                    || enums == TransUserEnum.MEMBER_ATTENT_PROLONG) {
                    String name = "大师";
                    BizMember tempMem = cacheMap.get(detail.getTransUseId()) == null ? memberService
                        .get(detail.getTransUseId()) : cacheMap.get(detail.getTransUseId());
                    if (null != tempMem) {
                        name = "大师(" + tempMem.getName() + ")";
                    }
                    tempMap.put("summary", StringUtils.replace(
                        TransUserEnum.getEnum(detail.getTransUse()).getName(), "大师", name));
                } else {
                    tempMap.put("summary", TransUserEnum.getEnum(detail.getTransUse()).getName());
                }
                long out = 0;
                long in = 0;
                tempMap.put("left", detail.getAccountBalance() + "");
                tempMap.put("type", TransTypeEnum.getEnum(detail.getTransType()).getName());
                switch (TransTypeEnum.getEnum(detail.getTransType())) {
                    case IN:
                        in = detail.getValue();
                        break;
                    case OUT:
                        out = detail.getValue();
                        break;
                    default:
                        break;
                }
                tempMap.put("out", out + "");
                tempMap.put("in", in + "");
                tempList.add(tempMap);
            }
        }
        return PageUtils.toJsonString(page, tempList);
    }
}
