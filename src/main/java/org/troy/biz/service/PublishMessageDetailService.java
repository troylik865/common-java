package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizPublishMessageDetail;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:PublishMessageDetailService </p> 
 *
 * <p>Description:PublishMessageDetail 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
public interface PublishMessageDetailService extends BaseJdbcService {

    /**
     * 增加
     * @param publishMessageDetail
     * @throws ServiceException
     */
    void save(BizPublishMessageDetail publishMessageDetail) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param publishMessageDetail
     * @throws ServiceException
     */
    void update(BizPublishMessageDetail publishMessageDetail) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizPublishMessageDetail get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizPublishMessageDetail> findAll(Page page);

    /**
     * 根据接收者的ID 来获取相关的发布信息
     * 
     * @param page
     * @param acceptMemberId
     * @return
     */
    List<BizPublishMessageDetail> findByAcceptMemberId(Page page, long acceptMemberId);

    List<BizPublishMessageDetail> findByAcceptMemberIdAndPublishMemberIdAndGmtCreateBetween(Page page,
                                                                                            Long acceptMemberId,
                                                                                            Long publishMemberId,
                                                                                            String begin,
                                                                                            String end);

    List<BizPublishMessageDetail> findByAcceptMemberIdAndPublishMemberIdAndStatusAndGmtCreateBetween(Page page,
                                                                                                     Long acceptMemberId,
                                                                                                     Long publishMemberId,
                                                                                                     String status,
                                                                                                     String begin,
                                                                                                     String end);

    /**
     * 根据接收者的ID 来获取相关的发布信息
     * 
     * @param acceptMemberId
     * @return
     */
    List<BizPublishMessageDetail> findByAcceptMemberId(long acceptMemberId);

    /**
     * 通过发布信息ID 获取发布的详情
     * 
     * @param messageId
     * @return
     */
    List<BizPublishMessageDetail> findByMessageId(long messageId);

}
