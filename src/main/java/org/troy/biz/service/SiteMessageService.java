package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizSiteMessage;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/**
 * 站内信息相关的Service
 * 
 * @author troy
 * @version $Id: SiteMessageService.java, v 0.1 2014年7月19日 下午6:39:15 troy Exp $
 */
public interface SiteMessageService extends BaseJdbcService {

    /**
     * 增加
     * @param bizSiteMessage
     * @throws ServiceException
     */
    void save(BizSiteMessage bizSiteMessage) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param bizSiteMessage
     * @throws ServiceException
     */
    void update(BizSiteMessage bizSiteMessage) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizSiteMessage get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizSiteMessage> findAll(Page page);
    
    List<BizSiteMessage> findByMessageType(Page page,String messageType);
    
    /**
     * 根据接受信息的会员Id来获取相应的站内信
     * 
     * @param page
     * @param memberId
     * @return
     */
    List<BizSiteMessage> findByReceiveMemberId(Page page,Long memberId);
    

    List<BizSiteMessage> findByReceiveMemberIdAndType(Page page,Long memberId,String type);

}
