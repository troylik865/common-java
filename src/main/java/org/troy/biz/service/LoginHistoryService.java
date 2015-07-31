package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizLoginHistory;
import org.troy.biz.entity.BizMember;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:LoginHistoryService </p> 
 *
 * <p>Description:LoginHistory 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface LoginHistoryService extends BaseJdbcService {

    /**
     * 增加
     * @param loginHistory
     * @throws ServiceException
     */
    void save(BizLoginHistory loginHistory) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param loginHistory
     * @throws ServiceException
     */
    void update(BizLoginHistory loginHistory) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizLoginHistory get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizLoginHistory> findAll(Page page);

    /**
     * 保存用户的登录信息
     * 
     * @param member
     * @return
     */
    void storeLoginInfo(BizMember member);

}
