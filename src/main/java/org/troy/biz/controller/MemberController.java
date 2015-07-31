package org.troy.biz.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.constant.BizConstant;
import org.troy.biz.entity.BizAttentionRecord;
import org.troy.biz.entity.BizFinance;
import org.troy.biz.entity.BizInvestion;
import org.troy.biz.entity.BizMember;
import org.troy.biz.enums.FinanceEnum;
import org.troy.biz.service.AttentionRecordService;
import org.troy.biz.service.FinanceService;
import org.troy.biz.service.InvestionService;
import org.troy.biz.service.LoginHistoryService;
import org.troy.biz.service.MemberService;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.entity.AjaxReturnInfo;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.StringUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

/***
 * <p>Title:MemberController </p> 
 *
 * <p>Description:Member 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Controller
@RequestMapping("/biz/member")
public class MemberController extends ViewController {

    /** 日志对象 */
    public static final Logger     logger         = Logger.getLogger(MemberController.class);

    @Autowired
    private MemberService          memberService;

    @Autowired
    private LoginHistoryService    loginHistoryService;

    @Autowired
    private FinanceService         financeService;

    @Autowired
    private InvestionService       investionService;

    @Autowired
    private AttentionRecordService attentionRecordService;

    private static final String    CREATE         = "biz/member/create";

    private static final String    UPDATE         = "biz/member/update";

    private static final String    LIST           = "biz/member/list";

    private static final String    PUBLISH        = "biz/member/publish";

    private static final String    ATTENTION      = "biz/member/attention";

    private static final String    DETAIL         = "biz/member/detail";

    private static final String    MEMBER_MODIFY  = "biz/member/memberModify";

    /**
     * 修改成功页面
     */
    private static final String    MEMBER_MOD_SUC = "biz/member/memberModSuc";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了member,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(BizMember member, HttpServletRequest request) {

        HttpSession session = HttpReceiver.getHttpSession();
        String validateCode = request.getParameter("validateCode");
        //进行验证码验证
        if (StringUtils.isBlank(validateCode)
            || !StringUtils.equals(validateCode, (String) session.getAttribute(session.getId()))) {
            return AjaxReturnInfo.returnErr("手机验证码不正确，请正确填写！");
        }

        Long memberId = member.getId();

        //表示此次操作是更新
        if (null != memberId) {
            BizMember dbMem = memberService.get(memberId);
            try {
                BeanUtils.copyProperties(dbMem, member);
                memberService.save(dbMem);
            } catch (IllegalAccessException e) {
                logger.error("属性copy异常", e);
                return AjaxReturnInfo.returnErr("系统异常，用户注册失败！");
            } catch (InvocationTargetException e) {
                logger.error("", e);
            }
            return AjaxReturnInfo.returnSuc("用户注册成功！");
        }

        BizMember alM = memberService.getBizMemberByUserName(member.getUserName());
        if (null != alM) {
            return AjaxReturnInfo.returnErr("用户名已经存在！");
        }
        try {
            String[] strs = request.getParameterValues("investDirection");
            member.setInvestDirection(StringUtil.joinWithoutNull(strs, BizConstant.SPLIT));
            String password = member.getPassword();
            member.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            member.setAttentDailyCost(20L);
            member.setCostType(FinanceEnum.GOLD.getValue());
            Date date = new Date();
            member.setGmtCreate(date);
            member.setGmtModified(date);

            //如果session能获取到推广链接的情况，将此次推广信息入库
            String key = (String) HttpReceiver.getHttpSession().getAttribute("key");
            if (StringUtils.isNotBlank(key)) {
                //执行推广信息入库的动作
                memberService.saveWithSpreadKey(member, key);
            } else {
                memberService.save(member);
            }
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("memberId", member.getId() + "");
            return AjaxReturnInfo.returnSuc("用户注册成功！", paramMap);
        } catch (Exception e) {
            return AjaxReturnInfo.returnErr("系统异常，用户注册失败！");
        }
    }

    @RequestMapping(value = "/three", method = RequestMethod.GET)
    public String three() {
        return "biz/memberRegistThree";
    }

    @RequestMapping(value = "/modSuc", method = RequestMethod.GET)
    public String modSuc() {
        return MEMBER_MOD_SUC;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizMember member = memberService.get(id);
        map.put("member", member);
        return UPDATE;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modify(Map<String, Object> map) {
        BizMember mem = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null != mem) {
            BizMember member = memberService.get(mem.getId());
            map.put("name", member.getName());
            map.put("sex", member.getSex());
            map.put("age", member.getAge());
            map.put("phoneNumber", member.getPhoneNumber());
            map.put("address", member.getAddress());
            map.put("email", member.getEmail());
            map.put("qq", member.getQq());
            map.put("invest", member.getInvestDirection());
            map.put("memberId", member.getId());
        }
        return MEMBER_MODIFY;
    }

    @Log(message = "修改了member,id:{0}。")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(BizMember member, HttpServletRequest request) {
        try {
            String[] strs = request.getParameterValues("investDirection");
            BizMember dbMem = memberService.get(member.getId());
            dbMem.setInvestDirection(StringUtil.joinWithoutNull(strs, BizConstant.SPLIT));
            dbMem.setName(member.getName());
            dbMem.setSex(member.getSex());
            dbMem.setAge(member.getAge());
            dbMem.setPhoneNumber(member.getPhoneNumber());
            dbMem.setAddress(member.getAddress());
            dbMem.setEmail(member.getEmail());
            dbMem.setQq(member.getQq());
            memberService.update(dbMem);
            return AjaxReturnInfo.returnSuc("用户修改成功！", dbMem.getId());
        } catch (Exception e) {
            return AjaxReturnInfo.returnErr("修改用户信息异常！请检查相关参数！");
        }
    }

    @Log(message = "删除了member,id:{0}。")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Long id) {
        evenName = "member删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizMember member = null;
        member = memberService.get(id);
        memberService.delete(id);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { member.getId() }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @Log(message = "删除了member,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "member删除";
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
    public String list(Page page, Map<String, Object> map) {
        List<BizMember> members = null;
        members = memberService.findAll(page);
        map.put("page", page);
        map.put("members", members);
        return LIST;
    }

    @RequestMapping(value = "/publish", method = { RequestMethod.GET, RequestMethod.POST })
    public String publish(Page page, Map<String, Object> map) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null != member) {
            map.put("name", member.getName());
            List<BizInvestion> list = investionService.findByMemberId(member.getId());
            String str = "";

            //只要有一个品种参赛就认为有资格发布了信息了
            if (CollectionUtils.isNotEmpty(list)) {
                for (BizInvestion invest : list) {
                    if (StringUtils.equals(invest.getIsValidated(), "1")) {
                        str = "1";
                        break;
                    }
                }
            }
            map.put("isValidated", str);
            BizFinance finance = financeService.findByMemberIdAndType(member.getId(),
                FinanceEnum.GOLD.getValue());
            if (null != finance) {
                map.put("finance", finance.getValue());
            }
        }
        return PUBLISH;
    }

    @RequestMapping(value = "/attention", method = { RequestMethod.GET, RequestMethod.POST })
    public String attention(Page page, Map<String, Object> map) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null != member) {
            map.put("dailyCost", member.getAttentDailyCost());
            map.put("memberNo", HttpReceiver.getHttpServletReqeuest().getParameter("memberNo"));
        }
        return ATTENTION;
    }

    @RequestMapping(value = "/detail", method = { RequestMethod.GET, RequestMethod.POST })
    public String detail(Page page, Map<String, Object> map) {
        return DETAIL;
    }

    @RequestMapping(value = "/checkSession", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String checkSession() {
        BizMember member = HttpReceiver.getCurrentMember();
        return AjaxReturnInfo.returnSuc((null != member));
    }

    @RequestMapping(value = "/check", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String check(Page page, Map<String, Object> map) {
        String type = HttpReceiver.getHttpServletReqeuest().getParameter("type");
        if (StringUtils.equals(type, "check")) {
            String userName = HttpReceiver.getHttpServletReqeuest().getParameter("param");
            BizMember member = memberService.getBizMemberByUserName(userName);
            if (null == member) {
                return "{\"info\":\"用户名不存在,请正确填写需要重置密码的会员名！\",\"status\":\"n\"}";
            }
            return "{\"info\":\"验证通过！\",\"status\":\"y\"}";
        } else {
            String userName = HttpReceiver.getHttpServletReqeuest().getParameter("param");
            BizMember member = memberService.getBizMemberByUserName(userName);
            if (null != member) {
                return "{\"info\":\"用户名已存在,请使用其他用户名完成注册！\",\"status\":\"n\"}";
            }

            return "{\"info\":\"验证通过！\",\"status\":\"y\"}";
        }
    }

    /**
     * 会员登陆
     * 
     * @return
     */
    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String login(HttpServletRequest request) {
        String validateCode = request.getParameter("validate");
        HttpSession session = request.getSession();
        if (!StringUtils.equalsIgnoreCase(validateCode,
            (String) session.getAttribute(session.getId()))) {
            return AjaxReturnInfo.returnErr("验证码错误！");
        }
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        final BizMember member = memberService.getBizMemberByUserName(userName);
        if (null == member || !StringUtils.equals(password, member.getPassword())) {
            return AjaxReturnInfo.returnErr("用户名/密码不正确，请重新输入!");
        }
        session.setAttribute("member", member);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", member.getName());
        paramMap.put(
            "lastLoginTime",
            (null != member.getGmtLastLogin()) ? DateUtil.date2String(member.getGmtLastLogin(),
                BizConstant.DATE_TIME_FORMAT) : "");
        loginHistoryService.storeLoginInfo(member);
        return AjaxReturnInfo.returnSuc(paramMap);
    }

    @RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String logout() {
        HttpReceiver.getHttpSession().removeAttribute("member");
        return AjaxReturnInfo.returnSuc();
    }

    @RequestMapping(value = "/addAttented", method = { RequestMethod.POST })
    public @ResponseBody String addAttented() {
        //获取当前登陆用户的信息
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");

        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先登陆！");
        }
        HttpServletRequest request = HttpReceiver.getHttpServletReqeuest();
        String memberNo = request.getParameter("memberNo");
        String dateNnum = request.getParameter("dateNum");

        if (StringUtils.isBlank(memberNo) || StringUtils.isBlank(dateNnum)) {
            return AjaxReturnInfo.returnErr("请填写正确的大师编码和需要关注的天数！");
        }
        BizMember mem = memberService.getBizMemberByMemberNo(memberNo);
        if (null == mem) {
            return AjaxReturnInfo.returnErr("无对应的大师信息，请确认大师编号的正确性！");
        }
        BizAttentionRecord check = attentionRecordService.findByMemberIdAndAttentedMemberId(
            member.getId(), mem.getId());
        if (null != check) {
            return AjaxReturnInfo.returnErr("您已经关注了此大师，请使用大师延长关注的功能!");
        }
        BizAttentionRecord record = new BizAttentionRecord();
        Date date = new Date();
        long dailyCost = mem.getAttentDailyCost();
        record.setAttentedMemberId(mem.getId());
        record.setMemberId(member.getId());
        record.setDailyCost(dailyCost + "");
        record.setGmtCreate(date);
        record.setGmtModified(date);
        record.setType(mem.getCostType());
        int dayNum = Integer.parseInt(dateNnum);
        record.setValue(dayNum * dailyCost);
        record.setAttentDayNum(dayNum + "");
        record.setExtendField(DateUtil.date2String(date, BizConstant.DATE_TIME_FORMAT));
        try {
            attentionRecordService.saveWithCost(record);
        } catch (ServiceException e) {
            return AjaxReturnInfo.returnErr(e.getMessage());
        }
        return AjaxReturnInfo.returnSuc("大师关注成功！");
    }

    @RequestMapping(value = "/prolongAttented", method = { RequestMethod.POST })
    public @ResponseBody String prolongAttented() {
        //获取当前登陆用户的信息
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");

        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先登陆！");
        }
        HttpServletRequest request = HttpReceiver.getHttpServletReqeuest();
        String id = request.getParameter("id");
        String dateNum = request.getParameter("dateNum");

        if (StringUtils.isBlank(id) || StringUtils.isBlank(dateNum)) {
            return AjaxReturnInfo.returnErr("请填写正确的大师编码和需要关注的天数！");
        }
        BizAttentionRecord record = attentionRecordService.get(Long.parseLong(id));
        BizMember mem = memberService.get(record.getAttentedMemberId());
        try {
            record.addAttentDay(Long.parseLong(dateNum), mem.getAttentDailyCost());
            attentionRecordService.updateWithCost(record);
        } catch (ServiceException e) {
            return AjaxReturnInfo.returnErr(e.getMessage());
        }
        return AjaxReturnInfo.returnSuc("延长关注成功！");
    }

    @RequestMapping(value = "/assessAttented", method = { RequestMethod.POST })
    public @ResponseBody String assessAttented(String id) {
        //获取当前登陆用户的信息
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先登陆！");
        }
        HttpServletRequest request = HttpReceiver.getHttpServletReqeuest();
        String score = request.getParameter("score");
        String content = request.getParameter("content");

        if (StringUtils.isBlank(score) || StringUtils.isBlank(content)) {
            return AjaxReturnInfo.returnErr("请选择评分或者进行评价！");
        }
        BizAttentionRecord record = attentionRecordService.get(Long.parseLong(id));
        record.setStarCount(score);
        record.setMemberDesc(content);
        try {
            attentionRecordService.update(record);
        } catch (ServiceException e) {
            return AjaxReturnInfo.returnErr(e.getMessage());
        }
        return AjaxReturnInfo.returnSuc("评价发布成功！");
    }

    @RequestMapping(value = "/getDailyCost", method = { RequestMethod.POST })
    public @ResponseBody String getDailyCost() {
        //获取当前登陆用户的信息
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");

        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先登陆！");
        }
        HttpServletRequest request = HttpReceiver.getHttpServletReqeuest();
        String memberNo = request.getParameter("memberNo");
        BizMember mem = memberService.getBizMemberByMemberNo(memberNo);
        if (null == mem) {
            return AjaxReturnInfo.returnErr("无对应的大师信息，请确认大师编号的正确性！");
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("cost", mem.getAttentDailyCost() + "");
        map.put("name", mem.getName());
        return AjaxReturnInfo.returnSuc(map);
    }

    /**
     * 会员申请参赛的逻辑
     * 
     * @return
     */
    @RequestMapping(value = "/apply", method = { RequestMethod.POST })
    public @ResponseBody String apply() {
        //获取当前登陆用户的信息
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");

        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先登陆！");
        }
        HttpServletRequest request = HttpReceiver.getHttpServletReqeuest();
        String memberNo = request.getParameter("memberNo");
        BizMember mem = memberService.getBizMemberByMemberNo(memberNo);
        if (null == mem) {
            return AjaxReturnInfo.returnErr("无对应的大师信息，请确认大师编号的正确性！");
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("cost", mem.getAttentDailyCost() + "");
        map.put("name", mem.getName());
        return AjaxReturnInfo.returnSuc(map);
    }

    @RequestMapping(value = "/reSec", method = { RequestMethod.POST })
    public @ResponseBody String reSec() {
        HttpServletRequest request = HttpReceiver.getHttpServletReqeuest();
        String validateCode = request.getParameter("validateCode");
        if (null != validateCode) {
            validateCode = StringUtils.trim(validateCode);
        }

        HttpSession session = HttpReceiver.getHttpSession();
        //进行验证码验证
        if (StringUtils.isBlank(validateCode)
            || !StringUtils.equals(validateCode, (String) session.getAttribute(session.getId()))) {
            return AjaxReturnInfo.returnErr("手机验证码不正确，请正确填写！");
        }

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        BizMember mem = memberService.getBizMemberByUserName(userName);
        if (null == mem) {
            return AjaxReturnInfo.returnErr("无对应的会员注册信息，请正确填写！");
        }
        mem.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        memberService.update(mem);
        return AjaxReturnInfo.returnSuc("密码重置成功！");
    }

}
