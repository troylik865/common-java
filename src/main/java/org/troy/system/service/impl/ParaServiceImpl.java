package org.troy.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.dao.ParaDao;
import org.troy.system.entity.main.SysPara;
import org.troy.system.service.ParaService;

/**
 * 系统参数接口实现类
 * @author wangj
 * 2013-5-17
 */
@Service
public class ParaServiceImpl extends BaseJdbcServiceImpl implements ParaService {
	
	@Autowired
	private ParaDao paraDao;
	
	/**   
	 * @param Para  
	 * @see org.troy.system.service.service.ParaService#save(com.cnnct.system.entity.main.SysPara)  
	 */
	@Transactional
	public void save(SysPara para) throws ServiceException{
		if(paraDao.findByParaKeyAndParaValue(para.getParaKey(), para.getParaValue())!=null){
			throw new ServiceException("已经存在此关键字的参数值！");
		}
		paraDao.save(para);
	}
	
	/**   
	 * @param id  
	 * @see org.troy.system.service.service.ParaService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException{
		paraDao.delete(id);
	}
	
	/**   
	 * @param Para  
	 * @see org.troy.system.service.service.ParaService#update(com.cnnct.system.entity.main.SysPara)  
	 */
	@Transactional
	public void update(SysPara para)  throws ServiceException{
		SysPara oldPara =paraDao.findByParaKeyAndParaValue(para.getParaKey(), para.getParaValue());
		if(oldPara!=null && oldPara.getId()!=para.getId()){
			throw new ServiceException("已经存在此关键字的参数值！");
		}
		paraDao.save(para);
	}

	/**   
	 * @param id
	 * @return  
	 * @see org.troy.system.service.service.ParaService#get(java.lang.Long)  
	 */
	@Transactional(readOnly=true)
	public SysPara get(Long id) {
		return paraDao.findOne(id);
	}
	/**   
	 * @return  
	 * @see org.troy.system.service.service.ParaService#findAll(com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<SysPara> findAll(Page page) {
		org.springframework.data.domain.Page<SysPara> springDataPage = paraDao.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**   
	 * @param page
	 * @param paraName
	 * @return  
	 * @see org.troy.system.service.service.ParaService#find(com.cnnct.common.utils.dwz.Page, java.lang.String)  
	 */
	@Transactional(readOnly=true)
	public List<SysPara> find(Page page, String paraName) {
		org.springframework.data.domain.Page<SysPara> Paras = 
				(org.springframework.data.domain.Page<SysPara>)paraDao.findByParaNameContaining(paraName, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, Paras);
		return Paras.getContent();
	}


}
