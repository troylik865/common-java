package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizAttentionRecord;
import org.troy.biz.entity.BizVisitHistory;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:AttentionRecordService </p> 
 *
 * <p>Description:AttentionRecord 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
public interface AttentionRecordService extends BaseJdbcService {

    /**
     * 增加
     * @param attentionRecord
     * @throws ServiceException
     */
    void save(BizAttentionRecord attentionRecord) throws ServiceException;

    void saveWithCost(BizAttentionRecord attentionRecord) throws ServiceException;

    void updateWithCost(BizAttentionRecord attentionRecord) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param attentionRecord
     * @throws ServiceException
     */
    void update(BizAttentionRecord attentionRecord) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizAttentionRecord get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizAttentionRecord> findAll(Page page);

    /**
     * 通过当前登录人的会员ID来获取正在关注的大师列表
     * 
     * @param page
     * @param memberId
     * @return
     */
    List<BizAttentionRecord> findByMemberId(Page page, long memberId);

    /**
     * 通过会员ID和被关注的会员Id 来获取对应的关注记录
     * 
     * @param memberId          会员Id
     * @param attentMemberId    被关注的大师对应的会员Id
     * @return
     */
    BizAttentionRecord findByMemberIdAndAttentedMemberId(long memberId, long attentMemberId);

    /**
     * 通过被关注的大师Id来获取所有的关注记录
     * 
     * @param page
     * @param attentMemberId
     * @return
     */
    List<BizAttentionRecord> findByAttentedMemberId(Page page, long attentMemberId);

    List<BizAttentionRecord> findByMemberId(long memberId);

    List<BizAttentionRecord> findByAttentedMemberIdAndGmtModifiedBetween(Page page,
                                                                         long attentMemberId,
                                                                         String begin, String end);

    /**
     * 获取到关注天数不为空的所有的关注记录
     * 
     * @return
     */
    List<BizAttentionRecord> findByAttentDayNumIsNotNull();

    /**
     * 更新被关注记录和相对应的访问记录
     * 
     * @param record    关注记录
     * @param history   访问历史
     */
    void saveAttentRecordAndVisitHistory(BizAttentionRecord record, BizVisitHistory history);

    void execDeduct();

}
