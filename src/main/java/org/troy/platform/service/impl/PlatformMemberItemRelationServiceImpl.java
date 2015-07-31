package org.troy.platform.service.impl;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.platform.dao.PlatformMemberItemRelationDao;
import org.troy.platform.entity.PlatformMemberItemRelation;
import org.troy.platform.service.PlatformMemberItemRelationService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;

@Service
public class PlatformMemberItemRelationServiceImpl extends BaseJdbcServiceImpl implements
                                                                              PlatformMemberItemRelationService {

    @Autowired
    private PlatformMemberItemRelationDao platformMemberItemRelationDao;

    @Override
    public PlatformMemberItemRelation update(PlatformMemberItemRelation platformMemberItemRelation)
                                                                                                   throws ServiceException {
        return platformMemberItemRelationDao.save(platformMemberItemRelation);
    }

    @Override
    public PlatformMemberItemRelation save(PlatformMemberItemRelation platformMemberItemRelation)
                                                                                                 throws ServiceException {
        return platformMemberItemRelationDao.save(platformMemberItemRelation);
    }

    @Override
    public PlatformMemberItemRelation findById(Long id) throws ServiceException {
        return platformMemberItemRelationDao.findOne(id);
    }

    @Override
    public List<PlatformMemberItemRelation> findAll() throws ServiceException {
        return platformMemberItemRelationDao.findAll();
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Long id : ids) {
            platformMemberItemRelationDao.delete(id);
        }
    }

}