package org.troy.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.troy.biz.dao.BizSysConfigDao;
import org.troy.biz.entity.BizSysConfig;
import org.troy.biz.service.BizSysConfigService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;

@Service
public class BizSysConfigServiceImpl extends BaseJdbcServiceImpl implements BizSysConfigService {

    @Autowired
    private BizSysConfigDao bizSysConfigDao;

    @Override
    public void update(BizSysConfig bizSysConfig) throws ServiceException {
        bizSysConfigDao.save(bizSysConfig);
        
    }

    @Override
    public List<BizSysConfig> findByType(String type) throws ServiceException {
        return bizSysConfigDao.findByType(type);
    }

}
