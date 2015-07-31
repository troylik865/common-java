package org.troy.biz.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.troy.biz.constant.BizConstant;
import org.troy.biz.constant.BizTransRecordConstant;
import org.troy.biz.controller.form.TransRecordReviewForm;
import org.troy.biz.entity.BizAttach;
import org.troy.biz.entity.BizFinanceTransDetail;
import org.troy.biz.entity.BizInvestion;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizReviewLog;
import org.troy.biz.entity.BizTransRecord;
import org.troy.biz.entity.vo.BizTransRecordVO;
import org.troy.biz.enums.InvestDirectionEnum;
import org.troy.biz.enums.TransStatusEnum;
import org.troy.biz.enums.TransTypeEnum;
import org.troy.biz.enums.TransUserEnum;
import org.troy.biz.service.AttachService;
import org.troy.biz.service.BizReviewLogService;
import org.troy.biz.service.FinanceTransDetailService;
import org.troy.biz.service.InvestionService;
import org.troy.biz.service.MemberService;
import org.troy.biz.service.TransRecordService;
import org.troy.biz.service.TransRecordStatisService;
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
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.controller.ViewController;

import com.alibaba.fastjson.JSONObject;

/***
 * <p>
 * Title:TransRecordController
 * </p>
 * 
 * <p>
 * Description:TransRecord 控制层
 * </p>
 * 
 * <p>
 * Author:jiangb
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 * <p>
 * CreateDate: 2014-06-23 22:19
 * </p>
 * 
 */
@Controller
@RequestMapping("/biz/transRecord")
public class TransRecordController extends ViewController {

    private static final Logger       logger          = Logger
                                                          .getLogger(TransRecordController.class);

    @Autowired
    private TransRecordService        transRecordService;

    @Autowired
    private MemberService             memberService;

    @Autowired
    private BizReviewLogService       reviewLogService;

    @Autowired
    private AttachService             attachService;

    @Autowired
    private TransRecordStatisService  transRecordStatisService;

    @Autowired
    private InvestionService          investionService;

    @Autowired
    private FinanceTransDetailService financeTransDetailService;

    private static final String       LIST_REVIEW     = "system/security/transRecord/listToReview";
    private static final String       SHOW_LAST       = "system/security/transRecord/showLast";
    private static final String       SHOW_DAILY      = "system/security/transRecord/showDaily";
    private static final String       SHOW_DETAIL_PER = "system/security/transRecord/showDetailPer";

    private static final String       LIST_REVIEWED   = "system/security/transRecord/listReviewed";

    private static final String       REVIEW          = "system/security/transRecord/preReview";

    private static final String       VIEW            = "system/security/transRecord/viewTrans";

    private static final String       MODIFY_LAST     = "system/security/transRecord/modifyLast";

    private static final String       SHOW_REVIEWED   = "system/security/transRecord/showReviewed";

    private static final String       CREATE          = "biz/transRecord/create";

