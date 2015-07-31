package org.troy.biz.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.troy.biz.constant.BizConstant;
import org.troy.biz.entity.BizAttach;
import org.troy.biz.entity.BizFinance;
import org.troy.biz.entity.BizInvestion;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizSysConfig;
import org.troy.biz.enums.AttachTypeEnum;
import org.troy.biz.enums.FinanceEnum;
import org.troy.biz.enums.InvestDirectionEnum;
import org.troy.biz.enums.SexEnum;
import org.troy.biz.service.AttachService;
import org.troy.biz.service.BizSysConfigService;
import org.troy.biz.service.FinanceService;
import org.troy.biz.service.InvestionService;
import org.troy.biz.service.MemberCollectService;
import org.troy.biz.service.MemberService;
import org.troy.biz.service.TransRecordService;
import org.troy.biz.service.TransRecordStatisService;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.utils.DateUtil;
import org.troy.system.controller.ViewController;

/**
 * 业务首页相关的请求
 * 
 * @author troy
 * @version $Id: BizIndexController.java, v 0.1 2014年6月22日 上午10:20:08 troy Exp $
 */
@Controller
@RequestMapping("/")
public class BizIndexController extends ViewController {

    private String                   pre                  = "biz";

    private static final String      SHOW                 = "index";

    private static final String      HEAD                 = "head";

    private static final String      MAIN                 = "main";

    private static final String      LEFT_GUIDE           = "leftGuide";

    private static final String      SEARCH               = "search";

    private static final String      MEMBER_SEARCH        = "memberSearch";

    private static final String      COMPANY_DESC         = "companyDesc";

    private static final String      CONTACT_US           = "contactUs";

    private static final String      RECOMMEND            = "recommend";

    private static final String      RECOMMEND1           = "recommend1";

    private static final String      RECOMMEND2           = "recommend2";

    private static final String      PERSONAL_PAGE        = "personalPage";

    private static final String      TRANS_RECORD_SUCCESS = "transRecordSuccess";

    private static final String      MY_TRANS_RECORD      = "myTransRecord";

    private static final String      SOFT_CENTER          = "softCenter";

    private static final String      PAGE_DEMO            = "pageDemo";

    private static final String      FIRST                = "first";

    private static final String      MEMBER_REGIST_FIRST  = "memberRegistFirst";

    private static final String      MEMBER_REGIST_SECOND = "memberRegistSecond";

    private static final String      MEMBER_REGIST_THREEE = "memberRegistThree";

    private static final String      HOME_WITH_CHART      = "homeWithChart";

    private static final String      MEMBER_RANK          = "memberRank";

    private static final String      TEST                 = "test";

    private static final String      FORGET_SEC           = "forgetSec";

    private static final String      FORGET_SEC_SUC       = "forgetSecSuc";

    private static final String      TEST_DETAIL          = "testDetail";

    private static final String      NEW_MEMBER           = "newMember";

    @Autowired
    private FinanceService           financeService;

    @Autowired
    private InvestionService         investionService;

    @Autowired
    private MemberService            memberService;

    @Autowired
    private TransRecordService       transRecordService;

    @Autowired
    private AttachService            attachService;

    @Autowired
    private MemberCollectService     memberCollectService;

    @Autowired
    private TransRecordStatisService transRecordStatisService;

    @Autowired
    private BizSysConfigService      bizSysConfigService;

    @RequestMapping(value = "/index")
    public String index(Map<String, Object> map, HttpServletRequest request) {
        BizMember member = (BizMember) request.getSession().getAttribute("member");

        // 判断是否存在推广链接
        String key = request.getParameter("key");
        if (StringUtils.isNotBlank(key)) {
            request.getSession().setAttribute("key", key);
        }
        if (null != member) {
            map.put("isLogin", "1");
        }

        List<BizSysConfig> list = bizSysConfigService.findByType("site");
        if (!CollectionUtils.isEmpty(list)) {
            for (BizSysConfig config : list) {
                if (StringUtils.equals(config.getParamName(), "headPhone1")) {
                    map.put("headPhone1", config.getParamValue());
                }
                if (StringUtils.equals(config.getParamName(), "headPhone2")) {
                    map.put("headPhone2", config.getParamValue());
                }
                if (StringUtils.equals(config.getParamName(), "footPhone")) {
                    map.put("footPhone", config.getParamValue());
                }
            }
        }
        return createView(SHOW);
    }

