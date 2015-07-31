package org.troy.platform.service.impl;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.platform.dao.PlatformMemberDao;
import org.troy.platform.entity.PlatformMember;
import org.troy.platform.service.PlatformMemberService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;

@Service
public class PlatformMemberServiceImpl extends BaseJdbcServiceImpl implements PlatformMemberService {

    @Autowired
    private PlatformMemberDao platformMemberDao;

    @Override
    public PlatformMember update(PlatformMember platformMember) throws ServiceException {
        return platformMemberDao.save(platformMember);
    }

    @Override
    public PlatformMember save(PlatformMember platformMember) throws ServiceException {
        return platformMemberDao.save(platformMember);
    }

    @Override
    public PlatformMember findById(Long id) throws ServiceException {
        return platformMemberDao.findOne(id);
    }

    @Override
    public List<PlatformMember> findAll() throws ServiceException {
        return platformMemberDao.findAll();
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Long id : ids) {
            platformMemberDao.delete(id);
        }
    }


}