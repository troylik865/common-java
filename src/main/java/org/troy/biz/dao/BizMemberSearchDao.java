package org.troy.biz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizMemberSearch;

/***
 * <p>Title:BizMemberSearchDao </p> 
 *
 * <p>Description:BizMemberSearch 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface BizMemberSearchDao extends JpaRepository<BizMemberSearch, Long> {

    Page<BizMemberSearch> findByGmtCreateIsNotNullOrderByGmtCreateDesc(Pageable pageable);

}
