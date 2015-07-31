package org.troy.biz.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import org.troy.biz.entity.BizVisitHistory ;

/***
 * <p>Title:BizVisitHistoryDao </p> 
 *
 * <p>Description:BizVisitHistory 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
public interface BizVisitHistoryDao extends JpaRepository<BizVisitHistory, Long> {
	
}
