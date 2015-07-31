package org.troy.biz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizPublishMessage;

/***
 * <p>Title:BizPublishMessageDao </p> 
 *
 * <p>Description:BizPublishMessage 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
public interface BizPublishMessageDao extends JpaRepository<BizPublishMessage, Long> {

    public Page<BizPublishMessage> findByMemberIdOrderByGmtCreateDesc(long memberId, Pageable page);

    public Page<BizPublishMessage> findByMemberIdAndStatusOrderByGmtCreateDesc(long memberId,
                                                                               String status,
                                                                               Pageable page);
    

    public Page<BizPublishMessage> findByGmtCreateIsNotNullOrderByGmtCreateDesc(Pageable page);
    
    public Page<BizPublishMessage> findByStatusOrderByGmtCreateDesc(String status, Pageable page);

}