    private static final String       UPDATE          = "biz/transRecord/update";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    /**
     * 跳转到审核页面
     * 
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/toReview/{id}", method = RequestMethod.GET)
    public String preReview(@PathVariable Long id, Map<String, Object> map) {
        TransRecordReviewForm transRecordForm = new TransRecordReviewForm();
        transRecordForm.setResult(TransStatusEnum.S.getValue());
        transRecordForm.setTransRecordId(id);
        map.put("transRecordForm", transRecordForm);
        Long transRecordId = transRecordForm.getTransRecordId();
        if (null != transRecordId) {
            List<BizAttach> sumList = new ArrayList<BizAttach>();
            List<BizAttach> list = attachService.findByRefIdAndAttachTypeOrderByGmtCreateDesc(
                transRecordId, "biz_trans_record1");
            List<BizAttach> list2 = attachService.findByRefIdAndAttachTypeOrderByGmtCreateDesc(
                transRecordId, "biz_trans_record2");
            if (!CollectionUtils.isEmpty(list2)) {
                sumList.addAll(list2);
            }
            if (!CollectionUtils.isEmpty(list)) {
                sumList.addAll(list);
            }
            map.put("attachs", sumList);
        }
        return REVIEW;
    }

    @RequestMapping(value = "/reviewTransRecord", method = RequestMethod.POST)
    public @ResponseBody String reviewTransRecord(TransRecordReviewForm form,
                                                  HttpServletRequest request) {
        BizTransRecord transRecord = transRecordService.get(form.getTransRecordId());
        // 后续应加上判断此刻该记录的状态，应先锁，后判断状态，再更新
        if (isPass(form)) {
            transRecord.setStatus(TransStatusEnum.S.getValue());
            transRecord.setGmtValidate(DateUtil.getNowDate());
        } else {
            transRecord.setStatus(TransStatusEnum.F.getValue());
        }
        transRecordService.saveWithStatisAndReview(transRecord, form);
        return new AjaxObject("审核成功").toString();

    }

    @RequestMapping(value = "/modifyLast1", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String modifyLast1(HttpServletRequest request) {
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        type = StringUtils.isBlank(type) ? InvestDirectionEnum.QI_HUO.getValue() : type;
        BizTransRecordVO vo;
        try {
            vo = transRecordService.findLastRecord(Long.parseLong(id), type);
            if (null != vo) {
                BizTransRecord record = transRecordService.get(vo.getId());
                record.setInvestType(InvestDirectionEnum.getInvestDirectionEnum(
                    record.getInvestType()).getName());
                return AjaxReturnInfo.returnSuc(record);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/modifyLast/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public String modifyLast(@PathVariable Long id, String type, Map<String, Object> map, Page page) {
        type = StringUtils.isBlank(type) ? InvestDirectionEnum.QI_HUO.getValue() : type;
        BizTransRecordVO vo;
        try {
            vo = transRecordService.findLastRecord(id, type);
            if (null != vo) {
                BizTransRecord transRecord = transRecordService.get(vo.getId());
                map.put("id", transRecord.getId() + "");
                map.put("investType", transRecord.getInvestType());
                map.put("investTypeName",
                    InvestDirectionEnum.getInvestDirectionEnum(transRecord.getInvestType())
                        .getName());
                map.put("fee", getRealValue(transRecord.getFee()));
                map.put("gainsAndLosses", getRealValue(transRecord.getGainsAndLosses()));
                map.put("currIncome", getRealValue(transRecord.getCurrIncome()));
                map.put("currOutcome", getRealValue(transRecord.getCurrOutcome()));
                map.put("currValue", getRealValue(transRecord.getCurrValue()));
                map.put("lastDayValue", getRealValue(transRecord.getLastDayValue()));
                map.put("totalGainsAndLosses", transRecord.getTotalGainsAndLosses());
                map.put("origionValue", getRealValue(transRecord.getOrigionValue()));
                map.put("status", transRecord.getStatus());
                map.put("importDate", transRecord.getImportDate());
                map.put("memberId", transRecord.getMemberId() + "");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MODIFY_LAST;
    }

    @RequestMapping(value = "/viewTrans/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public String viewTrans(@PathVariable Long id, Page page, Map<String, Object> map) {
        BizTransRecordVO transRecord = transRecordService.getBizTransRecordVO(id);
        map.put("transRecord", transRecord);
        return VIEW;
    }

    @RequestMapping(value = "/showReviewed/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public String showReviewed(@PathVariable Long id, Page page, Map<String, Object> map) {
        BizTransRecord transRecord = transRecordService.get(id);
        if (null != transRecord) {
            long memberId = transRecord.getMemberId();
            List<BizTransRecord> list = transRecordService.findTransRrcordByMemberAndTypeAndStatus(
                page, memberId, transRecord.getInvestType(), TransStatusEnum.S.getValue());
            List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
            double divi = 1000000.0;
            for (BizTransRecord recordVO : list) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("id", recordVO.getId() + "");
                tempMap.put("investTypeStr",
                    InvestDirectionEnum.getInvestDirectionEnum(recordVO.getInvestType()).getName());
                tempMap.put("importDate", recordVO.getImportDate());
                tempMap.put("currIncomeWYuan",
                    (null == recordVO.getCurrIncome() ? 0 : recordVO.getCurrIncome()) / divi + "");
                tempMap
                    .put("currOutcomeWYuan",
                        (null == recordVO.getCurrOutcome() ? 0 : recordVO.getCurrOutcome()) / divi
                                + "");
                tempMap.put("currValueWYuan", recordVO.getCurrValue() / divi + "");
                tempMap.put("lastDayValueWYuan",
                    (null == recordVO.getLastDayValue() ? 0 : recordVO.getLastDayValue()) / divi
                            + "");
                tempMap.put("status", recordVO.getStatus());
                String statusName = "";
                if (StringUtils.equals(recordVO.getStatus(), "I")) {
                    statusName = "审核中";
                } else if (StringUtils.equals(recordVO.getStatus(), "S")) {
                    statusName = "审核通过";
                } else if (StringUtils.equals(recordVO.getStatus(), "F")) {
                    BizReviewLog log = reviewLogService.getBizReviewLog(recordVO.getId(),
                        "TRANS_RECORD");
                    if (null != log) {
                        tempMap.put("failReason", log.getComment());
                    }
                    statusName = "审核退回";
                } else {
                    statusName = "莫名其妙";
                }
                tempMap.put("statusName", statusName);
                temp.add(tempMap);
            }
            map.put("page", page);
            map.put("data", temp);
        }
        return SHOW_REVIEWED;
    }

    @RequestMapping(value = "/showReviewed1/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public String showReviewed1(@PathVariable Long id, Page page, Map<String, Object> map) {
        List<BizTransRecordVO> list = transRecordService
            .findTransRrcordByMemberAndTypeAndStatusForWeek(page, id, TransStatusEnum.S.getValue());
        List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
        double divi = 1000000.0;
        for (BizTransRecordVO recordVO : list) {
            Map<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("id", recordVO.getId() + "");
            tempMap.put("investTypeStr",
                InvestDirectionEnum.getInvestDirectionEnum(recordVO.getInvestType()).getName());
            tempMap.put("importDate", recordVO.getImportDate());
            tempMap.put("currIncomeWYuan",
                (null == recordVO.getCurrIncome() ? 0 : recordVO.getCurrIncome()) / divi + "");
            tempMap.put("currOutcomeWYuan",
                (null == recordVO.getCurrOutcome() ? 0 : recordVO.getCurrOutcome()) / divi + "");
            tempMap.put("currValueWYuan", recordVO.getCurrValue() / divi + "");
            tempMap.put("lastDayValueWYuan",
                (null == recordVO.getLastDayValue() ? 0 : recordVO.getLastDayValue()) / divi + "");
            tempMap.put("status", recordVO.getStatus());
            String statusName = "";
            if (StringUtils.equals(recordVO.getStatus(), "I")) {
                statusName = "审核中";
            } else if (StringUtils.equals(recordVO.getStatus(), "S")) {
                statusName = "审核通过";
            } else if (StringUtils.equals(recordVO.getStatus(), "F")) {
                BizReviewLog log = reviewLogService.getBizReviewLog(recordVO.getId(),
                    "TRANS_RECORD");
                if (null != log) {
                    tempMap.put("failReason", log.getComment());
                }
                statusName = "审核退回";
            } else {
                statusName = "莫名其妙";
            }
            tempMap.put("statusName", statusName);
            temp.add(tempMap);
        }
        map.put("page", page);
        map.put("data", temp);
        return SHOW_REVIEWED;
    }

    /**
     * 是否通过
     * 
     * @param result
     * @return
     */
    private boolean isPass(TransRecordReviewForm form) {
        return TransStatusEnum.getEnum(form.getResult()) == TransStatusEnum.S;
    }

