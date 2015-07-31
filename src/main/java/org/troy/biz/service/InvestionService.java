package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizInvestion;
import org.troy.biz.entity.BizSiteMessage;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/**
 * 
 * 投资方向相关的service
 *
 *
 * @author siren.lb
 * @version $Id: InvestionService.java,v 0.1 2014年7月28日 下午10:30:59 siren.lb Exp $
 */
public interface InvestionService extends BaseJdbcService {

    public List<BizInvestion> findAll(Page page);

    public List<BizInvestion> findAllByInvestDirectionAndIsValidated(Page page,
                                                                     String investDirection,
                                                                     String isValidated);

    public List<BizInvestion> findByMemberId(Page page, Long memberId);

    public List<BizInvestion> findByMemberIdIn(Page page, List<Long> ids);

    public List<BizInvestion> findByMemberIdInAndIsValidated(Page page, List<Long> ids,
                                                             String isValidated);

    public List<BizInvestion> findByMemberIdInAndInvestDirectionAndIsValidated(Page page,
                                                                               List<Long> ids,
                                                                               String investDirection,
                                                                               String isValidated);

    public List<BizInvestion> findByIdIn(List<Long> ids);

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

    public List<BizInvestion> findByIsValidated(Page page, String isValidated);

    /**
     * 根据会员Id 来获取旗下所有的投资方向信息
     * 
     * @param memberId      会员Id
     * @return                    该会员下面所有涉及的投资方向
     */
    public List<BizInvestion> findByMemberId(Long memberId);

    void update(BizInvestion bizInvestion) throws ServiceException;

    void updateInvestions(List<BizInvestion> bizInvestions) throws ServiceException;

    void updateInvestionsWithStatis(List<BizInvestion> bizInvestions) throws ServiceException;

    void save(BizInvestion bizInvestion) throws ServiceException;

    void saveWithUpdateSiteMessage(BizInvestion bizInvestion, BizSiteMessage message)
                                                                                     throws ServiceException;

    void updateWithUpdateSiteMessage(BizInvestion bizInvestion, BizSiteMessage message)
                                                                                       throws ServiceException;
}
