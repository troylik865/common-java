package org.troy.platform.service;
import java.util.List;

import org.troy.platform.entity.PlatformMemberItemRelation;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;

public interface PlatformMemberItemRelationService extends BaseJdbcService {

    PlatformMemberItemRelation findById(Long id)  throws ServiceException;

    List<PlatformMemberItemRelation> findAll() throws ServiceException;

    PlatformMemberItemRelation update(PlatformMemberItemRelation platformMemberItemRelation) throws ServiceException;

    PlatformMemberItemRelation save(PlatformMemberItemRelation platformMemberItemRelation) throws ServiceException;

    void deleteAll(List<Long> ids);
    
}
