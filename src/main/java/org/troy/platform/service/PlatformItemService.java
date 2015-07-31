package org.troy.platform.service;
import java.util.List;

import org.troy.platform.entity.PlatformItem;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;

public interface PlatformItemService extends BaseJdbcService {

    PlatformItem findById(Long id)  throws ServiceException;

    List<PlatformItem> findAll() throws ServiceException;

    PlatformItem update(PlatformItem platformItem) throws ServiceException;

    PlatformItem save(PlatformItem platformItem) throws ServiceException;
    
    void deleteAll(List<Long> ids);

}
