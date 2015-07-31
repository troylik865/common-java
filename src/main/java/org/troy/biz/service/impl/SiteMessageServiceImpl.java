package org.troy.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.biz.dao.BizSiteMessageDao;
import org.troy.biz.entity.BizSiteMessage;
import org.troy.biz.service.SiteMessageService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/**
 * 站内信相关的实现
 * 
 * @author troy
 * @version $Id: SiteMessageServiceImpl.java, v 0.1 2014年7月19日 下午6:41:23 troy Exp $
 */
@Service
public class SiteMessageServiceImpl extends BaseJdbcServiceImpl implements SiteMessageService {

    @Autowired
    private BizSiteMessageDao bizSiteMessageDao;

    @Override
    @Transactional
    public void save(BizSiteMessage bizSiteMessage) throws ServiceException {
        bizSiteMessageDao.save(bizSiteMessage);
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException {
        bizSiteMessageDao.delete(id);
    }

    @Override
    @Transactional
    public void update(BizSiteMessage bizSiteMessage) throws ServiceException {
        bizSiteMessageDao.save(bizSiteMessage);
    }

    @Override
    @Transactional(readOnly = true)
    public BizSiteMessage get(Long id) {
        return bizSiteMessageDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizSiteMessage> findAll(Page page) {
        return PageUtils.getListData(bizSiteMessageDao
            .findByGmtCreateIsNotNullOrderByGmtCreateDesc(PageUtils.createPageable(page)), page);
    }

    @Override
    public List<BizSiteMessage> findByReceiveMemberId(Page page, Long memberId) {
        return PageUtils.getListData(
            bizSiteMessageDao.findByReceiveMemberIdOrderByGmtCreateDesc(
                PageUtils.createPageable(page), memberId), page);
    }

    @Override
    public List<BizSiteMessage> findByReceiveMemberIdAndType(Page page, Long memberId, String type) {
        return PageUtils.getListData(
            bizSiteMessageDao.findByReceiveMemberIdAndMessageTypeOrderByGmtCreateDesc(
                PageUtils.createPageable(page), memberId, type), page);
    }

    @Override
    public List<BizSiteMessage> findByMessageType(Page page, String messageType) {
        return PageUtils.getListData(bizSiteMessageDao.findByMessageTypeOrderByGmtCreateDesc(
            PageUtils.createPageable(page), messageType), page);
    }

}