package org.troy.biz.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.biz.constant.BizConstant;
import org.troy.biz.dao.BizAttentionRecordDao;
import org.troy.biz.dao.BizFinanceDao;
import org.troy.biz.dao.BizFinanceRecordDao;
import org.troy.biz.dao.BizFinanceTransDetailDao;
import org.troy.biz.dao.BizVisitHistoryDao;
import org.troy.biz.entity.BizAttentionRecord;
import org.troy.biz.entity.BizFinance;
import org.troy.biz.entity.BizFinanceRecord;
import org.troy.biz.entity.BizFinanceTransDetail;
import org.troy.biz.entity.BizVisitHistory;
import org.troy.biz.enums.FinanceEnum;
import org.troy.biz.enums.TransTypeEnum;
import org.troy.biz.enums.TransUserEnum;
import org.troy.biz.service.AttentionRecordService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.util.WorkDayUtil;

/***
 * <p>Title:AttentionRecordServiceImpl </p> 
 *
 * <p>Description:AttentionRecord 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
@Service
public class AttentionRecordServiceImpl extends BaseJdbcServiceImpl implements
                                                                   AttentionRecordService {

    private static final Logger      logger = Logger.getLogger(AttentionRecordServiceImpl.class);

    @Autowired
    private BizAttentionRecordDao    attentionRecordDao;

    @Autowired
    private BizVisitHistoryDao       bizVisitHistoryDao;

    @Autowired
    private BizFinanceDao            bizFinanceDao;

    @Autowired
    private BizFinanceTransDetailDao bizFinanceTransDetailDao;

    @Autowired
    private BizFinanceRecordDao      bizFinanceRecordDao;

    /**   
     * @param attentionRecord  
     * @see org.troy.biz.service.AttentionRecordService#save(org.troy.biz.entity.BizAttentionRecord )  
     */
    @Transactional
    public void save(BizAttentionRecord attentionRecord) throws ServiceException {
        attentionRecordDao.save(attentionRecord);
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.AttentionRecordService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        attentionRecordDao.delete(id);
    }

    /**   
     * @param attentionRecord  
     * @see org.troy.biz.service.AttentionRecordService#update(org.troy.biz.entity.BizAttentionRecord )  
     */
    @Transactional
    public void update(BizAttentionRecord attentionRecord) throws ServiceException {
        attentionRecordDao.save(attentionRecord);
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.AttentionRecordService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizAttentionRecord get(Long id) {
        return attentionRecordDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.AttentionRecordService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizAttentionRecord> findAll(Page page) {
        org.springframework.data.domain.Page<BizAttentionRecord> springDataPage = attentionRecordDao
            .findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizAttentionRecord> findByMemberId(Page page, long memberId) {
        return PageUtils.getListData(attentionRecordDao.findByMemberIdOrderByGmtCreateDesc(
            PageUtils.createPageable(page), memberId), page);
    }

    @Override
    @Transactional(readOnly = true)
    public BizAttentionRecord findByMemberIdAndAttentedMemberId(long memberId, long attentMemberId) {
        return attentionRecordDao.findByMemberIdAndAttentedMemberId(memberId, attentMemberId);
    }

    /**
     * 获取被关注时间不为空的数据
     * @see org.troy.biz.service.AttentionRecordService#findByAttentDayNumIsNotNull()
     */
    @Override
    @Transactional(readOnly = true)
    public List<BizAttentionRecord> findByAttentDayNumIsNotNull() {
        return attentionRecordDao.findByAttentDayNumIsNotNullOrderByGmtCreateDesc();
    }

    @Override
    @Transactional
    public void saveAttentRecordAndVisitHistory(BizAttentionRecord record, BizVisitHistory history) {

        //更新执行过代扣后的新数据
        attentionRecordDao.save(record);

        //会员的访问记录
        bizVisitHistoryDao.save(history);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizAttentionRecord> findByAttentedMemberId(Page page, long attentMemberId) {
        return PageUtils.getListData(
            attentionRecordDao.findByAttentedMemberIdOrderByGmtCreateDesc(
                PageUtils.createPageable(page), attentMemberId), page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizAttentionRecord> findByAttentedMemberIdAndGmtModifiedBetween(Page page,
                                                                                long attentMemberId,
                                                                                String begin,
                                                                                String end) {
        return PageUtils.getListData(attentionRecordDao
            .findByAttentedMemberIdAndGmtModifiedBetweenOrderByGmtCreateDesc(
                PageUtils.createPageable(page), attentMemberId,
                DateUtil.string2Date(begin, BizConstant.DATE_FORMAT),
                DateUtil.string2Date(end, BizConstant.DATE_FORMAT)), page);
    }

    @Override
    @Transactional
    public void saveWithCost(BizAttentionRecord attentionRecord) throws ServiceException {
        BizFinance finance = bizFinanceDao.findByMemberIdAndType(attentionRecord.getMemberId(),
            FinanceEnum.GOLD.getValue());
        if (null == finance) {
            return;
        }
        finance.setGmtModified(new Date());
        long left = finance.getValue() - attentionRecord.getValue();
        if (left < 0) {
            throw new ServiceException("余额不足，请充值后再进行大师关注！");
        }
        finance.setValue(left);
        attentionRecordDao.save(attentionRecord);
        bizFinanceDao.save(finance);
        BizFinanceRecord record = bizFinanceRecordDao.findByTypeAndFinanceType(
            BizConstant.SYSTEM_LEFT, FinanceEnum.GOLD.getValue());
        long leftValue = 0;
        if (null == record) {
            BizFinanceRecord temp = new BizFinanceRecord();
            temp.setType(BizConstant.SYSTEM_LEFT);
            temp.setFinanceType(FinanceEnum.GOLD.getValue());
            temp.setFinanceValue(attentionRecord.getValue());
            bizFinanceRecordDao.save(temp);
            leftValue = finance.getValue();
        } else {
            leftValue = record.getFinanceValue() - attentionRecord.getValue();
            record.setFinanceValue(leftValue);
            bizFinanceRecordDao.save(record);
        }

        //记录到交易明细里面
        BizFinanceTransDetail detail = new BizFinanceTransDetail();
        detail.setMemberId(attentionRecord.getMemberId());
        detail.setTransUseId(finance.getId());
        detail.setType(FinanceEnum.GOLD.getValue());
        detail.setValue(attentionRecord.getValue());
        detail.setTransUse(TransUserEnum.MEMBER_ATTENT.getValue());
        detail.setTransType(TransTypeEnum.OUT.getValue());
        Date date = new Date();
        detail.setGmtCreate(date);
        detail.setGmtModified(date);
        detail.setSysLeft(leftValue);
        detail.setAccountBalance(finance.getValue());
        bizFinanceTransDetailDao.save(detail);
    }

    @Override
    @Transactional
    public void updateWithCost(BizAttentionRecord attentionRecord) throws ServiceException {
        BizFinance finance = bizFinanceDao.findByMemberIdAndType(attentionRecord.getMemberId(),
            FinanceEnum.GOLD.getValue());
        if (null == finance) {
            return;
        }
        finance.setGmtModified(new Date());
        long totalCost = attentionRecord.getLastestTotalCost();
        long left = finance.getValue() - totalCost;
        if (left < 0) {
            throw new ServiceException("余额不足，请充值后再进行大师关注！");
        }
        finance.setValue(left);
        bizFinanceDao.save(finance);
        attentionRecordDao.save(attentionRecord);

        //记录到交易明细里面
        BizFinanceTransDetail detail = new BizFinanceTransDetail();
        detail.setMemberId(attentionRecord.getMemberId());
        detail.setTransUseId(finance.getId());
        detail.setType(FinanceEnum.GOLD.getValue());
        detail.setValue(totalCost);
        detail.setTransUse(TransUserEnum.MEMBER_ATTENT_PROLONG.getValue());
        detail.setTransType(TransTypeEnum.OUT.getValue());
        Date date = new Date();
        detail.setGmtCreate(date);
        detail.setGmtModified(date);
        detail.setAccountBalance(finance.getValue());
        bizFinanceTransDetailDao.save(detail);
    }

    @Override
    public void execDeduct() {
        //如果不是正常的工作日，就不执行任务
        if (!WorkDayUtil.isWorkDay(new Date())) {
            if (logger.isInfoEnabled()) {
                logger.info("今天不是工作日，不执行代扣逻辑！");
            }
            return;
        }

        //执行资金代扣逻辑
        List<BizAttentionRecord> list = this.findByAttentDayNumIsNotNull();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (BizAttentionRecord record : list) {
            try {
                record.execDeduct();

                //执行代扣逻辑的数据更新
                this.saveAttentRecordAndVisitHistory(record, buidBizVisitHistory(record));

            } catch (Exception e) {
                logger.error("执行代扣任务的时候 record：[" + record + "] 出现异常！", e);
            }

        }
    }

    private BizVisitHistory buidBizVisitHistory(BizAttentionRecord record) {
        BizVisitHistory history = new BizVisitHistory();
        Date date = record.getGmtModified();
        history.setMemberId(record.getMemberId());
        history.setAttentedMemberId(record.getAttentedMemberId());
        history.setGmtVisit(date);
        history.setGmtCreate(date);
        history.setGmtModified(date);
        return history;
    }

    @Override
    public List<BizAttentionRecord> findByMemberId(long memberId) {
        return attentionRecordDao.findByMemberId(memberId);
    }

}