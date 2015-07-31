package org.troy.system.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.StringUtil;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.dao.AreaDao;
import org.troy.system.entity.main.SysArea;
import org.troy.system.service.AreaService;
import org.troy.system.util.SysConst;


/***
 * <p>Title:areaServiceImpl </p> 
 *
 * <p>Description:行政区划 业务接口实现 </p> 
 *
 * <p>Author:wangj </p> 
 *
 * <p>Copyright: Copyright (c) 2013 </p>
 * 
 * <p>CreateDate: 2013-06-21 09:59 </p>
 *
 */
@Service
public class AreaServiceImpl extends BaseJdbcServiceImpl implements AreaService{

	@Autowired
	private AreaDao areaDao;
	
	/**   
	 * @param area  
	 * @see com.cnnct.system.service.areaService#save(com.cnnct.system.entity.area)  
	 */
	@Transactional
	public void save(SysArea area) throws ServiceException{
		if(areaDao.findByAreaCode(area.getAreaCode())!=null){
			throw new ServiceException("已经存在此区域编码的行政区划！");
		}
		areaDao.save(area);
	}
	
	/**   
	 * @param id  
	 * @see com.cnnct.system.service.areaService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException{
		//判断是否根节点
		if (isRoot(id)) {
			throw new ServiceException("不允许删除根节点。");
		}
		
		SysArea sysArea = this.get(id);
		
		//先判断是否存在子模块，如果存在子模块，则不允许删除
		if(sysArea.getChildren().size() > 0){
			throw new ServiceException(sysArea.getAreaName() + "区域下存在下级区域，不允许删除。");
		}
		areaDao.delete(id);
	}
	
	/**   
	 * @param area  
	 * @see com.cnnct.system.service.areaService#update(com.cnnct.system.entity.area)  
	 */
	@Transactional
	public void update(SysArea area)  throws ServiceException{
		SysArea oldArea =areaDao.findByAreaCode(area.getAreaCode());
		if(oldArea!=null && !oldArea.getId().equals(area.getId())){
			throw new ServiceException("已经存在此区域编码的行政区划！");
		}
		areaDao.save(area);
	}

	/**   
	 * @param id
	 * @return  
	 * @see com.cnnct.system.service.areaService#get(java.lang.Long)  
	 */
	@Transactional(readOnly=true)
	public SysArea get(Long id) {
		return areaDao.findOne(id);
	}
	
	/**
	 * 判断是否是根节点.
	 */
	private boolean isRoot(Long id) {
		return id == SysConst.ROOTID;
	}
	
	/**   
	 * @return  
	 * @see com.cnnct.system.service.areaService#findAll(com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<SysArea> findAll(Page page) {
		org.springframework.data.domain.Page<SysArea> springDataPage = areaDao.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	

	/**   
	 * @param parentId
	 * @param page
	 * @return  
	 * @see org.troy.system.service.impl.service.AreaService#find(java.lang.Long, com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<SysArea> find(Long parentId, Page page) {
		org.springframework.data.domain.Page<SysArea> p = 
				areaDao.findByParentId(parentId, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, p);
		return p.getContent();
	}

	/**   
	 * @param parentId
	 * @param name
	 * @param page
	 * @return  
	 * @see org.troy.system.service.impl.service.AreaService#find(java.lang.Long, java.lang.String, com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<SysArea> find(Long parentId, String areaName, Page page) {
		org.springframework.data.domain.Page<SysArea> p = 
				areaDao.findByParentIdAndAreaNameContaining(parentId, areaName, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, p);
		return p.getContent();
	}
	
	/**   
	 * @param parentAreaCode
	 * @return  list
	 * @see org.troy.system.service.impl.service.AreaService#find(java.lang.String)  
	 */
	@Transactional(readOnly=true)
	public List<SysArea> find(String parentAreaCode, String streetCode){
		List<SysArea> list = new ArrayList<SysArea>();
		SysArea parent = areaDao.findByAreaCode(parentAreaCode);
		if(parent!=null && StringUtil.isEmpty(streetCode)){
			list = areaDao.findByParentId(parent.getId());
		}else{
			list.add(areaDao.findByAreaCode(streetCode));
		}
		return list;
	}
	
	/**   
	 * @param areaLevel
	 * @return  list
	 * @see org.troy.system.service.impl.service.AreaService#findByAreaLevel(java.lang.String)  
	 */
	@Transactional(readOnly=true)
	public List<SysArea> findByAreaLevel(String areaLevel){
		return areaDao.findByAreaLevel(areaLevel);
	}
	
	/**
	 * 
	 * @return  
	 * @see org.troy.system.service.impl.service.AreaService#getTree()
	 */
	@Transactional(readOnly=true)
	public SysArea getTree() {
		List<SysArea> list = areaDao.findAllWithCache();
		
		List<SysArea> rootList = makeTree(list);
				
		return rootList.get(0);
	}

	/**
	 * 构造组织区域数集合
	 * @param list
	 * @return
	 */
	private List<SysArea> makeTree(List<SysArea> list) {
		List<SysArea> parent = new ArrayList<SysArea>();
		// get parentId = null;
		for (SysArea e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<SysArea>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	/**
	 * 构造子节点
	 * @param parent
	 * @param children
	 */
	private void makeChildren(List<SysArea> parent, List<SysArea> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<SysArea> tmp = new ArrayList<SysArea>();
		for (SysArea c1 : parent) {
			for (SysArea c2 : children) {
				c2.setChildren(new ArrayList<SysArea>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}

	/* (non-Javadoc)
	 * @see com.cnnct.system.service.AreaService#findByAreaLevelAndAreaCode(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findByAreaLevelAndAreaCode(String areaLevel,
			String areaCode,boolean isGlobal) {
		List<Map<String, Object>> list = null;
		String sql = "select area_code,area_name from sys_area where 1=1 ";
		if(StringUtil.isNotEmpty(areaLevel)){
			sql += "and area_level ='"+areaLevel+"' ";
		}
		if(isGlobal == false){
			if(StringUtil.isNotEmpty(areaCode)){
				sql += "and area_code ='"+areaCode+"' ";
			}
		}
		sql += "order by area_code";
		try {
			list=this.executeQueryForMapList(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public SysArea findByCode(String code) {
		SysArea area = areaDao.findByAreaCode(code);
		return area;
	}
	
}