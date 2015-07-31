package org.troy.biz.controller.backstage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.constant.BizConstant;
import org.troy.biz.entity.BizFinance;
import org.troy.biz.entity.BizFinanceTransDetail;
import org.troy.biz.entity.BizInvestion;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizSiteMessage;
import org.troy.biz.enums.FinanceEnum;
import org.troy.biz.enums.InvestDirectionEnum;
import org.troy.biz.enums.MsgStatusEnum;
import org.troy.biz.enums.SiteMessageTypeEnum;
import org.troy.biz.enums.TransTypeEnum;
import org.troy.biz.enums.TransUserEnum;
import org.troy.biz.service.FinanceService;
import org.troy.biz.service.FinanceTransDetailService;
import org.troy.biz.service.InvestionService;
import org.troy.biz.service.MemberService;
import org.troy.biz.service.SiteMessageService;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.component.beanvalidator.BeanValidators;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

import com.google.common.collect.Lists;

/***
 * <p>Title:后台会员展示 </p> 
 *
 * <p>Description:后台会员展示控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2013 </p>
 * 
 * <p>CreateDate: 2014-07-05 22:49 </p>
 *
 */
@Controller
@RequestMapping("/biz/backstage/bizmember")
public class BizMemberController extends ViewController {

    @Autowired
    private MemberService             memberService;

    @Autowired
    private SiteMessageService        siteMessageService;

    @Autowired
    private InvestionService          investionService;

    @Autowired
    private FinanceService            financeService;

    @Autowired
    private FinanceTransDetailService financeTransDetailService;

    @Autowired
    private Validator                 validator;

    private static final String       CREATE      = "biz/backstage/bizmember/create";
    private static final String       UPDATE      = "biz/backstage/bizmember/update";
    private static final String       SHOW_DETAIL = "biz/backstage/bizmember/showDetail";
    private static final String       LIST        = "biz/backstage/bizmember/list";
    private static final String       VALI_LIST   = "biz/backstage/bizmember/valiList";
    private static final String       SHOW_ALL    = "biz/backstage/bizmember/showAllInvest";
    private static final String       APPROVAL    = "biz/backstage/bizmember/approval";

    //	@RequiresPermissions("BizMember:save")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了会员,id:{0}。")
    //	@RequiresPermissions("BizMember:save")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(BizMember member) {
        evenName = "会员添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        //调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
        BeanValidators.validateWithException(validator, member);

