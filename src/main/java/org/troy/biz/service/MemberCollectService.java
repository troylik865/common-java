package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizMemberCollect;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/**
 * 
 * 大师收藏
 *
 *
 * @author siren.lb
 * @version $Id: MemberCollectService.java,v 0.1 2014年9月15日 上午12:02:22 siren.lb Exp $
 */
public interface MemberCollectService extends BaseJdbcService {

    /**
     * 增加
     * @param bizMemberCollect
     * @throws ServiceException
     */
    void save(BizMemberCollect bizMemberCollect) throws ServiceException;

    void save(List<BizMemberCollect> bizMemberCollect) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param BizMemberCollect
     * @throws ServiceException
     */
    void update(BizMemberCollect bizMemberCollect) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizMemberCollect get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizMemberCollect> findAll(Page page);

    List<BizMemberCollect> findByMemberId(Page page, long memberId);

    BizMemberCollect findByMemberIdAndCollectedMemberId(long memberId, long collectedMemberId);

}