    @Log(message = "添加了transRecord,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam MultipartFile[] myfiles1,
                         @RequestParam MultipartFile[] myfiles2, HttpServletRequest request) {
        // 如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
        // 如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
        // 并且上传多个文件时，前台表单中的所有<input
        // type="file"/>的name都应该是myfiles1,2，否则参数里的myfiles无法获取到所有上传的文件

        BizTransRecord transRecord = buildBizTransRecord(request);
        // 设置为初始状态
        transRecord.setStatus(TransStatusEnum.I.getValue());
        HttpSession session = HttpReceiver.getHttpSession();
        BizMember member = (BizMember) session.getAttribute("member");
        transRecord.setMemberId(member.getId());
        Map<String, String> extendMap = new HashMap<String, String>();
        extendMap.put("memberNo", member.getMemberNo());
        try {
            transRecordService.save(transRecord, myfiles1, myfiles2, extendMap);
        } catch (ServiceException e) {
            return "redirect:/personalPage?suc=false";
        }
        return "redirect:/personalPage?suc=true";
    }

    /**
     * 通过request来进行交易对象的组装
     * 
     * @param request
     * @return
     */
    private BizTransRecord buildBizTransRecord(HttpServletRequest request) {
        BizTransRecord record = new BizTransRecord();
        String recordType = request.getParameter("recordType");
        if (StringUtils.equals(recordType, BizTransRecordConstant.FIRST_RECORD_TYPE)) {
            String investTypeFirst = request.getParameter("investTypeFirst");
            String importDateFirst = request.getParameter("importDateFirst");
            String origionValueFirst = request.getParameter("origionValueFirst");
            record.setInvestType(investTypeFirst);
            record.setImportDate(importDateFirst);
            long origionValue = Math.round(Double.parseDouble(origionValueFirst) * 100);
            record.setOrigionValue(origionValue);
            record.setCurrValue(origionValue);
            record.setRecordType(recordType);
        } else {
            String totleGainsAndLosses = request.getParameter("totleGainsAndLosses");
            String investType = request.getParameter("investType");
            String importDate = request.getParameter("importDate");
            long fee = getLongValue(request, "fee");
            long gainsAndLosses = getLongValue(request, "gainsAndLosses");
            long currIncome = getLongValue(request, "currIncome");
            long currOutcome = getLongValue(request, "currOutcome");
            long currValue = getLongValue(request, "currValue");
            long lastDayValue = getLongValue(request, "lastDayValue");
            long origionValue = getLongValue(request, "origionValue");
            record.setTotalGainsAndLosses(totleGainsAndLosses);
            record.setFee(fee);
            record.setGainsAndLosses(gainsAndLosses);
            record.setCurrIncome(currIncome);
            record.setCurrOutcome(currOutcome);
            record.setCurrValue(currValue);
            record.setLastDayValue(lastDayValue);
            record.setOrigionValue(origionValue);
            record.setInvestType(investType);
            record.setImportDate(importDate);
        }
        return record;
    }

