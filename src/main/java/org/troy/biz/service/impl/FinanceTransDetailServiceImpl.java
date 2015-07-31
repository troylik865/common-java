package org.troy.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.biz.dao.BizFinanceTransDetailDao;
import org.troy.biz.entity.BizFinanceTransDetail;
import org.troy.biz.service.FinanceTransDetailService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>Title:FinanceTransDetailServiceImpl </p> 
 *
 * <p>Description:FinanceTransDetail 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Service
public class FinanceTransDetailServiceImpl extends BaseJdbcServiceImpl implements
                                                                      FinanceTransDetailService {

    private static final String      SQL = "select * from biz_finance_trans_detail where DATE_FORMAT(gmt_create,'%Y-%m-%d') = ?";

    @Autowired
    private BizFinanceTransDetailDao financeTransDetailDao;

    /**   
     * @param financeTransDetail  
     * @see org.troy.biz.service.FinanceTransDetailService#save(org.troy.biz.entity.BizFinanceTransDetail )  
     */
    @Transactional
    public void save(BizFinanceTransDetail financeTransDetail) throws ServiceException {
        financeTransDetailDao.save(financeTransDetail);
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.FinanceTransDetailService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        financeTransDetailDao.delete(id);
    }

    /**   
     * @param financeTransDetail  
     * @see org.troy.biz.service.FinanceTransDetailService#update(org.troy.biz.entity.BizFinanceTransDetail )  
     */
    @Transactional
    public void update(BizFinanceTransDetail financeTransDetail) throws ServiceException {
        financeTransDetailDao.save(financeTransDetail);
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.FinanceTransDetailService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizFinanceTransDetail get(Long id) {
        return financeTransDetailDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.FinanceTransDetailService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizFinanceTransDetail> findAll(Page page) {
        org.springframework.data.domain.Page<BizFinanceTransDetail> springDataPage = financeTransDetailDao
            .findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<BizFinanceTransDetail> findByMemberId(Page page, Long memberId) {
        return PageUtils.getListData(financeTransDetailDao.findByMemberIdOrderByGmtCreateDesc(
            PageUtils.createPageable(page), memberId), page);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BizFinanceTransDetail> findByGmtCreate(Page page, String date) {
        Object[] params = new Object[1];
        params[0] = date;
        try {
            return (List<BizFinanceTransDetail>) super.queryForList(SQL, params,
                BizFinanceTransDetail.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}