package org.troy.platform.service;
import java.util.List;

import org.troy.platform.entity.PlatformMember;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;

public interface PlatformMemberService extends BaseJdbcService {

    PlatformMember findById(Long id)  throws ServiceException;

    List<PlatformMember> findAll() throws ServiceException;

    PlatformMember update(PlatformMember platformMember) throws ServiceException;

    PlatformMember save(PlatformMember platformMember) throws ServiceException;

    void deleteAll(List<Long> ids);
}
