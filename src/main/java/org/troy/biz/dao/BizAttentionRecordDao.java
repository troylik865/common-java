package org.troy.biz.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizAttentionRecord;

/***
 * <p>Title:BizAttentionRecordDao </p> 
 *
 * <p>Description:BizAttentionRecord 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
public interface BizAttentionRecordDao extends JpaRepository<BizAttentionRecord, Long> {

    List<BizAttentionRecord> findByAttentedMemberId(long memberId);

    List<BizAttentionRecord> findByMemberId(long memberId);

    List<BizAttentionRecord> findByAttentDayNumIsNotNullOrderByGmtCreateDesc();

    public Page<BizAttentionRecord> findByMemberIdOrderByGmtCreateDesc(Pageable page, long memberId);

    public Page<BizAttentionRecord> findByAttentedMemberIdOrderByGmtCreateDesc(Pageable page,
                                                                               long attentedMemberId);

    public Page<BizAttentionRecord> findByAttentedMemberIdAndGmtModifiedBetweenOrderByGmtCreateDesc(Pageable page,
                                                                                                    Long attentedMemberId,
                                                                                                    Date begin,
                                                                                                    Date end);

    public BizAttentionRecord findByMemberIdAndAttentedMemberId(long memberId, long attentMemberId);

}
