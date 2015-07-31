package org.troy.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.biz.dao.BizTransRecordStatisDao;
import org.troy.biz.entity.BizTransRecord;
import org.troy.biz.entity.BizTransRecordStatis;
import org.troy.biz.service.TransRecordStatisService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/**
 * 
 * 交易记录统计的实现类
 *
 *
 * @author siren.lb
 * @version $Id: TransRecordStatisServiceImpl.java,v 0.1 2014年7月23日 下午3:34:13 siren.lb Exp $
 */
@Service
public class TransRecordStatisServiceImpl extends BaseJdbcServiceImpl implements
                                                                     TransRecordStatisService {

    @Autowired
    private BizTransRecordStatisDao bizTransRecordStatisDao;

    @Override
    @Transactional(readOnly = true)
    public BizTransRecordStatis create(BizTransRecord transrecord) throws ServiceException {
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

    @Override
    @Transactional
    public BizTransRecordStatis update(List<BizTransRecord> transrecords, Long memberId)
                                                                                        throws ServiceException {
        if (CollectionUtils.isEmpty(transrecords)) {
            return null;
        }
        BizTransRecord record = transrecords.get(0);
        BizTransRecordStatis recordStatis = bizTransRecordStatisDao.findByMemberIdAndInvestType(
            memberId, record.getInvestType());
        if (recordStatis == null && !CollectionUtils.isEmpty(transrecords)) {
            create(transrecords.get(0));
            transrecords.remove(0);
        }
        return update(transrecords, recordStatis);
    }

    /**
     * 更新统计记录
     * 
     * @param transrecords
     * @param recordStatis
     * @return
     * @throws ServiceException
     */
    public BizTransRecordStatis update(List<BizTransRecord> transrecords,
                                       BizTransRecordStatis recordStatis) throws ServiceException {
        if (CollectionUtils.isEmpty(transrecords)) {
            return recordStatis;
        }
        int length = transrecords.size();
        for (int i = 0; i < length; i++) {
            BizTransRecord record = transrecords.get(i);
            if (null == record) {
                continue;
            }
            recordStatis.setTotalIncome(recordStatis.getTotalIncome() + record.getCurrIncome());
            recordStatis.setTotalOutcome(recordStatis.getTotalOutcome() + record.getCurrOutcome());
            recordStatis.setTotal(recordStatis.getTotal() + recordStatis.getTotal()
                                  + record.getGainsAndLosses());
            if (i == length - 1) {
                recordStatis.setLastestValue(record.getCurrValue());
            }
        }
        recordStatis.setGmtModified(new Date());
        bizTransRecordStatisDao.save(recordStatis);
        return recordStatis;
    }

    /**
     * 重新生成统计记录
     * @see org.troy.biz.service.TransRecordStatisService#rebuild(java.util.List, java.lang.Long)
     */
    @Override
    @Transactional
    public BizTransRecordStatis rebuild(List<BizTransRecord> transrecords, Long memberId)
                                                                                         throws ServiceException {
        BizTransRecordStatis recordStatis = bizTransRecordStatisDao.findByMemberId(memberId);
        if (null == recordStatis) {
            return null;
        }

        //重置统计表记录中的数据
        recordStatis.setTotalIncome(0L);
        recordStatis.setTotalOutcome(0L);
        recordStatis.setTotal(0L);
        recordStatis.setLastestValue(0L);
        return update(transrecords, recordStatis);
    }

    @Override
    public void save(BizTransRecordStatis bizTransRecordStatis) throws ServiceException {
        bizTransRecordStatisDao.save(bizTransRecordStatis);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        bizTransRecordStatisDao.delete(id);
    }

    @Override
    public void update(BizTransRecordStatis bizTransRecordStatis) throws ServiceException {
        bizTransRecordStatisDao.save(bizTransRecordStatis);
    }

    @Override
    public BizTransRecordStatis get(Long id) {
        return bizTransRecordStatisDao.findOne(id);
    }

    @Override
    public List<BizTransRecordStatis> findAll(Page page) {
        return PageUtils.getListData(
            bizTransRecordStatisDao.findAll(PageUtils.createPageable(page)), page);
    }

    @Override
    public BizTransRecordStatis findByMemberId(Long memberId, String type) {
        return bizTransRecordStatisDao.findByMemberIdAndInvestType(memberId, type);
    }

    @Override
    public List<BizTransRecordStatis> rankMember(Page page, String investType, String isValidated) {
        return PageUtils.getListData(bizTransRecordStatisDao
            .findByInvestTypeAndIsValidatedOrderByTotalDesc(investType, isValidated,
                PageUtils.createPageable(page)), page);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BizTransRecordStatis> rankMemberBySQL(Page page, String investType) {
        Object[] params = new Object[3];
        int perPageSize = page.getNumPerPage();
        int curPage = page.getPlainPageNum();
        curPage = curPage - 1;
        params[0] = investType;
        params[1] = perPageSize * curPage;
        params[2] = page.getNumPerPage();
        String sql = "select * from biz_trans_record_statis where invest_type = ? order by is_validated desc,total desc limit ?,?";
        try {
            int count = super.queryForInt(
                "select count(id) from biz_trans_record_statis where invest_type = ? ",
                new Object[] { investType });
            page.setTotalCount(count);
            page.setTotalPage(count / perPageSize + 1);
            return (List<BizTransRecordStatis>) super.queryForList(sql, params,
                BizTransRecordStatis.class);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private List<BizTransRecordStatis> getTotalStatisList(String investType) {
        Object[] params = new Object[1];
        params[0] = investType;
        String sql = "select * from biz_trans_record_statis where invest_type = ? order by is_validated desc,total/origion_value desc";
        try {
            return (List<BizTransRecordStatis>) super.queryForList(sql, params,
                BizTransRecordStatis.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean executeRebuildRank(String investType, BizTransRecordStatis statis) {
        List<BizTransRecordStatis> list = getTotalStatisList(investType);
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        int size = list.size();
        List<BizTransRecordStatis> statises = new ArrayList<BizTransRecordStatis>();
        Date date = DateUtil.getNowDate();
        for (int i = 0; i < size; i++) {
            BizTransRecordStatis sta = list.get(i);
            if (sta.getId() == statis.getId()) {
                sta = statis;
            }
            sta.setRank(i + 1);
            sta.setGmtModified(date);
            statises.add(sta);
        }
        bizTransRecordStatisDao.save(statises);
        return false;
    }

    @Override
    public int getMemberRankByMemberIdAndInvest(long memberId, String invest) {
        try {
            BizTransRecordStatis statis = bizTransRecordStatisDao.findByMemberIdAndInvestType(
                memberId, invest);
            return statis.getRank();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
