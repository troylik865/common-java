package org.troy.biz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizFinanceTransDetail;

/***
 * <p>Title:BizFinanceTransDetailDao </p> 
 *
 * <p>Description:BizFinanceTransDetail 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface BizFinanceTransDetailDao extends JpaRepository<BizFinanceTransDetail, Long> {

    public Page<BizFinanceTransDetail> findByMemberIdOrderByGmtCreateDesc(Pageable page,
                                                                          Long memberId);

}
