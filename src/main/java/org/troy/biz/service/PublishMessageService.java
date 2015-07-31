package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizPublishMessage;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:PublishMessageService </p> 
 *
 * <p>Description:PublishMessage 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
public interface PublishMessageService extends BaseJdbcService {

    /**
     * 增加
     * @param publishMessage
     * @throws ServiceException
     */
    void save(BizPublishMessage publishMessage) throws ServiceException;

    void saveWithDetail(BizPublishMessage publishMessage) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;
    

    void deleteAll(List<Long> id) throws ServiceException;

    /**
     * 修改
     * @param publishMessage
     * @throws ServiceException
     */
    void update(BizPublishMessage publishMessage) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizPublishMessage get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizPublishMessage> findAll(Page page);

    List<BizPublishMessage> findByMemberId(Page page, long memberId);

    List<BizPublishMessage> findByMemberIdAndStatus(Page page, long memberId, String status);

    List<BizPublishMessage> findByStatus(Page page, String status);

}
