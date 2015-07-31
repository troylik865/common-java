package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizFinanceRecord;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/**
 * 
 * @author siren.lb
 * @version $Id: FinanceRecordService.java,v 0.1 2014年10月30日 下午12:11:51 siren.lb Exp $
 */
public interface FinanceRecordService extends BaseJdbcService {

    /**
     * 增加
     * @param finance
     * @throws ServiceException
     */
    void save(BizFinanceRecord finance) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param finance
     * @throws ServiceException
     */
    void update(BizFinanceRecord finance) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizFinanceRecord get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizFinanceRecord> findAll(Page page);

    BizFinanceRecord findByTypeAndFinanceType(String type, String financeType);

}
