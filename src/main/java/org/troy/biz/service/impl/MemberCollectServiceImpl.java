package org.troy.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.troy.biz.dao.BizMemberCollectDao;
import org.troy.biz.entity.BizMemberCollect;
import org.troy.biz.service.MemberCollectService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

@Service
public class MemberCollectServiceImpl extends BaseJdbcServiceImpl implements MemberCollectService {

    @Autowired
    private BizMemberCollectDao bizMemberCollectDao;

    @Override
    public void save(BizMemberCollect bizMemberCollect) throws ServiceException {
        bizMemberCollectDao.save(bizMemberCollect);
    }

    @Override
    public void save(List<BizMemberCollect> bizMemberCollect) throws ServiceException {
        bizMemberCollectDao.save(bizMemberCollect);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        bizMemberCollectDao.delete(id);
    }

    @Override
    public void update(BizMemberCollect bizMemberCollect) throws ServiceException {
        bizMemberCollectDao.save(bizMemberCollect);

    }

    @Override
    public BizMemberCollect get(Long id) {
        return bizMemberCollectDao.findOne(id);
    }

    @Override
    public List<BizMemberCollect> findAll(Page page) {
        return PageUtils.getListData(bizMemberCollectDao.findAll(PageUtils.createPageable(page)),
            page);
    }

    @Override
    public List<BizMemberCollect> findByMemberId(Page page, long memberId) {
        return PageUtils.getListData(bizMemberCollectDao.findByMemberIdOrderByGmtCreateDesc(
            PageUtils.createPageable(page), memberId), page);
    }

    @Override
    public BizMemberCollect findByMemberIdAndCollectedMemberId(long memberId, long collectedMemberId) {
        return bizMemberCollectDao.findByMemberIdAndCollectedMemberId(memberId, collectedMemberId);
    }
}
