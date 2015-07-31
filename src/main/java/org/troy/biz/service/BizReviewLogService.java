package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizReviewLog;
import org.troy.biz.entity.vo.BizReviewRequest;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:BizReviewLogService </p> 
 *
 * <p>Description:BizReviewLog 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
public interface BizReviewLogService extends BaseJdbcService {

    /**
     * 增加
     * @param bizReviewLog
     * @throws ServiceException
     */
    void save(BizReviewLog bizReviewLog) throws ServiceException;
    

    /**
     * 分页查询
     * @param page
     */
    List<BizReviewLog> findAll(Page page);
    
    /**
     * 请求创建审批记录
     * @param request
     */
    void create(BizReviewRequest request);
    
    /**
     * 获取审批记录
     * @param bizId
     * @param bizType
     * @return
     */
    BizReviewLog getBizReviewLog(Long bizId,String bizType);

}
