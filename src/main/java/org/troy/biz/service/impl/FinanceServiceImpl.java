package org.troy.biz.service.impl;

import java.util.List;

import org.troy.biz.dao.BizFinanceDao;
import org.troy.biz.entity.BizFinance;
import org.troy.biz.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>Title:FinanceServiceImpl </p> 
 *
 * <p>Description:Finance 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Service
public class FinanceServiceImpl extends BaseJdbcServiceImpl implements FinanceService {

    @Autowired
    private BizFinanceDao financeDao;

    /**   
     * @param finance  
     * @see org.troy.biz.service.FinanceService#save(org.troy.biz.entity.BizFinance )  
     */
    @Transactional
    public void save(BizFinance finance) throws ServiceException {
        financeDao.save(finance);
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.FinanceService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        financeDao.delete(id);
    }

    /**   
     * @param finance  
     * @see org.troy.biz.service.FinanceService#update(org.troy.biz.entity.BizFinance )  
     */
    @Transactional
    public void update(BizFinance finance) throws ServiceException {
        financeDao.save(finance);
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.FinanceService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizFinance get(Long id) {
        return financeDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.FinanceService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizFinance> findAll(Page page) {
        org.springframework.data.domain.Page<BizFinance> springDataPage = financeDao
            .findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public BizFinance findByMemberIdAndType(long memberId, String type) {
        return financeDao.findByMemberIdAndType(memberId, type);
    }

}