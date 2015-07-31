package org.troy.biz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizMemberCollect;

/**
 * 
 * 大师收藏相关的Dao
 *
 *
 * @author siren.lb
 * @version $Id: BizMemberCollectDao.java,v 0.1 2014年9月15日 上午12:00:19 siren.lb Exp $
 */
public interface BizMemberCollectDao extends JpaRepository<BizMemberCollect, Long> {

    Page<BizMemberCollect> findByMemberIdOrderByGmtCreateDesc(Pageable page, long memberId);

    BizMemberCollect findByMemberIdAndCollectedMemberId(long memberId, long collectedMemberId);

}
