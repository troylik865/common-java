package org.troy.biz.service.impl;

import java.util.List;

import org.troy.biz.dao.BizMemberSearchDao;
import org.troy.biz.entity.BizMemberSearch;
import org.troy.biz.service.MemberSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>Title:MemberSearchServiceImpl </p> 
 *
 * <p>Description:MemberSearch 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Service
public class MemberSearchServiceImpl extends BaseJdbcServiceImpl implements MemberSearchService {

    @Autowired
    private BizMemberSearchDao memberSearchDao;

    /**   
     * @param memberSearch  
     * @see org.troy.biz.service.MemberSearchService#save(org.troy.biz.entity.BizMemberSearch )  
     */
    @Transactional
    public void save(BizMemberSearch memberSearch) throws ServiceException {
        memberSearchDao.save(memberSearch);
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.MemberSearchService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        memberSearchDao.delete(id);
    }

    /**   
     * @param memberSearch  
     * @see org.troy.biz.service.MemberSearchService#update(org.troy.biz.entity.BizMemberSearch )  
     */
    @Transactional
    public void update(BizMemberSearch memberSearch) throws ServiceException {
        memberSearchDao.save(memberSearch);
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.MemberSearchService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizMemberSearch get(Long id) {
        return memberSearchDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.MemberSearchService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizMemberSearch> findAll(Page page) {
        org.springframework.data.domain.Page<BizMemberSearch> springDataPage = memberSearchDao
            .findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Transactional(readOnly = true)
    public List<BizMemberSearch> findAllByGmtCreateDesc(Page page) {
        return PageUtils.getListData(memberSearchDao
            .findByGmtCreateIsNotNullOrderByGmtCreateDesc(PageUtils.createPageable(page)), page);
    }

}