    /**
     * 获取到金额的分
     * 
     * @param request
     * @param key
     * @return
     */
    private long getLongValue(HttpServletRequest request, String key) {
        String keyValue = request.getParameter(key);
        if (StringUtils.isBlank(keyValue)) {
            return 0;
        }
        return Math.round(Double.parseDouble(keyValue) * 100);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizTransRecord transRecord = transRecordService.get(id);
        if (null == transRecord) {
            return UPDATE;
        }
        map.put("id", transRecord.getId() + "");
        map.put("investType", transRecord.getInvestType());
        map.put("investTypeName",
            InvestDirectionEnum.getInvestDirectionEnum(transRecord.getInvestType()).getName());
        map.put("fee", getRealValue(transRecord.getFee()));
        map.put("gainsAndLosses", getRealValue(transRecord.getGainsAndLosses()));
        map.put("currIncome", getRealValue(transRecord.getCurrIncome()));
        map.put("currOutcome", getRealValue(transRecord.getCurrOutcome()));
        map.put("currValue", getRealValue(transRecord.getCurrValue()));
        map.put("lastDayValue", getRealValue(transRecord.getLastDayValue()));
        map.put("totalGainsAndLosses", transRecord.getTotalGainsAndLosses());
        map.put("origionValue", getRealValue(transRecord.getOrigionValue()));
        map.put("status", transRecord.getStatus());
        map.put("importDate", transRecord.getImportDate());
        BizReviewLog log = reviewLogService.getBizReviewLog(transRecord.getId(), "TRANS_RECORD");
        if (null != log) {
            map.put("failReason", log.getComment());
        }
        return UPDATE;
    }

    private String getRealValue(Long value) {
        if (null == value) {
            return "0";
        }
        return value / 100.0 + "";
    }

    @RequestMapping(value = "/updateRecord", method = RequestMethod.POST)
    public String updateRecord(@RequestParam MultipartFile[] myfiles1,
                               @RequestParam MultipartFile[] myfiles2, HttpServletRequest request) {
        String flag = "1";
        try {
            BizTransRecord record = getRecord(request);
            record.setStatus("I");
            transRecordService.update(record);
        } catch (Exception e) {
            logger.error("更新交易信息异常！", e);
            flag = "0";
        }
        return "redirect:/myTransRecord?flag=" + flag;
    }

    @RequestMapping(value = "/updateRecordWithStatis", method = RequestMethod.POST)
    public @ResponseBody String updateRecordWithStatis(HttpServletRequest request) {
        evenName = "更新交易记录";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizTransRecord record = getRecord(request);
        try {
            transRecordService.modifyLastRecord(record);
        } catch (Exception e) {
            logger.error("更新交易信息异常！", e);
            ajaxObject = new AjaxObject(evenName + "失败！");
        }
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    /**
     * 组装交易对象
     * 
     * @param request
     * @return
     */
    private BizTransRecord getRecord(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            return null;
        }
        BizTransRecord record = transRecordService.get(Long.parseLong(id));
        String fee = request.getParameter("fee");
        String gainsAndLosses = request.getParameter("gainsAndLosses");
        String currValue = request.getParameter("currValue");
        String totleGainsAndLosses = request.getParameter("totleGainsAndLosses");
        String currIncome = request.getParameter("currIncome");
        String currOutcome = request.getParameter("currOutcome");
        record.setFee(getRealLong(fee));
        record.setCurrIncome(getRealLong(currIncome));
        record.setCurrOutcome(getRealLong(currOutcome));
        record.setTotalGainsAndLosses(totleGainsAndLosses);
        record.setGainsAndLosses(getRealLong(gainsAndLosses));
        record.setCurrValue(getRealLong(currValue));
        record.setGmtModified(DateUtil.getNowDate());
        return record;
    }

    private Long getRealLong(String value) {
        try {
            if (StringUtils.isBlank(value)) {
                return 0L;
            }
            return (long) (Float.parseFloat(value) * 100);
        } catch (Exception e) {
            return 0L;
        }
    }

