package org.troy.biz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizFinance;

/***
 * <p>Title:BizFinanceDao </p> 
 *
 * <p>Description:BizFinance 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface BizFinanceDao extends JpaRepository<BizFinance, Long> {

    public BizFinance findByMemberIdAndType(long memberId, String type);
}
