package org.troy.biz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizLinkMessage;

/***
 * <p>Title:BizLinkMessageDao </p> 
 *
 * <p>Description:BizLinkMessage 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface BizLinkMessageDao extends JpaRepository<BizLinkMessage, Long> {

    Page<BizLinkMessage> findByGmtCreateIsNotNullOrderByGmtCreateDesc(Pageable pageable);

}
