package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizMemberSearch;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:MemberSearchService </p> 
 *
 * <p>Description:MemberSearch 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface MemberSearchService extends BaseJdbcService {

    /**
     * 增加
     * @param memberSearch
     * @throws ServiceException
     */
    void save(BizMemberSearch memberSearch) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param memberSearch
     * @throws ServiceException
     */
    void update(BizMemberSearch memberSearch) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizMemberSearch get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizMemberSearch> findAll(Page page);

    /**
     * 分页查询
     * @param page
     */
    List<BizMemberSearch> findAllByGmtCreateDesc(Page page);

}
