package org.troy.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.dao.GroupDao;
import org.troy.system.entity.main.SysGroup;
import org.troy.system.service.GroupService;
import org.troy.system.util.SysConst;

/**
 * 系统组织机构业务实现类
 * @author wangj
 * 2013-5-17
 */
@Service
public class GroupServiceImpl extends BaseJdbcServiceImpl implements GroupService {
	
	@Autowired
	private GroupDao groupDao;
	
	/**   
	 * @param group  
	 * @see org.troy.system.service.service.GroupService#save(com.cnnct.system.entity.main.SysGroup)  
	 */
	@Transactional
	public void save(SysGroup group) {
		groupDao.save(group);
	}
	
	/**   
	 * @param id
	 * @throws ServiceException  
	 * @see org.troy.system.service.service.GroupService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException {
		if (isRoot(id)) {
			throw new ServiceException("不允许删除根组织。");
		}
		
		SysGroup sysGroup = this.get(id);
		
		//先判断是否存在子模块，如果存在子模块，则不允许删除
		if(sysGroup.getChildren().size() > 0){
			throw new ServiceException(sysGroup.getName() + "组织下存在子组织，不允许删除。");
		}
		
		groupDao.delete(id);
	}

	/**   
	 * @param group  
	 * @see org.troy.system.service.service.GroupService#update(com.cnnct.system.entity.main.SysGroup)  
	 */
	@Transactional
	public void update(SysGroup group) {
		groupDao.save(group);
	}

	/**   
	 * @param id
	 * @return  
	 * @see org.troy.system.service.service.GroupService#get(java.lang.Long)  
	 */
	@Transactional(readOnly=true)
	public SysGroup get(Long id) {
		return groupDao.findOne(id);
	}

	/**   
	 * @param parentId
	 * @param page
	 * @return  
	 * @see org.troy.system.service.service.GroupService#find(java.lang.Long, com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<SysGroup> find(Long parentId, Page page) {
		org.springframework.data.domain.Page<SysGroup> p = 
				groupDao.findByParentId(parentId, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, p);
		return p.getContent();
	}

	/**   
	 * @param parentId
	 * @param name
	 * @param page
	 * @return  
	 * @see org.troy.system.service.service.GroupService#find(java.lang.Long, java.lang.String, com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<SysGroup> find(Long parentId, String name, Page page) {
		org.springframework.data.domain.Page<SysGroup> p = 
				groupDao.findByParentIdAndNameContaining(parentId, name, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, p);
		return p.getContent();
	}
	
	/**
	 * 判断是否是根组织.
	 */
	private boolean isRoot(Long id) {
		return id == SysConst.ROOTID;
	}

	/**
	 * 
	 * @return  
	 * @see org.troy.system.service.service.GroupService#getTree()
	 */
	@Transactional(readOnly=true)
	public SysGroup getTree() {
		List<SysGroup> list = groupDao.findAllWithCache();
		
		List<SysGroup> rootList = makeTree(list);
				
		return rootList.get(0);
	}

	/**
	 * 构造组织机构数集合
	 * @param list
	 * @return
	 */
	private List<SysGroup> makeTree(List<SysGroup> list) {
		List<SysGroup> parent = new ArrayList<SysGroup>();
		// get parentId = null;
		for (SysGroup e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<SysGroup>(0));
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
	private void makeChildren(List<SysGroup> parent, List<SysGroup> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<SysGroup> tmp = new ArrayList<SysGroup>();
		for (SysGroup c1 : parent) {
			for (SysGroup c2 : children) {
				c2.setChildren(new ArrayList<SysGroup>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}
}
