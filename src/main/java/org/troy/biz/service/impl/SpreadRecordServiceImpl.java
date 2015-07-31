package org.troy.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.troy.biz.dao.BizSpreadRecordDao;
import org.troy.biz.entity.BizSpreadRecord;
import org.troy.biz.service.SpreadRecordService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

@Service
public class SpreadRecordServiceImpl extends BaseJdbcServiceImpl implements SpreadRecordService {

    @Autowired
    private BizSpreadRecordDao bizSpreadRecordDao;

    @Override
    public List<BizSpreadRecord> findAll(Page page) {
        return PageUtils.getListData(bizSpreadRecordDao
            .findByGmtCreateIsNotNullOrderByGmtCreateDesc(PageUtils.createPageable(page)), page);
    }

    @Override
    public List<BizSpreadRecord> findBySpreadMemberId(Page page, long spreadMemberId) {
        return PageUtils.getListData(bizSpreadRecordDao
            .findByGmtCreateIsNotNullOrderByGmtCreateDesc(PageUtils.createPageable(page)), page);
    }

    @Override
    public List<BizSpreadRecord> findBySpreadMemberIds(Page page, List<Long> ids) {
        return PageUtils.getListData(
            bizSpreadRecordDao.findBySpreadMemberIdInOrderByGmtCreateDesc(
                PageUtils.createPageable(page), ids), page);
    }

}
