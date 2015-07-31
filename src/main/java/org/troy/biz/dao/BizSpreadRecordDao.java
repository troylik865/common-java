package org.troy.biz.dao;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.troy.biz.entity.BizSpreadRecord;

/**
 * 推广信息的处理Dao
 * 
 * @author troy
 * @version $Id: BizSpreadRecordDao.java, v 0.1 2014年7月17日 上午11:49:32 troy Exp $
 */
public interface BizSpreadRecordDao extends JpaRepository<BizSpreadRecord, Long>,
                                   JpaSpecificationExecutor<BizSpreadRecord> {

    Page<BizSpreadRecord> findByGmtCreateIsNotNullOrderByGmtCreateDesc(Pageable pageable);

    Page<BizSpreadRecord> findBySpreadMemberIdOrderByGmtCreateDesc(Pageable pageable,
                                                                   long spreadMemberId);

    Page<BizSpreadRecord> findBySpreadMemberIdInOrderByGmtCreateDesc(Pageable pageable,
                                                                     Collection<Long> ids);

}
