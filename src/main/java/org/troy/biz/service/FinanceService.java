package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizFinance;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:FinanceService </p> 
 *
 * <p>Description:Finance 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface FinanceService extends BaseJdbcService {

    /**
     * 增加
     * @param finance
     * @throws ServiceException
     */
    void save(BizFinance finance) throws ServiceException;

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
    void update(BizFinance finance) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizFinance get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizFinance> findAll(Page page);

    /**
     * 通过memberId和资金类型来获取用户相关的资金
     * 
     * @param memberId  用户Id
     * @param type      资金类型
     * @return
     */
    BizFinance findByMemberIdAndType(long memberId, String type);

}
