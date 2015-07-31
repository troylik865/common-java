package org.troy.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.troy.biz.dao.BizFinanceRecordDao;
import org.troy.biz.entity.BizFinanceRecord;
import org.troy.biz.service.FinanceRecordService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

public class FinanceRecordServiceImpl extends BaseJdbcServiceImpl implements FinanceRecordService {

    @Autowired
    private BizFinanceRecordDao bizFinanceRecordDao;

    @Override
    public void save(BizFinanceRecord finance) throws ServiceException {
        bizFinanceRecordDao.save(finance);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        bizFinanceRecordDao.delete(id);
    }

    @Override
    public void update(BizFinanceRecord finance) throws ServiceException {
        bizFinanceRecordDao.save(finance);
    }

    @Override
    public BizFinanceRecord get(Long id) {
        return bizFinanceRecordDao.findOne(id);
    }

    @Override
    public List<BizFinanceRecord> findAll(Page page) {
        return PageUtils.getListData(bizFinanceRecordDao.findAll(PageUtils.createPageable(page)),
            page);
    }

    @Override
    public BizFinanceRecord findByTypeAndFinanceType(String type, String financeType) {
        return bizFinanceRecordDao.findByTypeAndFinanceType(type, financeType);
    }

}
