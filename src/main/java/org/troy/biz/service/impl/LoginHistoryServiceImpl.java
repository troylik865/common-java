package org.troy.biz.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.biz.dao.BizLoginHistoryDao;
import org.troy.biz.dao.BizMemberDao;
import org.troy.biz.entity.BizLoginHistory;
import org.troy.biz.entity.BizMember;
import org.troy.biz.service.LoginHistoryService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>Title:LoginHistoryServiceImpl </p> 
 *
 * <p>Description:LoginHistory 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Service
public class LoginHistoryServiceImpl extends BaseJdbcServiceImpl implements LoginHistoryService {

    @Autowired
    private BizLoginHistoryDao loginHistoryDao;

    @Autowired
    private BizMemberDao       memberDao;

    /**   
     * @param loginHistory  
     * @see org.troy.biz.service.LoginHistoryService#save(org.troy.biz.entity.BizLoginHistory )  
     */
    @Transactional
    public void save(BizLoginHistory loginHistory) throws ServiceException {
        loginHistoryDao.save(loginHistory);
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.LoginHistoryService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        loginHistoryDao.delete(id);
    }

    /**   
     * @param loginHistory  
     * @see org.troy.biz.service.LoginHistoryService#update(org.troy.biz.entity.BizLoginHistory )  
     */
    @Transactional
    public void update(BizLoginHistory loginHistory) throws ServiceException {
        loginHistoryDao.save(loginHistory);
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.LoginHistoryService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizLoginHistory get(Long id) {
        return loginHistoryDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.LoginHistoryService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizLoginHistory> findAll(Page page) {
        org.springframework.data.domain.Page<BizLoginHistory> springDataPage = loginHistoryDao
            .findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Transactional
    public void storeLoginInfo(BizMember member) {
        BizLoginHistory history = new BizLoginHistory();
        Date date = new Date();
        history.setGmtCreate(date);
        history.setGmtLogin(date);
        history.setGmtModified(date);
        history.setMemberId(member.getId());
        loginHistoryDao.save(history);
        member.setGmtLastLogin(date);
        memberDao.save(member);
    }

}