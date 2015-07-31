package org.troy.biz.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.troy.biz.entity.BizMember;

/***
 * <p>Title:BizMemberDao </p> 
 *
 * <p>Description:BizMember 接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface BizMemberDao extends JpaRepository<BizMember, Long>,
                             JpaSpecificationExecutor<BizMember> {

    public BizMember findByUserName(String userName);

    public List<BizMember> findByNameContaining(String userName);

    public BizMember findBizMemberByMemberNo(String memberNo);

    public BizMember findByName(String memberNo);

    public Page<BizMember> findByInvestDirectionContainingAndIsValidatedOrderByGmtValidatedAsc(Pageable page,
                                                                                               String investDirection,
                                                                                               String isValidated);

}
