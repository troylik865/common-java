package org.troy.platform.service.impl;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.platform.dao.PlatformItemDao;
import org.troy.platform.entity.PlatformItem;
import org.troy.platform.service.PlatformItemService;

@Service
public class PlatformItemServiceImpl extends BaseJdbcServiceImpl implements PlatformItemService {

    @Autowired
    private PlatformItemDao platformItemDao;

    @Override
    public PlatformItem update(PlatformItem platformItem) throws ServiceException {
        return platformItemDao.save(platformItem);
    }

    @Override
    public PlatformItem save(PlatformItem platformItem) throws ServiceException {
        return platformItemDao.save(platformItem);
    }

    @Override
    public PlatformItem findById(Long id) throws ServiceException {
        return platformItemDao.findOne(id);
    }

    @Override
    public List<PlatformItem> findAll() throws ServiceException {
        return platformItemDao.findAll();
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Long id : ids) {
            platformItemDao.delete(id);
        }
    }

}