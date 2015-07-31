package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizMemberRank;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:MemberRankService </p> 
 *
 * <p>Description:MemberRank 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface MemberRankService extends BaseJdbcService {

    /**
     * 增加
     * @param memberRank
     * @throws ServiceException
     */
    void save(BizMemberRank memberRank) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    void deleteAll(List<Long> ids);

    /**
     * 修改
     * @param memberRank
     * @throws ServiceException
     */
    void update(BizMemberRank memberRank) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizMemberRank get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizMemberRank> findAll(Page page);

    /**
     * 分页查询
     * @param page
     */
    List<BizMemberRank> listTop(Page page);

    List<BizMemberRank> listTopByInvestDirection(Page page, String investDirection);

}