    @RequestMapping(value = "/head")
    public String head() {
        return createView(HEAD);
    }

    @RequestMapping(value = "/main")
    public String main() {
        return createView(MAIN);
    }

    @RequestMapping(value = "/leftGuide")
    public String leftGuide(Map<String, Object> map) {
        List<BizSysConfig> configs = bizSysConfigService.findByType("site");
        if (!CollectionUtils.isEmpty(configs)) {
            for (BizSysConfig config : configs) {
                if (StringUtils.equals(config.getParamName(), "qq")) {
                    map.put("qq", config.getParamValue());
                }
                if (StringUtils.equals(config.getParamName(), "tel")) {
                    map.put("tel", config.getParamValue());
                }
            }
        }
        return createView(LEFT_GUIDE);
    }

    @RequestMapping(value = "/search")
    public String search() {
        return createView(SEARCH);
    }

    @RequestMapping(value = "/memberSearch")
    public String memberSearch() {
        return createView(MEMBER_SEARCH);
    }

    @RequestMapping(value = "/companyDesc")
    public String companyDesc() {
        return createView(COMPANY_DESC);
    }

    @RequestMapping(value = "/contactUs")
    public String contactUs() {
        return createView(CONTACT_US);
    }

    @RequestMapping(value = "/recommend")
    public String recommend() {
        return createView(RECOMMEND);
    }

    @RequestMapping(value = "/recommend1")
    public String recommend1() {
        return createView(RECOMMEND1);
    }

    @RequestMapping(value = "/recommend2")
    public String recommend2() {
        return createView(RECOMMEND2);
    }

    @RequestMapping(value = "/testDetail")
    public String testDetail() {
        return createView(TEST_DETAIL);
    }

    @RequestMapping(value = "/homeWithChart")
    public String homeWithChart(Map<String, Object> map, HttpServletRequest request) {
        //大师编号
        String memberNo = request.getParameter("memberNo");
        //获取大师信息
        BizMember member = memberService.getBizMemberByMemberNo(memberNo);
        if (null != member) {
            map.put("name", member.getName());
            map.put("memberNo", memberNo);
            map.put("memberId", member.getId() + "");
            map.put("memberLink", member.getMemberLink());
        }
        return createView(HOME_WITH_CHART);
    }

    @RequestMapping(value = "/homeWithChartSelf")
    public String homeWithChartSelf(Map<String, Object> map, HttpServletRequest request) {
        String memberNo = request.getParameter("memberNo");
        BizMember member = null;
        if (StringUtils.isBlank(memberNo)) {
            member = HttpReceiver.getCurrentMember();
        } else {
            member = memberService.getBizMemberByMemberNo(memberNo);
        }
        if (null != member) {
            map.put("name", member.getName());
            map.put("memberNo", member.getMemberNo());
            map.put("memberId", member.getId() + "");
            map.put("sex", SexEnum.getEnum(member.getSex()).getName());
            map.put("phone", member.getPhoneNumber());
            map.put("age", member.getAge() + "");
            map.put("name", member.getName());
            map.put("email", member.getEmail());
            map.put("qq", member.getQq());
            map.put("memberLink", member.getMemberLink());
            List<BizInvestion> invests = investionService.findByMemberId(member.getId());
            if (!CollectionUtils.isEmpty(invests)) {
                List<String> invest = new ArrayList<String>();
                for (BizInvestion bi : invests) {
                    invest.add(InvestDirectionEnum.getInvestDirectionEnum(bi.getInvestDirection())
                        .getName());
                }
                map.put("investDirection", StringUtils.join(invest, ";"));
            }

            //加载附件
            List<BizAttach> attachs = attachService.findAttachByAttachTypeAndRefId(
                AttachTypeEnum.MEMBER.getValue(), member.getId());
            if (!CollectionUtils.isEmpty(attachs)) {
                map.put("src", "/biz/attach/download?fileId=" + attachs.get(0).getId());
            }
        }
        BizMember loginMem = HttpReceiver.getCurrentMember();
        map.put(
            "collect",
            (loginMem != null && memberCollectService.findByMemberIdAndCollectedMemberId(
                loginMem.getId(), member.getId()) == null) ? "1" : "");
        int rank = 0;
        String index = request.getParameter("index");
        if (StringUtils.isNotBlank(index)) {
            rank = Integer.parseInt(index);
        } else {
            rank = transRecordStatisService.getMemberRankByMemberIdAndInvest(member.getId(),
                InvestDirectionEnum.QI_HUO.getValue());
        }
        map.put("isValidated", request.getParameter("isValidated"));
        map.put("rank", rank + "");
        return createView(HOME_WITH_CHART);
    }

