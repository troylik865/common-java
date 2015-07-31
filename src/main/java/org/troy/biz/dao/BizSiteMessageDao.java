package org.troy.biz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizSiteMessage;

/**
 * 站内信相关的操作
 * 
 * @author troy
 * @version $Id: BizSiteMessageDao.java, v 0.1 2014年7月19日 下午6:19:21 troy Exp $
 */
public interface BizSiteMessageDao extends JpaRepository<BizSiteMessage, Long> {

    Page<BizSiteMessage> findByReceiveMemberIdOrderByGmtCreateDesc(Pageable pageable, Long memberId);

    Page<BizSiteMessage> findByGmtCreateIsNotNullOrderByGmtCreateDesc(Pageable pageable);

    Page<BizSiteMessage> findByMessageTypeOrderByGmtCreateDesc(Pageable pageable, String messageType);

    Page<BizSiteMessage> findByReceiveMemberIdAndMessageTypeOrderByGmtCreateDesc(Pageable pageable,
                                                                                 Long memberId,
                                                                                 String messageType);
}
