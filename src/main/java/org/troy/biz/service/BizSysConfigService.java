package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizSysConfig;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;

public interface BizSysConfigService extends BaseJdbcService {

    /**
     * 修改
     * @param bizSysConfig
     * @throws ServiceException
     */
    void update(BizSysConfig bizSysConfig) throws ServiceException;

    List<BizSysConfig> findByType(String type) throws ServiceException;
}
