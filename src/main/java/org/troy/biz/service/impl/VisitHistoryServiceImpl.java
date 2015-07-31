package org.troy.biz.service.impl;

import java.util.List;


import org.troy.biz.dao.BizVisitHistoryDao;
import org.troy.biz.entity.BizVisitHistory ;
import org.troy.biz.service.VisitHistoryService ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;


/***
 * <p>Title:VisitHistoryServiceImpl </p> 
 *
 * <p>Description:VisitHistory 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
@Service
public class VisitHistoryServiceImpl extends BaseJdbcServiceImpl implements VisitHistoryService{

	@Autowired
	private BizVisitHistoryDao visitHistoryDao;
	
	/**   
	 * @param visitHistory  
	 * @see org.troy.biz.service.VisitHistoryService#save(org.troy.biz.entity.BizVisitHistory )  
	 */
	@Transactional
	public void save(BizVisitHistory visitHistory) throws ServiceException{
		visitHistoryDao.save(visitHistory);
	}
	
	/**   
	 * @param id  
	 * @see org.troy.biz.service.VisitHistoryService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException{
		visitHistoryDao.delete(id);
	}
	
	/**   
	 * @param visitHistory  
	 * @see org.troy.biz.service.VisitHistoryService#update(org.troy.biz.entity.BizVisitHistory )  
	 */
	@Transactional
	public void update(BizVisitHistory visitHistory)  throws ServiceException{
		visitHistoryDao.save(visitHistory);
	}

	/**   
	 * @param id
	 * @return  
	 * @see org.troy.biz.service.VisitHistoryService#get(java.lang.Long)  
	 */
	@Transactional(readOnly=true)
	public BizVisitHistory get(Long id) {
		return visitHistoryDao.findOne(id);
	}
	/**   
	 * @return  
	 * @see org.troy.biz.service.VisitHistoryService#findAll(com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<BizVisitHistory> findAll(Page page) {
		org.springframework.data.domain.Page<BizVisitHistory> springDataPage = visitHistoryDao.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	

}