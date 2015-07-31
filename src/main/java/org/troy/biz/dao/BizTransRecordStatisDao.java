package org.troy.biz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizTransRecordStatis;

/**
 * 交易记录统计
 * 
 * @author troy
 * @version $Id: BizTransRecordStatisDao.java, v 0.1 2014年7月22日 下午11:36:40 troy Exp $
 */
public interface BizTransRecordStatisDao extends JpaRepository<BizTransRecordStatis, Long> {

    /**
     * 根据会员Id来获取交易的统计信息
     * 
     * @param memberId  会员Id
     * @return
     */
    public BizTransRecordStatis findByMemberId(Long memberId);

    public Page<BizTransRecordStatis> findAll(Pageable page);

    public BizTransRecordStatis findByMemberIdAndInvestType(Long memberId, String type);
    
    /**
     * 分页获取所有通过验证和累计盈亏排序过的数据
     * 
     * @param investType 投资方向
     * @param isValidated 是否通过参赛验证
     * @param page
     * @return
     */
    public Page<BizTransRecordStatis> findByInvestTypeAndIsValidatedOrderByTotalDesc(String investType,
                                                                                     String isValidated,
                                                                                     Pageable page);

}