    @Log(message = "删除了transRecord,id:{0}。")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Long id) {
        try {
            transRecordService.delete(id);
        } catch (Exception e) {
            return AjaxReturnInfo.returnErr("删除交易失败!");
        }
        return AjaxReturnInfo.returnSuc("成功删除交易!");
    }

    @Log(message = "删除了transRecord,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "transRecord删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] transRecordTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizTransRecord transRecord = transRecordService.get(ids[i]);
            transRecordService.delete(transRecord.getId());

            transRecordTypes[i] = transRecord.getId().toString();
        }
        // 记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(transRecordTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        List<BizTransRecord> transRecords = null;
        transRecords = transRecordService.findAll(page);
        map.put("page", page);
        map.put("transRecords", transRecords);
        return JSONObject.toJSONString(map);
    }

    @RequestMapping(value = "/findAll", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String findAll(Page page, Map<String, Object> map,
                                        @RequestParam String type) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        List<BizTransRecord> list = transRecordService.findAllByType(page, type);
        if (CollectionUtils.isEmpty(list)) {
            return PageUtils.toJsonString(page, new ArrayList<Map<String, String>>());
        }
        return PageUtils.toJsonString(page, list);
    }

    /**
     * 初期资金
     * 
     * @param type
     * @return
     */
    @RequestMapping(value = "/findFristTransRecord", method = { RequestMethod.GET,
            RequestMethod.POST })
    public @ResponseBody String findFristTransRecord(@RequestParam String type) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        long memberId = member.getId();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        BizTransRecordVO transRecord = transRecordService.findFirstTransRecord(memberId, type);
        if (transRecord != null) {
            paramMap.put(BizTransRecordConstant.EXISTENCE_OF_FIRST,
                BizTransRecordConstant.EXIST_OF_FIRST);
            paramMap
                .put(BizTransRecordConstant.ORIGION_VALUES,
                    transRecord.getOrigionValue() == null ? 0.0
                        : transRecord.getOrigionValue() / 100.0);
        } else {
            paramMap.put(BizTransRecordConstant.EXISTENCE_OF_FIRST,
                BizTransRecordConstant.NOT_EXIST_OF_FIRST);
        }
        return AjaxReturnInfo.returnSuc(paramMap);
    }

    /**
     * 
     * 上日权益级累计信息查询
     * 
     * @param type
     * @param dateStr
     * @return
     */
    @RequestMapping(value = "/findLastTransRecord", method = { RequestMethod.GET,
            RequestMethod.POST })
    public @ResponseBody String findLastTransRecord(@RequestParam String type,
                                                    @RequestParam String dateStr) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        long memberId = member.getId();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 得到上日权益
        try {
            // 当日交易
            BizTransRecordVO currRecord = transRecordService.findCurrTransRecord(memberId, type,
                dateStr);
            if (currRecord != null) {
                return AjaxReturnInfo.returnErr("当天记录不得重复录入");
            }
            // 上日交易
            BizTransRecordVO vo = transRecordService.findPreviousTransRecord(memberId, type,
                DateUtil.string2Date(dateStr, DateUtil.PATTERN_DATE));
            if (vo == null) {// 表示上日权益没有
                paramMap.put("isHave", "false");
            } else {
                paramMap.put("isHave", "true");
                paramMap.put("lastDayValue", vo.getCurrValue() / 100.0);
            }
            BizTransRecordVO totalInfo = transRecordService.queryTotalInfoBeforeOneDate(memberId,
                type, dateStr);
            paramMap.put("totalInCome",
                totalInfo.getTotalIncome() == null ? 0 : totalInfo.getTotalIncome() / 100.0);
            paramMap.put("totalOutCome",
                totalInfo.getTotalOutcome() == null ? 0 : totalInfo.getTotalOutcome() / 100.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxReturnInfo.returnSuc(paramMap);
    }

    @RequestMapping(value = "/findLastTransRecord1", method = { RequestMethod.GET,
            RequestMethod.POST })
    public @ResponseBody String findLastTransRecord1(@RequestParam String type,
                                                     @RequestParam String dateStr) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        long memberId = member.getId();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 得到上日权益
        try {
            // 上日交易
            BizTransRecordVO vo = transRecordService.findPreviousTransRecord(memberId, type,
                DateUtil.string2Date(dateStr, DateUtil.PATTERN_DATE));
            if (vo == null) {// 表示上日权益没有
                paramMap.put("isHave", "false");
            } else {
                paramMap.put("isHave", "true");
                paramMap.put("lastDayValue", vo.getCurrValue() / 100.0);
            }
            BizTransRecordVO totalInfo = transRecordService.queryTotalInfoBeforeOneDate(memberId,
                type, dateStr);
            paramMap.put("totalInCome",
                totalInfo.getTotalIncome() == null ? 0 : totalInfo.getTotalIncome() / 100.0);
            paramMap.put("totalOutCome",
                totalInfo.getTotalOutcome() == null ? 0 : totalInfo.getTotalOutcome() / 100.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxReturnInfo.returnSuc(paramMap);
    }

    @RequestMapping(value = "/findLastTransRecord2", method = { RequestMethod.GET,
            RequestMethod.POST })
    public @ResponseBody String findLastTransRecord2(@RequestParam String type,
                                                     @RequestParam String dateStr,
                                                     @RequestParam Long id) {
        BizTransRecord record = transRecordService.get(id);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 得到上日权益
        try {
            // 上日交易
            BizTransRecordVO vo = transRecordService.findPreviousTransRecord(record.getMemberId(),
                type, DateUtil.string2Date(dateStr, DateUtil.PATTERN_DATE));
            if (vo == null) {// 表示上日权益没有
                paramMap.put("isHave", "false");
            } else {
                paramMap.put("isHave", "true");
                paramMap.put("lastDayValue", vo.getCurrValue() / 100.0);
            }
            BizTransRecordVO totalInfo = transRecordService.queryTotalInfoBeforeOneDate(
                record.getMemberId(), type, dateStr);
            paramMap.put("totalInCome",
                totalInfo.getTotalIncome() == null ? 0 : totalInfo.getTotalIncome() / 100.0);
            paramMap.put("totalOutCome",
                totalInfo.getTotalOutcome() == null ? 0 : totalInfo.getTotalOutcome() / 100.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxReturnInfo.returnSuc(paramMap);
    }

    /**
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/findTransRecordValues", method = { RequestMethod.GET,
            RequestMethod.POST })
    public @ResponseBody String findTransRecordValues(HttpServletRequest request) {
        String memberNo = request.getParameter("memberNo");
        String investType = request.getParameter("investType");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder dates = new StringBuilder();
        StringBuilder values = new StringBuilder();
        try {
            // 获取大师信息
            BizMember member = memberService.getBizMemberByMemberNo(memberNo);
            if (null == member) {
                return AjaxReturnInfo.returnErr("该大师不存在");
            }
            List<BizTransRecordVO> list = transRecordService
                .findTransRecrorByMemberAndTypeAndDateAndStatus(member.getId(), investType,
                    startDate, endDate, "S");
            for (BizTransRecordVO vo : list) {
                dates.append(
                    DateUtil.date2String(
                        DateUtil.string2Date(vo.getImportDate(), DateUtil.PATTERN_DATE), "MMdd"))
                    .append(",");
                values.append(vo.getCurrValue() / 100.0).append(",");
            }
            paramMap.put("dates", StringUtil.deleteLastOfChar(dates, ","));
            paramMap.put("values", StringUtil.deleteLastOfChar(values, ","));
        } catch (Exception e) {
            // 错误日志打印
            // 跳转到错误页面
            e.printStackTrace();
        }
        return AjaxReturnInfo.returnSuc(paramMap);
    }

    @RequestMapping(value = "/findTransRecordTotal", method = { RequestMethod.GET,
            RequestMethod.POST })
    public @ResponseBody String findTransRecordTotal(HttpServletRequest request) {
        String memberNo = request.getParameter("memberNo");
        String investType = request.getParameter("investType");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder dates = new StringBuilder();
        StringBuilder values = new StringBuilder();
        try {
            // 获取大师信息
            BizMember member = memberService.getBizMemberByMemberNo(memberNo);
            if (null == member) {
                return AjaxReturnInfo.returnErr("该大师不存在");
            }
            List<BizTransRecordVO> list = transRecordService
                .findTransRecrorByMemberAndTypeAndDateAndStatus(member.getId(), investType,
                    startDate, endDate, "S");
            for (BizTransRecordVO vo : list) {
                if (StringUtil.isEmpty(vo.getTotalGainsAndLosses())) {
                    values.append(0).append(",");
                } else {
                    values.append(
                        StringUtil.substring(vo.getTotalGainsAndLosses(), vo
                            .getTotalGainsAndLosses().length() - 1)).append(",");
                }
                dates.append(
                    DateUtil.date2String(
                        DateUtil.string2Date(vo.getImportDate(), DateUtil.PATTERN_DATE), "MMdd"))
                    .append(",");
            }
            paramMap.put("dates", StringUtil.deleteLastOfChar(dates, ","));
            paramMap.put("values", StringUtil.deleteLastOfChar(values, ","));
        } catch (Exception e) {
            // 错误日志打印
            // 跳转到错误页面
            e.printStackTrace();
        }
        return AjaxReturnInfo.returnSuc(paramMap);
    }

    /**
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/findTransRrcordByMemberAndTypeForWeek", method = { RequestMethod.GET,
            RequestMethod.POST })
    public @ResponseBody String findTransRrcordByMemberAndTypeForWeek(Page page,
                                                                      Map<String, Object> map,
                                                                      HttpServletRequest request) {
        String memberNo = request.getParameter("memberNo");
        // 获取大师信息
        BizMember member = memberService.getBizMemberByMemberNo(memberNo);
        if (null == member) {
            return AjaxReturnInfo.returnErr("该大师不存在");
        }
        List<BizTransRecordVO> recordVOS = transRecordService
            .findTransRrcordByMemberAndTypeAndStatusForWeek(page, member.getId(), "S");
        List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
        double f = (100.0);
        for (BizTransRecordVO recordVO : recordVOS) {
            Map<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("id", recordVO.getId() + "");
            tempMap.put("investTypeStr",
                InvestDirectionEnum.getInvestDirectionEnum(recordVO.getInvestType()).getName());
            tempMap.put("origionValueWYuan", getRealNumWithTwo(recordVO.getOrigionValue() / f + "")
                                             + "元");
            tempMap
                .put("currValueWYuan", getRealNumWithTwo(recordVO.getCurrValue() / f + "") + "元");
            tempMap.put("importDate", recordVO.getImportDate());
            temp.add(tempMap);
        }
        return PageUtils.toJsonString(page, temp);
    }

    private String getRealNumWithTwo(String number) {
        int index = 0;
        if ((index = StringUtils.indexOf(number, ".")) != -1) {
            return StringUtils.substring(number, 0, index + 2);
        }
        return number;
    }

    @RequestMapping(value = "/reviewList", method = { RequestMethod.GET, RequestMethod.POST })
    public String reviewList(Page page, String keywords, Map<String, Object> map) {
        List<BizTransRecordVO> records = transRecordService.findTransRecordToReview(page);
        List<Map<String, String>> recordList = new ArrayList<Map<String, String>>();
        if (!CollectionUtils.isEmpty(records)) {
            Map<Long, BizMember> cacheMap = new HashMap<Long, BizMember>();
            for (BizTransRecordVO vo : records) {
                Map<String, String> tempMap = new HashMap<String, String>();
                String name = "该用户已经被删除！";
                String memberNo = "无";
                String isVal = "";
                if (!StringUtils.isBlank(vo.getMemberId())) {
                    Long memberId = Long.parseLong(vo.getMemberId());
                    BizMember member = cacheMap.get(memberId) == null ? memberService.get(memberId)
                        : cacheMap.get(memberId);
                    name = member.getName();
                    memberNo = member.getMemberNo();
                    BizInvestion investion = investionService.findByMemberIdAndInvestDirection(
                        Long.parseLong(vo.getMemberId()), vo.getInvestType());
                    if (null == investion) {
                        isVal = "无";
                    } else {
                        isVal = StringUtils.equals(investion.getIsValidated(), "1") ? "赛" : "无";
                    }
                }
                tempMap.put("id", vo.getId() + "");
                tempMap.put("investTypeStr",
                    InvestDirectionEnum.getInvestDirectionEnum(vo.getInvestType()).getName());
                tempMap.put("name", name);
                tempMap.put("memberNo", memberNo);
                tempMap.put("isVal", isVal);
                tempMap.put("origionValue", getRealString(vo.getOrigionValue()) + "");
                tempMap.put("currValue", getRealString(vo.getCurrValue()) + "");
                tempMap.put("lastDayValue", getRealString(vo.getLastDayValue()) + "");
                tempMap.put("fee", getRealString(vo.getFee()) + "");
                tempMap.put("gainsAndLosses", getRealString(vo.getGainsAndLosses()) + "");
                tempMap.put("currIncome", getRealString(vo.getCurrIncome()) + "");
                tempMap.put("currOutcome", getRealString(vo.getCurrOutcome()) + "");
                recordList.add(tempMap);
            }
        }
        map.put("page", page);
        map.put("records", recordList);
        map.put("keywords", keywords);
        return LIST_REVIEW;
    }

    private String getRealString(Long value) {
        if (null == value) {
            return "0";
        }
        return value / 100.0 + "";
    }

    @RequestMapping(value = "/reviewedList", method = { RequestMethod.GET, RequestMethod.POST })
    public String reviewedList(Page page, String keywords, Map<String, Object> map) {
        List<BizTransRecordVO> records = transRecordService.findTransRecordReviewed(page);
        map.put("page", page);
        map.put("records", records);
        map.put("keywords", keywords);
        return LIST_REVIEWED;
    }

    /**
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/myTransRecords", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String myTransRecords(Page page, Map<String, Object> map,
                                               HttpServletRequest request) {
        BizMember member = (BizMember) HttpReceiver.getHttpSession().getAttribute("member");
        if (null == member) {
            return AjaxReturnInfo.returnErr("请您先首页登录！");
        }
        List<BizTransRecordVO> recordVOS = transRecordService.findTransRecordByUserForSubmit(page,
            member.getId());
        List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
        double divi = 100.0;
        for (BizTransRecordVO recordVO : recordVOS) {
            Map<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("id", recordVO.getId() + "");
            tempMap.put("investTypeStr", recordVO.getInvestTypeStr());
            tempMap.put("importDate", recordVO.getImportDate());
            tempMap.put("currIncomeWYuan",
                (null == recordVO.getCurrIncome() ? 0 : recordVO.getCurrIncome()) / divi + "");
            tempMap.put("currOutcomeWYuan",
                (null == recordVO.getCurrOutcome() ? 0 : recordVO.getCurrOutcome()) / divi + "");
            tempMap.put("currValueWYuan", recordVO.getCurrValue() / divi + "");
            tempMap.put("lastDayValueWYuan",
                (null == recordVO.getLastDayValue() ? 0 : recordVO.getLastDayValue()) / divi + "");
            tempMap.put("status", recordVO.getStatus());
            String statusName = "";
            if (StringUtils.equals(recordVO.getStatus(), "I")) {
                statusName = "审核中";
            } else if (StringUtils.equals(recordVO.getStatus(), "S")) {
                statusName = "审核通过";
            } else if (StringUtils.equals(recordVO.getStatus(), "F")) {
                BizReviewLog log = reviewLogService.getBizReviewLog(recordVO.getId(),
                    "TRANS_RECORD");
                if (null != log) {
                    tempMap.put("failReason", log.getComment());
                }
                statusName = "审核退回";
            } else {
                statusName = "莫名其妙";
            }
            tempMap.put("statusName", statusName);
            temp.add(tempMap);
        }
        return PageUtils.toJsonString(page, temp);
    }

    @RequestMapping(value = "/showLast", method = { RequestMethod.GET, RequestMethod.POST })
    public String showLast(Page page, String keywords, Map<String, Object> map) {
        List<BizTransRecordVO> records = transRecordService.findTransRecordToReview(page);
        List<Map<String, String>> recordList = new ArrayList<Map<String, String>>();
        if (!CollectionUtils.isEmpty(records)) {
            Map<Long, BizMember> cacheMap = new HashMap<Long, BizMember>();
            for (BizTransRecordVO vo : records) {
                Map<String, String> tempMap = new HashMap<String, String>();
                String name = "该用户已经被删除！";
                String memberNo = "无";
                String isVal = "";
                if (!StringUtils.isBlank(vo.getMemberId())) {
                    Long memberId = Long.parseLong(vo.getMemberId());
                    BizMember member = cacheMap.get(memberId) == null ? memberService.get(memberId)
                        : cacheMap.get(memberId);
                    name = member.getName();
                    memberNo = member.getMemberNo();
                    BizInvestion investion = investionService.findByMemberIdAndInvestDirection(
                        Long.parseLong(vo.getMemberId()), vo.getInvestType());
                    if (null == investion) {
                        isVal = "无";
                    } else {
                        isVal = StringUtils.equals(investion.getIsValidated(), "1") ? "赛" : "无";
                    }
                }
                tempMap.put("id", vo.getId() + "");
                tempMap.put("investTypeStr",
                    InvestDirectionEnum.getInvestDirectionEnum(vo.getInvestType()).getName());
                tempMap.put("name", name);
                tempMap.put("memberNo", memberNo);
                tempMap.put("isVal", isVal);
                tempMap.put("origionValue", getRealString(vo.getOrigionValue()) + "");
                tempMap.put("currValue", getRealString(vo.getCurrValue()) + "");
                tempMap.put("lastDayValue", getRealString(vo.getLastDayValue()) + "");
                tempMap.put("fee", getRealString(vo.getFee()) + "");
                tempMap.put("gainsAndLosses", getRealString(vo.getGainsAndLosses()) + "");
                tempMap.put("currIncome", getRealString(vo.getCurrIncome()) + "");
                tempMap.put("currOutcome", getRealString(vo.getCurrOutcome()) + "");
                recordList.add(tempMap);
            }
        }
        map.put("page", page);
        map.put("records", recordList);
        map.put("keywords", keywords);
        return SHOW_LAST;
    }

    @RequestMapping(value = "/showDaily", method = { RequestMethod.GET, RequestMethod.POST })
    public String showDaily(Page page, String keywords, Map<String, Object> map) {
        List<BizTransRecordVO> records = transRecordService.findDailyTransRecord(page);
        map.put("page", page);
        map.put("records", records);
        return SHOW_DAILY;
    }

    @RequestMapping(value = "/showDetailPer/{date}", method = { RequestMethod.GET,
            RequestMethod.POST })
    public String showDailyPer(Page page, @PathVariable String date, Map<String, Object> map) {
        List<BizFinanceTransDetail> list = financeTransDetailService.findByGmtCreate(page, date);
        List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
        Map<Long, BizMember> cacheMap = new HashMap<Long, BizMember>();
        if (!CollectionUtils.isEmpty(list)) {
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
                tempMap.put("left", detail.getSysLeft() + "");
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
            map.put("page", page);
            map.put("data", tempList);
        }
        return SHOW_DETAIL_PER;
    }

    @RequestMapping(value = "/clear", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String clear(Long[] ids) {
        evenName = "删除交易记录";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            if (null == ids || ids.length == 0) {

            } else {
                for (long id : ids) {
                    transRecordService.deleteRecordByMemberId(id);
                }
            }
        } catch (Exception e) {
            logger.error("删除交易信息异常！", e);
            ajaxObject = new AjaxObject(evenName + "失败！");
        }
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }
}
