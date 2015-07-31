package org.troy.biz.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizPublishMessageDetail;

/***
 * <p>Title:BizPublishMessageDetailDao </p> 
 *
 * <p>Description:BizPublishMessageDetail 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
public interface BizPublishMessageDetailDao extends JpaRepository<BizPublishMessageDetail, Long> {

    List<BizPublishMessageDetail> findByAcceptMemberId(long memberId);

    List<BizPublishMessageDetail> findByMessageId(long messageId);

    Page<BizPublishMessageDetail> findByAcceptMemberIdOrderByGmtCreateDesc(Pageable page,
                                                                           long memberId);

    Page<BizPublishMessageDetail> findByAcceptMemberIdAndPublishMemberIdAndGmtCreateBetweenOrderByGmtCreateDesc(Pageable page,
                                                                                                                Long memberId,
                                                                                                                Long publishMemberId,
                                                                                                                Date beginDate,
                                                                                                                Date endDate);

    Page<BizPublishMessageDetail> findByAcceptMemberIdAndGmtCreateBetweenOrderByGmtCreateDesc(Pageable page,
                                                                                              Long memberId,
                                                                                              Date beginDate,
                                                                                              Date endDate);

    Page<BizPublishMessageDetail> findByAcceptMemberIdAndStatusAndGmtCreateBetweenOrderByGmtCreateDesc(Pageable page,
                                                                                                       Long memberId,
                                                                                                       String status,
                                                                                                       Date beginDate,
                                                                                                       Date endDate);

    Page<BizPublishMessageDetail> findByAcceptMemberIdAndPublishMemberIdAndStatusAndGmtCreateBetweenOrderByGmtCreateDesc(Pageable page,
                                                                                                                         Long memberId,
                                                                                                                         Long publishMemberId,
                                                                                                                         String status,
                                                                                                                         Date beginDate,
                                                                                                                         Date endDate);

}
