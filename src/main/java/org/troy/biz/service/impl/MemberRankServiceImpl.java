package org.troy.biz.service.impl;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.biz.dao.BizMemberRankDao;
import org.troy.biz.entity.BizMemberRank;
import org.troy.biz.service.MemberRankService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>Title:MemberRankServiceImpl </p> 
 *
 * <p>Description:MemberRank 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Service
public class MemberRankServiceImpl extends BaseJdbcServiceImpl implements MemberRankService {

    @Autowired
    private BizMemberRankDao memberRankDao;

    /**   
     * @param memberRank  
     * @see org.troy.biz.service.MemberRankService#save(org.troy.biz.entity.BizMemberRank )  
     */
    @Transactional
    public void save(BizMemberRank memberRank) throws ServiceException {
        memberRankDao.save(memberRank);
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.MemberRankService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        memberRankDao.delete(id);
    }

    /**   
     * @param memberRank  
     * @see org.troy.biz.service.MemberRankService#update(org.troy.biz.entity.BizMemberRank )  
     */
    @Transactional
    public void update(BizMemberRank memberRank) throws ServiceException {
        memberRankDao.save(memberRank);
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.MemberRankService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizMemberRank get(Long id) {
        return memberRankDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.MemberRankService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizMemberRank> findAll(Page page) {
        org.springframework.data.domain.Page<BizMemberRank> springDataPage = memberRankDao
            .findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Transactional(readOnly = true)
    public List<BizMemberRank> listTop(Page page) {
        return PageUtils.getListData(
            memberRankDao.findByGmtCreateIsNotNullOrderByRankAsc(PageUtils.createPageable(page)),
            page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizMemberRank> listTopByInvestDirection(Page page, String investDirection) {
        return PageUtils.getListData(memberRankDao.findByInvestDirectionOrderByRankAsc(
            PageUtils.createPageable(page), investDirection), page);
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Long id : ids) {
            memberRankDao.delete(id);
        }
    }

}