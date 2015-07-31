package org.troy.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.troy.biz.dao.BizNewsDao;
import org.troy.biz.entity.BizNews;
import org.troy.biz.service.NewsService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

@Service
public class NewsServiceImpl extends BaseJdbcServiceImpl implements NewsService {

    @Autowired
    private BizNewsDao bizNewsDao;

    @Override
    public void save(BizNews bizNews) throws ServiceException {
        bizNewsDao.save(bizNews);
    }

    @Override
    public void save(List<BizNews> bizNews) throws ServiceException {
        bizNewsDao.save(bizNews);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        bizNewsDao.delete(id);
    }

    @Override
    public void update(BizNews bizNews) throws ServiceException {
        bizNewsDao.save(bizNews);
    }

    @Override
    public BizNews get(Long id) {
        return bizNewsDao.findOne(id);
    }

    @Override
    public List<BizNews> findAll(Page page) {
        return PageUtils.getListData(bizNewsDao.findAll(PageUtils.createPageable(page)), page);
    }

    @Override
    public List<BizNews> findByGmtCreateIsNotNullOrderByTurnAsc(Page page) {
        return PageUtils
            .getListData(
                bizNewsDao.findByGmtCreateIsNotNullOrderByTurnAsc(PageUtils.createPageable(page)),
                page);
    }

}