        memberService.save(member);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { member.getId() }));
        return ajaxObject.toString();
    }

    //	@RequiresPermissions("BizMember:edit")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizMember member = memberService.get(id);
        map.put("member", member);
        BizFinance finance = financeService.findByMemberIdAndType(member.getId(),
            FinanceEnum.GOLD.getValue());
        if (null != finance) {
            map.put("coinValue", finance.getValue());
        }
        return UPDATE;
    }

    @RequestMapping(value = "/showDetail/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public String showDetail(@PathVariable Long id, Map<String, Object> map, Page page) {
        BizMember member = memberService.get(id);
        if (null != member) {
            List<BizFinanceTransDetail> details = financeTransDetailService.findByMemberId(page,
                member.getId());
            List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
            Map<Long, BizMember> cacheMap = new HashMap<Long, BizMember>();
            if (CollectionUtils.isNotEmpty(details)) {
                for (BizFinanceTransDetail detail : details) {
                    Map<String, String> tempMap = new HashMap<String, String>();
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
                        tempMap.put("summary", TransUserEnum.getEnum(detail.getTransUse())
                            .getName());
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
            map.put("page", page);
            map.put("data", tempList);
        }
        return SHOW_DETAIL;
    }


    @Log(message = "修改了会员,id:{0}。")
    //	@RequiresPermissions("BizMember:edit")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(BizMember member, HttpServletRequest request) {
        evenName = "会员修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        long id = member.getId();
        BizMember mem = memberService.get(id);
        mem.setMemberLink(member.getMemberLink());
        mem.setAttentDailyCost(member.getAttentDailyCost());
        mem.setGmtModified(DateUtil.getNowDate());
        memberService.updateWithFinance(mem, request.getParameter("coinValue"));
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { member.getId() }));
        return ajaxObject.toString();
    }

    @Log(message = "删除了会员,id:{0}。")
    //	@RequiresPermissions("BizMember:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Long id) {
        evenName = "会员删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizMember member = null;
        member = memberService.get(id);
        memberService.delete(id);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { member.getId() }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @Log(message = "删除了会员,ids:{0}。")
    //	@RequiresPermissions("BizMember:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "会员删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] memberTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizMember member = memberService.get(ids[i]);
            memberService.delete(member.getId());

            memberTypes[i] = member.getId().toString();
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(memberTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map, BizMember member) {
        List<BizMember> members = null;
        BizMemberSpecification bizMemberSpecification = new BizMemberSpecification(member);
        members = memberService.findBizMembers(bizMemberSpecification, page);
        List<Map<String, Object>> membersMapList = new ArrayList<Map<String, Object>>();
        if (!CollectionUtils.isEmpty(members)) {
            for (BizMember m : members) {
                Map<String, Object> tempMap = new HashMap<String, Object>();
                tempMap.put("id", m.getId());
                tempMap.put("memberNo", m.getMemberNo());
                tempMap.put("name", m.getName());
                tempMap.put("sexStr", m.getSexStr());
                tempMap.put("age", m.getAge());
                tempMap.put("phoneNumber", m.getPhoneNumber());
                tempMap.put("email", m.getEmail());
                tempMap.put("qq", m.getQq());
                List<BizInvestion> invests = investionService.findByMemberId(m.getId());
                int size = invests.size();
                String[] str = new String[size];
                for (int i = 0; i < size; i++) {
                    str[i] = InvestDirectionEnum.getInvestDirectionEnum(
                        invests.get(i).getInvestDirection()).getName();
                }
                tempMap.put("investDirection", StringUtils.join(str, ","));
                tempMap.put("userName", m.getUserName());
                tempMap.put("address", m.getAddress());
                tempMap.put("gmtCreate", m.getGmtCreate());
                tempMap.put("attentDailyCost", m.getAttentDailyCost());
                BizFinance finance = financeService.findByMemberIdAndType(m.getId(),
                    FinanceEnum.GOLD.getValue());
                tempMap.put("coinValue", finance.getValue());
                membersMapList.add(tempMap);
            }
        }
        map.put("page", page);
        map.put("member", member);
        map.put("data", membersMapList);
        return LIST;
    }

    @RequestMapping(value = "/valiList", method = { RequestMethod.GET, RequestMethod.POST })
    public String valiList(Page page, Map<String, Object> map, BizMember member) {
        BizMember admin = memberService.getAdminAccount();
        if (null == admin) {
            return VALI_LIST;
        }
        List<BizSiteMessage> list = siteMessageService.findByReceiveMemberIdAndType(page,
            admin.getId(), SiteMessageTypeEnum.MEMBER_APPLY.getValue());
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (BizSiteMessage message : list) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap
                    .put("type", SiteMessageTypeEnum.getEnum(message.getMessageType()).getName());
                BizMember tempMem = memberService.get(message.getMemberId());
                tempMap.put("id", message.getId() + "");
                tempMap.put("name", tempMem.getName());
                tempMap.put("content", message.getMessageContent());
                tempMap.put("status", MsgStatusEnum.getMsgUseEnum(message.getStatus()).getName());
                tempMap.put("time",
                    DateUtil.date2String(message.getGmtCreate(), BizConstant.DATE_TIME_FORMAT));
                mapList.add(tempMap);
            }
        }
        List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
        for (SiteMessageTypeEnum enu : SiteMessageTypeEnum.values()) {
            Map<String, String> temp = new HashMap<String, String>();
            temp.put("name", enu.getName());
            temp.put("value", enu.getValue());
            tempList.add(temp);
        }
        map.put("types", tempList);
        map.put("page", page);
        map.put("data", mapList);
        return VALI_LIST;
    }

    @RequestMapping(value = "/showAllInvest", method = { RequestMethod.GET, RequestMethod.POST })
    public String showAllInvest(Page page, Map<String, Object> map) {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        List<BizInvestion> list = null;
        String searchName = HttpReceiver.getHttpServletReqeuest().getParameter("name");
        String isValidated = HttpReceiver.getHttpServletReqeuest().getParameter("isValidated");
        if (StringUtils.isNotBlank(searchName)) {
            List<BizMember> members = memberService.getBizMemberByName(searchName);
            if (CollectionUtils.isEmpty(members)) {
                if (StringUtils.isBlank(isValidated)) {
                    list = investionService.findAll(page);
                } else {
                    list = investionService.findByIsValidated(page, isValidated);
                }
            } else {
                List<Long> ids = new ArrayList<Long>();
                for (BizMember m : members) {
                    ids.add(m.getId());
                }
                if (StringUtils.isBlank(isValidated)) {
                    list = investionService.findByMemberIdIn(page, ids);
                } else {
                    list = investionService.findByMemberIdInAndIsValidated(page, ids, isValidated);
                }
            }
        } else {
            if (StringUtils.isBlank(isValidated)) {
                list = investionService.findAll(page);
            } else {
                list = investionService.findByIsValidated(page, isValidated);
            }
        }
        Map<Long, String> memMap = new HashMap<Long, String>();
        Map<Long, String> memNoMap = new HashMap<Long, String>();
        for (BizInvestion bt : list) {
            Map<String, String> temp = new HashMap<String, String>();
            String name = memMap.get(bt.getMemberId());
            String memberNo = memNoMap.get(bt.getMemberId());
            if (StringUtils.isBlank(name)) {
                BizMember tempMem = memberService.get(bt.getMemberId());
                if (null != tempMem) {
                    name = tempMem.getName();
                    memMap.put(bt.getMemberId(), name);
                    memberNo = tempMem.getMemberNo();
                    memNoMap.put(bt.getMemberId(), memberNo);
                } else {
                    name = "会员已经删除!";
                    memberNo = "";
                }
            }
            temp.put("id", bt.getId() + "");
            temp.put("name", name);
            temp.put("type", InvestDirectionEnum.getInvestDirectionEnum(bt.getInvestDirection())
                .getName());
            temp.put("status", StringUtils.equals("1", bt.getIsValidated()) ? "有" : "无");
            temp.put("createTime",
                DateUtil.date2String(bt.getGmtCreate(), BizConstant.DATE_TIME_FORMAT));
            temp.put("memberNo", memberNo);
            mapList.add(temp);
        }
        map.put("page", page);
        map.put("data", mapList);
        return SHOW_ALL;
    }

    @RequestMapping(value = "/approval/{id}", method = { RequestMethod.POST, RequestMethod.GET })
    public String approval(@PathVariable Long id, Map<String, Object> map) {
        BizSiteMessage siteMessage = siteMessageService.get(id);
        String str = siteMessage.getExtendField();
        if (StringUtils.isNotBlank(str)) {
            String[] strs = StringUtils.split(str, ",");
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (String s : strs) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("name", InvestDirectionEnum.getInvestDirectionEnum(s).getName());
                tempMap.put("value", s);
                list.add(tempMap);
            }
            map.put("data", list);
        }
        map.put("messageId", id);
        return APPROVAL;
    }

    @RequestMapping(value = "/pass", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String pass(String[] values, Long messageId) {
        evenName = "用户参赛审核";
        AjaxObject ajaxObject = new AjaxObject();
        if (messageId == 0) {
            ajaxObject.setMessage(evenName + "失败！没有对应的信息！");
            return ajaxObject.toString();
        }
        BizSiteMessage siteMessage = siteMessageService.get(messageId);
        if (null == siteMessage) {
            ajaxObject.setMessage(evenName + "失败！该信息已经被删除！");
            return ajaxObject.toString();
        }
        if (null == values || values.length == 0) {
            siteMessage.setGmtModified(new Date());
            siteMessage.setStatus(MsgStatusEnum.S.getValue());
            siteMessageService.save(siteMessage);
            ajaxObject.setMessage(evenName + "成功！");
            return ajaxObject.toString();
        }
        Long memberId = siteMessage.getMemberId();
        try {
            for (int i = 0; i < values.length; i++) {
                String type = values[i];
                BizInvestion invest = investionService.findByMemberIdAndInvestDirection(memberId,
                    type);

                Date date = DateUtil.getNowDate();
                //如果没有该投资方向，测插入
                if (null == invest) {
                    invest = new BizInvestion();
                    invest.setGmtCreate(date);
                    invest.setGmtModified(date);
                    invest.setInvestDirection(type);
                    invest.setMemberId(memberId);
                    siteMessage.setStatus(MsgStatusEnum.S.getValue());
                    siteMessage.setGmtModified(date);
                    investionService.saveWithUpdateSiteMessage(invest, siteMessage);
                } else {
                    invest.setGmtModified(date);
                    invest.setGmtValidated(date);
                    invest.setIsValidated("1");
                    siteMessage.setStatus(MsgStatusEnum.S.getValue());
                    siteMessage.setGmtModified(date);
                    investionService.updateWithUpdateSiteMessage(invest, siteMessage);
                }
            }
            ajaxObject.setMessage(evenName + "成功！");
        } catch (Exception e) {
            ajaxObject.setMessage(evenName + "失败！");
        }
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/refuse", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String refuse(Long messageId) {
        evenName = "用户参赛驳回";
        AjaxObject ajaxObject = new AjaxObject();
        if (messageId == 0) {
            ajaxObject.setMessage(evenName + "失败！没有对应的信息！");
            return ajaxObject.toString();
        }
        BizSiteMessage siteMessage = siteMessageService.get(messageId);
        if (null == siteMessage) {
            ajaxObject.setMessage(evenName + "失败！该信息已经被删除！");
            return ajaxObject.toString();
        }
        try {
            siteMessage.setStatus(MsgStatusEnum.T.getValue());
            Date date = DateUtil.getNowDate();
            siteMessage.setGmtModified(date);
            siteMessageService.update(siteMessage);
            ajaxObject.setMessage(evenName + "成功！");
        } catch (Exception e) {
            ajaxObject.setMessage(evenName + "失败！");
        }
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/memberPass", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String memberPass(Long[] ids) {
        evenName = "用户参赛审核通过";
        AjaxObject ajaxObject = new AjaxObject();
        try {
            List<Long> longIds = new ArrayList<Long>();
            for (Long id : ids) {
                longIds.add(id);
            }
            List<BizInvestion> list = investionService.findByIdIn(longIds);
            Date date = DateUtil.getNowDate();
            for (BizInvestion invest : list) {
                invest.setGmtModified(date);
                invest.setGmtValidated(date);
                invest.setIsValidated("1");
            }
            investionService.updateInvestionsWithStatis(list);
            ajaxObject.setMessage(evenName + "成功！");
        } catch (Exception e) {
            e.printStackTrace();
            ajaxObject.setMessage(evenName + "失败！");
        }
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/memberCancel", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String memberCancel(Long[] ids) {
        evenName = "取消用户参赛资格";
        AjaxObject ajaxObject = new AjaxObject();
        try {
            List<Long> longIds = new ArrayList<Long>();
            for (Long id : ids) {
                longIds.add(id);
            }
            List<BizInvestion> list = investionService.findByIdIn(longIds);
            Date date = DateUtil.getNowDate();
            for (BizInvestion invest : list) {
                invest.setGmtModified(date);
                invest.setGmtValidated(date);
                invest.setIsValidated("0");
            }
            investionService.updateInvestionsWithStatis(list);
            ajaxObject.setMessage(evenName + "成功！");
        } catch (Exception e) {
            ajaxObject.setMessage(evenName + "失败！");
        }
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    /**
     * 定义BizMember查询规则
     */
    private class BizMemberSpecification implements Specification<BizMember> {

        private BizMember bizMember;

        public BizMemberSpecification(BizMember bizMember) {
            this.bizMember = bizMember;
        }

        /**
         * @param root
         * @param query
         * @param criteriabuilder
         * @return
         * @see org.springframework.data.jpa.domain.Specification#toPredicate(javax.persistence.criteria.Root,
         *      javax.persistence.criteria.CriteriaQuery,
         *      javax.persistence.criteria.CriteriaBuilder)
         */
        public Predicate toPredicate(Root<BizMember> root, CriteriaQuery<?> query,
                                     CriteriaBuilder criteriabuilder) {
            List<Predicate> predicateList = Lists.newArrayList();

            if (StringUtils.isNotBlank(bizMember.getName())) {
                Predicate operatorCodePredicate = criteriabuilder.equal(root.get("name"),
                    bizMember.getName());
                predicateList.add(operatorCodePredicate);
            }
            query.orderBy(criteriabuilder.desc(root.get("id")));// id倒序
            Predicate[] predicates = new Predicate[predicateList.size()];
            predicateList.toArray(predicates);

            return criteriabuilder.and(predicates);
        }
    }
}
