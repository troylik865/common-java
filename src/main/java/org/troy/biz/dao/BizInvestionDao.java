package org.troy.biz.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizInvestion;

/**
 * 
 * 投资方向操作的dao
 *
 * @author siren.lb
 * @version $Id: BizInvestionDao.java,v 0.1 2014年7月26日 下午5:12:35 siren.lb Exp $
 */
public interface BizInvestionDao extends JpaRepository<BizInvestion, Long> {

    /**
     * 根据会员Id和其相关的投资方向来获取记录
     * 
     * @param memberId                会员 Id
     * @param investDirection       投资方向
     * @return                             单条投资方向记录
     */
    public BizInvestion findByMemberIdAndInvestDirection(Long memberId, String investDirection);

    /**
     * 根据投资方向和验证的情况，来获取对应的列表
     * 
     * @param investDirection    投资方向
     * @param isValidated          是否验证通过
     * @return
     */
    public List<BizInvestion> findByInvestDirectionAndIsValidated(String investDirection,
                                                                  String isValidated);

    /**
     * 根据会员Id 来获取旗下所有的投资方向信息
     * 
     * @param memberId      会员Id
     * @return                    该会员下面所有涉及的投资方向
     */
    public List<BizInvestion> findByMemberId(Long memberId);

    /**
     * 查找会员所有的投资方向
     * 
     * @param page
     * @return
     */
    public Page<BizInvestion> findByGmtCreateIsNotNullOrderByMemberIdAsc(Pageable page);

    public Page<BizInvestion> findByIsValidated(Pageable page, String isValidated);

    public Page<BizInvestion> findByIsValidatedNotLike(Pageable page, String isValidated);

    public Page<BizInvestion> findByIsValidatedOrIsValidatedIsNull(Pageable page, String isValidated);

    public Page<BizInvestion> findByIsValidatedNot(Pageable page, String isValidated);

    public Page<BizInvestion> findByMemberIdInAndInvestDirectionAndIsValidated(Pageable page,
                                                                               Collection<Long> ids,
                                                                               String investDirection,
                                                                               String isValidated);

    public Page<BizInvestion> findByGmtCreateIsNotNullAndInvestDirectionAndIsValidatedOrderByMemberIdAsc(Pageable page,
                                                                                                         String investDirection,
                                                                                                         String isValidated);

    public Page<BizInvestion> findByMemberIdOrderByGmtCreateDesc(Pageable page, Long memberId);

    public Page<BizInvestion> findByMemberIdInOrderByMemberIdAsc(Pageable page, Collection<Long> ids);

    public Page<BizInvestion> findByMemberIdInAndIsValidatedOrderByMemberIdAsc(Pageable page,
                                                                               Collection<Long> ids,
                                                                               String isValidated);


    public List<BizInvestion> findByIdIn(Collection<Long> ids);

}
