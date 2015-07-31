package org.troy.biz.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import org.troy.biz.entity.BizLoginHistory ;

/***
 * <p>Title:BizLoginHistoryDao </p> 
 *
 * <p>Description:BizLoginHistory 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface BizLoginHistoryDao extends JpaRepository<BizLoginHistory, Long> {
	
}