    @RequestMapping(value = "/transRecordSuccess")
    public String transRecordSuccess(Map<String, Object> map, HttpServletRequest request) {
        return createView(TRANS_RECORD_SUCCESS);
    }

    @RequestMapping(value = "/myTransRecord")
    public String myTransRecord(Map<String, Object> map, HttpServletRequest request) {
        return createView(MY_TRANS_RECORD);
    }

    @RequestMapping(value = "/personalPage")
    public String personalPage(Map<String, Object> map) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null != member) {
            map.put("name", member.getName());
            List<BizInvestion> list = investionService.findByMemberId(member.getId());
            String str = "";

            // 只要有一个品种参赛就认为有资格发布了信息了
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
        String suc = HttpReceiver.getHttpServletReqeuest().getParameter("suc");
        map.put("suc", suc);
        return createView(PERSONAL_PAGE);
    }

    @RequestMapping(value = "/softCenter")
    public String softCenter() {
        return createView(SOFT_CENTER);
    }

    @RequestMapping(value = "/forgetSec")
    public String forgetSec() {
        return createView(FORGET_SEC);
    }

    @RequestMapping(value = "/forgetSecSuc")
    public String forgetSecSuc() {
        return createView(FORGET_SEC_SUC);
    }

    @RequestMapping(value = "/pageDemo")
    public String pageDemo() {
        return createView(PAGE_DEMO);
    }

    @RequestMapping(value = "/memberRegistFirst")
    public String memberRegistFirst() {

        return createView(MEMBER_REGIST_FIRST);
    }

    @RequestMapping(value = "/memberRegistSecond")
    public String memberRegistSecond() {
        return createView(MEMBER_REGIST_SECOND);
    }

    @RequestMapping(value = "/memberRegistThree")
    public String memberRegistThreee() {
        return createView(MEMBER_REGIST_THREEE);
    }

    @RequestMapping(value = "/newMember")
    public String newMember(Map<String, Object> map) {
        String index = HttpReceiver.getHttpServletReqeuest().getParameter("index");
        String desc = HttpReceiver.getHttpServletReqeuest().getParameter("desc");
        map.put("index", index);
        if (!StringUtils.isBlank(desc)) {
            try {
                map.put("desc", new String(desc.getBytes("ISO8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
        }
        return createView(NEW_MEMBER);
    }

    @RequestMapping(value = "/memberRank")
    public String memberRank() {
        return createView(MEMBER_RANK);
    }

    @RequestMapping(value = "/test")
    public String test() {
        return createView(TEST);
    }

    @RequestMapping(value = "/first")
    public String first(Map<String, Object> map) {
        HttpSession session = HttpReceiver.getHttpSession();
        BizMember member = (BizMember) session.getAttribute("member");
        if (null != member) {
            map.put("name", member.getName());
            map.put(
                "lastLoginTime",
                (null != member.getGmtLastLogin()) ? DateUtil.date2String(member.getGmtLastLogin(),
                    BizConstant.DATE_TIME_FORMAT) : "");
        }
        return createView(FIRST);
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

}
