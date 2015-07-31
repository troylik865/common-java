package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizFinanceTransDetail;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:FinanceTransDetailService </p> 
 *
 * <p>Description:FinanceTransDetail 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface FinanceTransDetailService extends BaseJdbcService {

    /**
     * 增加
     * @param financeTransDetail
     * @throws ServiceException
     */
    void save(BizFinanceTransDetail financeTransDetail) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param financeTransDetail
     * @throws ServiceException
     */
    void update(BizFinanceTransDetail financeTransDetail) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizFinanceTransDetail get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizFinanceTransDetail> findAll(Page page);

    /**
     * 分页查询
     * 
     * @param page
     * @param memberId
     * @return
     */
    List<BizFinanceTransDetail> findByMemberId(Page page, Long memberId);
    

    List<BizFinanceTransDetail> findByGmtCreate(Page page, String date);

}
