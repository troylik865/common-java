package org.troy.system.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.dao.CodeDao;
import org.troy.system.entity.main.SysCode;
import org.troy.system.service.CodeService;

/**
 * 系统数据字典接口实现类
 * @author wangj
 * 2013-5-17
 */
@Service
public class CodeServiceImpl extends BaseJdbcServiceImpl implements CodeService {
	
	@Autowired
	private CodeDao codeDao;
	
	/**   
	 * @param code  
	 * @see org.troy.system.service.service.CodeService#save(org.troy.system.entity.main.SysCode)  
	 */
	@Transactional
	public void save(SysCode code) throws ServiceException{
		if(codeDao.findByCodeTypeAndCodeValue(code.getCodeType(), code.getCodeValue())!=null){
			throw new ServiceException("已经存在此代码类型的代码值！");
		}
		codeDao.save(code);
	}
	
	/**   
	 * @param id  
	 * @see org.troy.system.service.service.CodeService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException{
		codeDao.delete(id);
	}
	
	/**   
	 * @param code  
	 * @see org.troy.system.service.service.CodeService#update(com.cnnct.system.entity.main.SysCode)  
	 */
	@Transactional
	public void update(SysCode code)  throws ServiceException{
		SysCode oldCode =codeDao.findByCodeTypeAndCodeValue(code.getCodeType(), code.getCodeValue());
		if(oldCode!=null && oldCode.getId()!=code.getId()){
			throw new ServiceException("已经存在此代码类型的代码值！");
		}
		codeDao.save(code);
	}

	/**   
	 * @param id
	 * @return  
	 * @see org.troy.system.service.service.CodeService#get(java.lang.Long)  
	 */
	@Transactional(readOnly=true)
	public SysCode get(Long id) {
		return codeDao.findOne(id);
	}
	/**   
	 * @return  
	 * @see org.troy.system.service.service.CodeService#findAll(com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<SysCode> findAll(Page page) {
		org.springframework.data.domain.Page<SysCode> springDataPage = codeDao.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**   
	 * @param page
	 * @param codetype
	 * @return  
	 * @see org.troy.system.service.service.CodeService#find(com.cnnct.common.utils.dwz.Page, java.lang.String)  
	 */
	@Transactional(readOnly=true)
	public List<SysCode> find(Page page, String codeType) {
		org.springframework.data.domain.Page<SysCode> codes = 
				(org.springframework.data.domain.Page<SysCode>)codeDao.findByCodeTypeContaining(codeType, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, codes);
		return codes.getContent();
	}

	/* (non-Javadoc)
	 * @see com.cnnct.system.service.CodeService#findCodeListBySql(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<String> findCodeListBySql(String tableName,
			String where, String orderBy,String valueField,String displayField) throws ServiceException{
		    List<String> datalist = new ArrayList<String>();
		        StringBuffer sqlbf=new StringBuffer();
		        sqlbf.append("select t.* from "+tableName+" t");
	        	if(where !=null && !"".equals(where.trim())) sqlbf.append(" where "+where);
	        	if(orderBy !=null && !"".equals(orderBy.trim())) sqlbf.append(" order by "+orderBy);
	        	try {
	        		List<Map<String, Object>> list=this.executeQueryForMapList(sqlbf.toString());
	        		if(list!=null && list.size()>0){
	        			for(int i=0;i<list.size();i++){
	        				Map<String,Object> map = list.get(i);
	        				datalist.add("\""+map.get(valueField.toLowerCase())+"\",\""+ map.get(displayField.toLowerCase())+"\"");
	        			}
	        		}
				} catch (SQLException e) {
					throw new ServiceException("获取数字字典查询失败！");
				}
		return datalist;
	}

	/* (non-Javadoc)
	 * @see com.cnnct.system.service.CodeService#findCodeListWithCache(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SysCode> findCodeListWithCache(String codeType,String notIn) throws ServiceException{
		Collection<String> notIns = new ArrayList<String>();
		if(StringUtils.isNotBlank(notIn)){
			if (notIn.contains(",")) {
				String[] arr = notIn.split(",");
			    notIns = Arrays.asList(arr);
			}else{
				notIns.add(notIn);
			}
		}else{
			notIns.add("ALL");//所有
		}
		return codeDao.findByCodeTypeAndCodeValueNotIn(codeType,notIns);
	}

	/**   
	 * @return  
	 * @see org.troy.system.service.service.CodeService#findAll()  
	 */
	@Transactional(readOnly=true)
	public List<SysCode> findAllWithCache() {
		return codeDao.findAllWithCache();
	}
}
