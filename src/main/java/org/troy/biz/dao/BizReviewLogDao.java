package org.troy.biz.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizReviewLog;

/***
 * <p>Title:BizReviewLogDao </p> 
 *
 * <p>Description:BizReviewLog 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
public interface BizReviewLogDao extends JpaRepository<BizReviewLog, Long> {
	
	
   /**
    * 查询业务的日志记录
    * @param page
    * @param bizId
    * @param bizType
    * @return
    */
    Page<BizReviewLog> findByBizIdAndBizTypeOrderByGmtCreateDesc(Pageable page,long bizId,String bizType);
    
    /**
     * 查询业务的日志记录
     * @param bizId
     * @param bizType
     * @return
     */
    List<BizReviewLog> findByBizIdAndBizTypeOrderByGmtCreateDesc(long bizId,String bizType);


}
