package org.troy.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.troy.biz.constant.BizTransRecordConstant;
import org.troy.biz.controller.form.TransRecordReviewForm;
import org.troy.biz.dao.BizAttachDao;
import org.troy.biz.dao.BizReviewLogDao;
import org.troy.biz.dao.BizTransRecordDao;
import org.troy.biz.dao.BizTransRecordStatisDao;
import org.troy.biz.entity.BizAttach;
import org.troy.biz.entity.BizReviewLog;
import org.troy.biz.entity.BizTransRecord;
import org.troy.biz.entity.BizTransRecordStatis;
import org.troy.biz.entity.vo.BizTransRecordVO;
import org.troy.biz.enums.AttachTypeEnum;
import org.troy.biz.enums.CostTypeEnum;
import org.troy.biz.enums.TransStatusEnum;
import org.troy.biz.service.TransRecordService;
import org.troy.biz.service.TransRecordStatisService;
import org.troy.biz.util.BizTransRecordUtil;
import org.troy.biz.util.MultipartFileUtil;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.StringUtil;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.util.WorkDayUtil;

/***
 * <p>
 * Title:TransRecordServiceImpl
 * </p>
 * 
 * <p>
 * Description:TransRecord 业务接口实现
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
@Service
public class TransRecordServiceImpl extends BaseJdbcServiceImpl implements TransRecordService {

    @Autowired
    private BizTransRecordDao        transRecordDao;

    @Autowired
    private BizTransRecordStatisDao  bizTransRecordStatisDao;

    @Autowired
    private BizAttachDao             bizAttachDao;

    @Autowired
    private BizReviewLogDao          bizReviewLogDao;

    @Autowired
    private TransRecordStatisService transRecordStatisService;

    @Autowired
    private ThreadPoolTaskExecutor   taskExecutor;

    private final static String      ORDER_SQL   = " order by STR_TO_DATE(import_date,'%Y-%m-%d') ";

    private final static String      SELECT_SQL1 = " select total_gains_and_losses, import_date,curr_value,status from biz_trans_record where member_id=? and invest_type=? and curr_value is not null and status=? ";

    private final static String      SELECT_SQL2 = SELECT_SQL1
                                                   + " and STR_TO_DATE(import_date,'%Y-%m-%d')<=? and status=? "
                                                   + ORDER_SQL;

    private final static String      SELECT_SQL3 = SELECT_SQL1
                                                   + " and STR_TO_DATE(import_date,'%Y-%m-%d')>=? and status=? "
                                                   + ORDER_SQL;

    private final static String      SELECT_SQL4 = SELECT_SQL1
                                                   + " and (STR_TO_DATE(import_date,'%Y-%m-%d')>=? and STR_TO_DATE(import_date,'%Y-%m-%d')<=?)  and status=?  "
                                                   + ORDER_SQL;

    private final static String      SELECT_SQL5 = " select sum(t.curr_income) as total_Income,sum(t.curr_outcome) as total_Outcome from biz_trans_record t where t.member_id=? and t.invest_type=? and STR_TO_DATE(t.import_date,'%Y-%m-%d')<? ";

    private final static String      SELECT_SQL6 = " select id from biz_trans_record where member_id=? and invest_type=?  order by  STR_TO_DATE(import_date,'%Y-%m-%d') desc limit 0,1";

    private final static String      SELECT_SQL7 = " SELECT  DATE_FORMAT(gmt_create,'%Y-%m-%d') as import_date,ifnull((select sum(value) from biz_finance_trans_detail where DATE_FORMAT(gmt_create,'%Y-%m-%d') = import_date and trans_type = 'out'),0) as curr_outcome ,ifnull((select sum(value) from biz_finance_trans_detail where DATE_FORMAT(gmt_create,'%Y-%m-%d') = import_date  and trans_type = 'in'),0) as curr_income FROM biz_finance_trans_detail group by import_date";

    private final static String      SELECT_SQL8 = "SELECT curr_income as currIncome,curr_outcome as currOutcome,gains_and_losses as gainsAndLosses FROM biz_trans_record WHERE id = ?";

    /**
     * @param transRecord
     * @see org.troy.biz.service.TransRecordService#save(org.troy.biz.entity.BizTransRecord
     *      )
     */
    @Transactional
    public void save(BizTransRecord transRecord, MultipartFile[] myfiles1,
                     MultipartFile[] myfiles2, Map<String, String> extendParam)
                                                                               throws ServiceException {
        Date date = DateUtil.getNowDate();
        transRecord.setGmtCreate(date);
        transRecord.setGmtModified(date);
        transRecordDao.save(transRecord);
        List<BizAttach> attachList1 = MultipartFileUtil.convertFileInfo(myfiles1,
            AttachTypeEnum.FINANCIAL_INTERESTS_AND_POSITIONS_PIC1, CostTypeEnum.COSTTYPE_00,
            transRecord.getId());
        List<BizAttach> attachList2 = MultipartFileUtil.convertFileInfo(myfiles2,
            AttachTypeEnum.FINANCIAL_INTERESTS_AND_POSITIONS_PIC2, CostTypeEnum.COSTTYPE_01,
            transRecord.getId());
        bizAttachDao.save(attachList1);
        bizAttachDao.save(attachList2);
    }

    /**
     * @param transRecord
     * @see org.troy.biz.service.TransRecordService#save(org.troy.biz.entity.
     *      BizTransRecord
     * 
     */
    @Transactional
    public void save(BizTransRecord transRecord) throws ServiceException {
        transRecordDao.save(transRecord);
    }

    /**
     * @param id
     * @see org.troy.biz.service.TransRecordService#delete(java.lang.Long)
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        transRecordDao.delete(id);
    }

    /**
     * @param transRecord
     * @see org.troy.biz.service.TransRecordService#update(org.troy.biz.entity.BizTransRecord
     *      )
     */
    @Transactional
    public void update(BizTransRecord transRecord) throws ServiceException {
        transRecordDao.save(transRecord);
    }

    /**
     * @param id
     * @return
     * @see org.troy.biz.service.TransRecordService#get(java.lang.Long)
     */
    @Transactional(readOnly = true)
    public BizTransRecord get(Long id) {
        return transRecordDao.findOne(id);
    }

    /**
     * @return
     * @see org.troy.biz.service.TransRecordService#findAll(com.cnnct.common.utils.dwz.Page)
     */
    @Transactional(readOnly = true)
    public List<BizTransRecord> findAll(Page page) {
        org.springframework.data.domain.Page<BizTransRecord> springDataPage = transRecordDao
            .findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    /**
     * @see org.troy.biz.service.TransRecordService#getBizTransRecordVO(java.lang.Long)
     */
    @Override
    public BizTransRecordVO getBizTransRecordVO(Long id) {
        BizTransRecord record = get(id);
        return BizTransRecordUtil.convertBizTransRecordVO(record);
    }

    /**
     * 通过type分页查询
     * 
     * @param page
     */
    @Override
    @Transactional(readOnly = true)
    public List<BizTransRecord> findAllByType(Page page, String type) {
        // return
        // PageUtils.getListData(transRecordDao.findByInvestTypeOrderByGmtCreateDesc(PageUtils
        // .createPageable(page), type), page);
        return null;
    }

    /**
     * @see org.troy.biz.service.TransRecordService#findFirstTransRecord(int,
     *      java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public BizTransRecordVO findFirstTransRecord(long memberId, String type) {
        BizTransRecord record = transRecordDao.findByMemberIdAndInvestTypeAndRecordType(memberId,
            type, BizTransRecordConstant.FIRST_RECORD_TYPE);
        return BizTransRecordUtil.convertBizTransRecordVO(record);
    }

    /**
     * @see org.troy.biz.service.TransRecordService#findCurrTransRecord(long,
     *      java.lang.String, java.util.Date)
     */
    @Override
    public BizTransRecordVO findCurrTransRecord(long memberId, String investType, String currDate) {
        BizTransRecord record = transRecordDao.findByMemberIdAndInvestTypeAndImportDate(memberId,
            investType, currDate);
        return BizTransRecordUtil.convertBizTransRecordVO(record);
    }

    /**
     * @throws Exception
     * @see org.troy.biz.service.TransRecordService#findPreviousTransRecord(long,
     *      java.lang.String, java.util.Date)
     */
    @Override
    @Transactional(readOnly = true)
    public BizTransRecordVO findPreviousTransRecord(long memberId, String investType, Date currDate) {
        // 得到上个工作日日期
        String beforeDateStr = DateUtil.date2String(WorkDayUtil.getLastWorkDate(currDate),
            DateUtil.PATTERN_DATE);
        BizTransRecord record = transRecordDao.findByMemberIdAndInvestTypeAndImportDate(memberId,
            investType, beforeDateStr);
        return BizTransRecordUtil.convertBizTransRecordVO(record);
    }

    /**
     * @throws Exception
     * @see org.troy.biz.service.TransRecordService#queryTotalInfoBeforeOneDate(long,
     *      java.lang.String, java.util.Date)
     */
    @Override
    @Transactional(readOnly = true)
    public BizTransRecordVO queryTotalInfoBeforeOneDate(long memberId, String investType,
                                                        String currDate) throws Exception {
        Object[] params = new Object[3];
        params[0] = memberId;
        params[1] = investType;
        params[2] = currDate;
        return (BizTransRecordVO) super.queryForObject(SELECT_SQL5, params, BizTransRecordVO.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<BizTransRecordVO> findTransRecrorByMemberAndTypeAndDate(Long memberId,
                                                                        String investType,
                                                                        String startDate,
                                                                        String endDate)
                                                                                       throws Exception {
        String sql = null;
        Object[] params = null;
        if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
            params = new Object[4];
            setPamramForFindTransRecrorByMemberAndTypeAndDate(params, memberId, investType);
            params[2] = DateUtil.string2Date(startDate, DateUtil.PATTERN_DATE);
            params[3] = DateUtil.string2Date(endDate, DateUtil.PATTERN_DATE);
            ;
            sql = SELECT_SQL4;
        } else if (StringUtil.isNotEmpty(startDate) && StringUtil.isEmpty(endDate)) {
            params = new Object[3];
            setPamramForFindTransRecrorByMemberAndTypeAndDate(params, memberId, investType);
            params[2] = DateUtil.string2Date(startDate, DateUtil.PATTERN_DATE);
            sql = SELECT_SQL3;
        } else if (StringUtil.isEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
            params = new Object[3];
            setPamramForFindTransRecrorByMemberAndTypeAndDate(params, memberId, investType);
            params[2] = DateUtil.string2Date(endDate, DateUtil.PATTERN_DATE);
            ;
            sql = SELECT_SQL2;
        } else if (StringUtil.isEmpty(startDate) && StringUtil.isEmpty(endDate)) {
            params = new Object[3];
            setPamramForFindTransRecrorByMemberAndTypeAndDate(params, memberId, investType);
            sql = SELECT_SQL1;
        }
        List<BizTransRecord> records = (List<BizTransRecord>) super.queryForList(sql, params,
            BizTransRecord.class);
        return BizTransRecordUtil.convertBizTransRecordVOList(records);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<BizTransRecordVO> findTransRecrorByMemberAndTypeAndDateAndStatus(Long memberId,
                                                                                 String investType,
                                                                                 String startDate,
                                                                                 String endDate,
                                                                                 String status)
                                                                                               throws Exception {
        String sql = null;
        Object[] params = null;
        if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
            params = new Object[6];
            setPamramForFindTransRecrorByMemberAndTypeAndDate(params, memberId, investType);
            params[2] = status;
            params[3] = DateUtil.string2Date(startDate, DateUtil.PATTERN_DATE);
            params[4] = DateUtil.string2Date(endDate, DateUtil.PATTERN_DATE);
            params[5] = status;
            sql = SELECT_SQL4;
        } else if (StringUtil.isNotEmpty(startDate) && StringUtil.isEmpty(endDate)) {
            params = new Object[4];
            setPamramForFindTransRecrorByMemberAndTypeAndDate(params, memberId, investType);
            params[2] = DateUtil.string2Date(startDate, DateUtil.PATTERN_DATE);
            params[3] = status;
            sql = SELECT_SQL3;
        } else if (StringUtil.isEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
            params = new Object[4];
            setPamramForFindTransRecrorByMemberAndTypeAndDate(params, memberId, investType);
            params[2] = DateUtil.string2Date(endDate, DateUtil.PATTERN_DATE);
            params[3] = status;
            sql = SELECT_SQL2;
        } else if (StringUtil.isEmpty(startDate) && StringUtil.isEmpty(endDate)) {
            params = new Object[3];
            setPamramForFindTransRecrorByMemberAndTypeAndDate(params, memberId, investType);
            params[2] = status;
            sql = SELECT_SQL1;
        }
        List<BizTransRecord> records = (List<BizTransRecord>) super.queryForList(sql, params,
            BizTransRecord.class);
        return BizTransRecordUtil.convertBizTransRecordVOList(records);
    }

    /**
     * set param
     * 
     * @param params
     * @param memberId
     * @param investType
     */
    private static void setPamramForFindTransRecrorByMemberAndTypeAndDate(Object[] params,
                                                                          Long memberId,
                                                                          String investType) {
        params[0] = memberId;
        params[1] = investType;
    }

    /**
     * @see org.troy.biz.service.TransRecordService#findTransRrcordByMenberAndTypeForWeek(org.troy.common.utils.dwz.Page,
     *      java.lang.Long, java.lang.String)
     */
    @Override
    public List<BizTransRecordVO> findTransRrcordByMemberAndTypeForWeek(Page page, Long memberId) {
        String dateStr = DateUtil.date2String(DateUtil.getBeforeDate(7), DateUtil.PATTERN_DATE);
        List<BizTransRecord> records = PageUtils.getListData(
            transRecordDao.findByMemberIdAndImportDateAfterOrderByGmtCreateDesc(
                PageUtils.createPageable(page), memberId, dateStr), page);
        return BizTransRecordUtil.convertBizTransRecordVOList(records);
    }

    /**
     * @see org.troy.biz.service.TransRecordService#findTransRecordToReview(org.troy.common.utils.dwz.Page)
     */
    @Override
    public List<BizTransRecordVO> findTransRecordToReview(Page page) {
        List<BizTransRecord> records = PageUtils.getListData(transRecordDao
            .findByStatusOrderByGmtCreateDesc(PageUtils.createPageable(page),
                TransStatusEnum.I.getValue()), page);
        return BizTransRecordUtil.convertBizTransRecordVOList(records);
    }

    @Override
    public List<BizTransRecordVO> findTransRecordReviewed(Page page) {
        List<BizTransRecord> records = PageUtils.getListData(transRecordDao
            .findByStatusNotOrderByGmtCreateDesc(PageUtils.createPageable(page),
                TransStatusEnum.I.getValue()), page);
        return BizTransRecordUtil.convertBizTransRecordVOList(records);
    }

    @Override
    public List<BizTransRecordVO> findTransRecordByUserForSubmit(Page page, Long memberId) {
        List<BizTransRecord> records = PageUtils.getListData(transRecordDao
            .findByMemberIdOrderByGmtCreateDesc(PageUtils.createPageable(page), memberId), page);
        return BizTransRecordUtil.convertBizTransRecordVOList(records);
    }

    @Override
    public List<BizTransRecordVO> findTransRecordByUserForSubmit(Page page, Long memberId,
                                                                 String investType) {
        List<BizTransRecord> records = PageUtils.getListData(
            transRecordDao.findByMemberIdAndStatusOrderByGmtCreateDesc(
                PageUtils.createPageable(page), memberId, TransStatusEnum.I.getValue()), page);
        return BizTransRecordUtil.convertBizTransRecordVOList(records);
    }

    @Override
    public List<BizTransRecordVO> findTransRecordByUserValid(Page page, Long memberId,
                                                             String investType) {
        List<BizTransRecord> records = PageUtils.getListData(
            transRecordDao.findByMemberIdAndStatusOrderByGmtCreateDesc(
                PageUtils.createPageable(page), memberId, TransStatusEnum.S.getValue()), page);
        return BizTransRecordUtil.convertBizTransRecordVOList(records);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizTransRecordVO> findTransRrcordByMemberAndTypeAndStatusForWeek(Page page,
                                                                                 Long memberId,
                                                                                 String status) {
        List<BizTransRecord> records = PageUtils.getListData(
            transRecordDao.findByMemberIdAndStatusOrderByGmtCreateDesc(
                PageUtils.createPageable(page), memberId, status), page);
        return BizTransRecordUtil.convertBizTransRecordVOList(records);
    }

    @Override
    @Transactional
    public void saveWithStatisAndReview(final BizTransRecord transRecord, TransRecordReviewForm form)
                                                                                                     throws ServiceException {
        if (null == transRecord || null == form) {
            return;
        }
        transRecordDao.save(transRecord);
        List<BizTransRecord> records = new ArrayList<BizTransRecord>(1);
        records.add(transRecord);
        BizTransRecordStatis statis = bizTransRecordStatisDao.findByMemberIdAndInvestType(
            transRecord.getMemberId(), transRecord.getInvestType());
        if (!StringUtils.equals(transRecord.getStatus(), TransStatusEnum.F.getValue())) {
            if (null == statis) {
                statis = create(transRecord);
            } else {
                update(transRecord, statis);
            }
        }
        BizReviewLog log = new BizReviewLog();
        log.setBizId(transRecord.getId());
        log.setBizType("TRANS_RECORD");
        log.setComment(form.getComment());
        Date date = DateUtil.getCurrDate();
        log.setGmtCreate(date);
        log.setGmtModified(date);
        bizReviewLogDao.save(log);

        final BizTransRecordStatis temp = statis;

        //利用线程池来进行排名的统计重建
        taskExecutor.execute(new Runnable() {

            @Override
            public void run() {
                transRecordStatisService.executeRebuildRank(transRecord.getInvestType(), temp);
            }
        });

    }

    @Override
    @Transactional
    public void modifyLastRecord(final BizTransRecord transRecord) throws ServiceException {
        if (null == transRecord) {
            return;
        }
        BizTransRecordStatis statis = bizTransRecordStatisDao.findByMemberIdAndInvestType(
            transRecord.getMemberId(), transRecord.getInvestType());
        modifyLastRecord(transRecord, statis);
        transRecordDao.save(transRecord);
    }

    private BizTransRecordStatis create(BizTransRecord transrecord) {
        BizTransRecordStatis recordStatic = new BizTransRecordStatis();
        recordStatic.setMemberId(transrecord.getMemberId());
        recordStatic.setTotalIncome(transrecord.getCurrIncome());
        recordStatic.setTotalOutcome(transrecord.getCurrOutcome());
        recordStatic.setTotal(transrecord.getGainsAndLosses());
        recordStatic.setInvestType(transrecord.getInvestType());
        recordStatic.setOrigionValue(transrecord.getOrigionValue());
        recordStatic.setLastestValue(transrecord.getCurrValue());//最新权益
        Date date = new Date();
        recordStatic.setGmtCreate(date);
        recordStatic.setGmtModified(date);
        recordStatic.setIsValidated("0");
        return bizTransRecordStatisDao.save(recordStatic);
    }

    private void update(BizTransRecord transrecord, BizTransRecordStatis recordStatis) {
        recordStatis.setTotalIncome(recordStatis.getTotalIncome() + transrecord.getCurrIncome());
        recordStatis.setTotalOutcome(recordStatis.getTotalOutcome() + transrecord.getCurrOutcome());
        recordStatis.setTotal(recordStatis.getTotal() + transrecord.getGainsAndLosses());
        recordStatis.setLastestValue(transrecord.getCurrValue());
        recordStatis.setGmtModified(new Date());
        bizTransRecordStatisDao.save(recordStatis);
    }

    private void modifyLastRecord(BizTransRecord transrecord, BizTransRecordStatis recordStatis) {
        BizTransRecord preRecord = findRecordById(transrecord.getId());
        if (null == preRecord) {
            return;
        }
        long finalCurr = recordStatis.getTotalIncome() - preRecord.getCurrIncome()
                         + transrecord.getCurrIncome();

        long finalOutCome = recordStatis.getTotalOutcome() + transrecord.getCurrOutcome()
                            - preRecord.getCurrOutcome();

        long finalTotal = recordStatis.getTotal() + transrecord.getGainsAndLosses()
                          - preRecord.getGainsAndLosses();

        recordStatis.setTotalIncome(finalCurr);
        recordStatis.setTotalOutcome(finalOutCome);
        recordStatis.setTotal(finalTotal);
        recordStatis.setLastestValue(transrecord.getCurrValue());
        recordStatis.setGmtModified(new Date());
        bizTransRecordStatisDao.save(recordStatis);
    }

    @Override
    public List<BizTransRecord> findTransRrcordByMemberAndTypeAndStatus(Page page, Long memberId,
                                                                        String type, String status) {
        return PageUtils.getListData(transRecordDao
            .findTransRrcordByMemberIdAndInvestTypeAndStatusOrderByGmtCreateDesc(
                PageUtils.createPageable(page), memberId, type, status), page);
    }

    @Override
    public BizTransRecordVO findLastRecord(long memberId, String investType) throws Exception {
        Object[] params = new Object[2];
        params[0] = memberId;
        params[1] = investType;
        return (BizTransRecordVO) super.queryForObject(SELECT_SQL6, params, BizTransRecordVO.class);
    }

    private BizTransRecord findRecordById(long id) {
        Object[] params = new Object[1];
        params[0] = id;
        try {
            return (BizTransRecord) super.queryForObject(SELECT_SQL8, params, BizTransRecord.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BizTransRecordVO> findDailyTransRecord(Page page) {
        try {
            return (List<BizTransRecordVO>) super.queryForList(SELECT_SQL7, null,
                BizTransRecordVO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteRecordByMemberId(long memberId) {
        BizTransRecordStatis statis = bizTransRecordStatisDao.findByMemberId(memberId);
        if (null != statis) {
            bizTransRecordStatisDao.delete(statis);

        }
        List<BizTransRecord> records = transRecordDao.findByMemberId(memberId);
        if (!CollectionUtils.isEmpty(records)) {
            transRecordDao.delete(records);
        }
    }

}