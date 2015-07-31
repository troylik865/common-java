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
import org.troy.system.dao.ModuleDao;
import org.troy.system.dao.RoleModuleDao;
import org.troy.system.entity.main.SysModule;
import org.troy.system.entity.main.SysRoleModule;
import org.troy.system.service.ModuleService;
import org.troy.system.util.SysConst;

/**
 * 系统功能模块接口实现类
 * @author wangj
 * 2013-5-17
 */
@Service
public class ModuleServiceImpl extends BaseJdbcServiceImpl implements ModuleService {
	@Autowired
	private ModuleDao moduleDao;
	@Autowired
	private RoleModuleDao roleModuleDao;
	
	/** (non-Javadoc)
	 * @see org.troy.system.service.service.ModuleService#save(com.cnnct.system.entity.main.SysModule)
	 */
	@Transactional
	public void save(SysModule module) throws ServiceException  {
		List<SysModule> list = moduleDao.findByActionsAndMethods(module.getActions(), module.getMethods());
		if (list!=null && list.size() > 0){
			throw new ServiceException("已存在Actions:Methods=" + module.getActions() +":" + module.getMethods() + "的模块。");
		}
		moduleDao.save(module);		
	}

	/**   
	 * @param id
	 * @throws ServiceException  
	 * @see org.troy.system.service.service.ModuleService#delete(int)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException {
		if (isRoot(id)) {
			throw new ServiceException("不允许删除根模块。");
		}
		
		SysModule module = this.get(id);
		
		//先判断是否存在子模块，如果存在子模块，则不允许删除
		if(module.getChildren().size() > 0){
			throw new ServiceException(module.getName() + "模块下存在子模块，不允许删除。");
		}
		
		List<SysRoleModule> roleModules = roleModuleDao.findByModuleId(id);
		if(!roleModules.isEmpty()){
			throw new ServiceException(module.getName() + "模块已赋权给相关人员，不允许删除。");
		}
		
		moduleDao.delete(module);
	}


	/** (non-Javadoc)
	 * @see org.troy.system.service.service.ModuleService#update(com.cnnct.system.entity.main.SysModule)
	 */
	@Transactional
	public void update(SysModule module) throws ServiceException {
		List<SysModule> list = moduleDao.findByActionsAndMethods(module.getActions(), module.getMethods());
		if (list!=null && list.size() > 0 && !list.get(0).getId().equals(module.getId())) {
			throw new ServiceException("已存在Actions:Methods=" + module.getActions() +":" + module.getMethods() + "的模块。");
		}
		moduleDao.save(module);	
	}

	/** (non-Javadoc)
	 * @see org.troy.system.service.service.ModuleService#get(java.lang.Long)
	 */
	@Transactional(readOnly=true)
	public SysModule get(Long id) {
		return moduleDao.findOne(id);
	}

	/**   
	 * @param parentId
	 * @param page
	 * @return  
	 * @see org.troy.system.service.service.ModuleService#find(java.lang.Long, com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<SysModule> find(Long parentId, Page page) {
		org.springframework.data.domain.Page<SysModule> p = 
				moduleDao.findByParentId(parentId, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, p);
		return p.getContent();
	}

	/**   
	 * @param parentId
	 * @param name
	 * @param page
	 * @return  
	 * @see org.troy.system.service.service.ModuleService#find(java.lang.Long, java.lang.String, com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<SysModule> find(Long parentId, String name, Page page) {
		org.springframework.data.domain.Page<SysModule> p = 
				moduleDao.findByParentIdAndNameContaining(parentId, name, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, p);
		return p.getContent();
	}

	/**   
	 * @param parentId
	 * @param name
	 * @param page
	 * @return  
	 * @see org.troy.system.service.service.ModuleService#findByParentId(java.lang.Long)  
	 */
	@Transactional(readOnly=true)
	public List<SysModule> findAllByParentId(Long parentId) {
		List<SysModule> list = moduleDao.findByParentId(parentId);
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				SysModule mod = list.get(i);
				List<SysModule> childlist = moduleDao.findByParentId(mod.getId());
				list.addAll(childlist);
			}
		}
		return list;
	}
	
	/**   
	 * @param parentId
	 * @param name
	 * @param page
	 * @return  
	 * @see org.troy.system.service.service.ModuleService#findByParentId(java.lang.Long)  
	 */
	@Transactional(readOnly=true)
	public List<SysModule> findByParentId(Long parentId) {
		List<SysModule> list = moduleDao.findByParentId(parentId);
		return list;
	}
	
	/**
	 * 判断是否是根模块.
	 */
	private boolean isRoot(Long id) {
		return id == SysConst.ROOTID;
	}

	/**
	 * 
	 * @return  
	 * @see org.troy.system.service.service.ModuleService#getTree()
	 */
	@Transactional(readOnly=true)
	public SysModule getTree() {
		List<SysModule> list = moduleDao.findAllWithCache();
		
		List<SysModule> rootList = makeTree(list);
				
		return rootList.get(0);
	}

	/**
	 * 构造树集合
	 * @param list
	 * @return
	 */
	private List<SysModule> makeTree(List<SysModule> list) {
		List<SysModule> parent = new ArrayList<SysModule>();
		// get parentId = null;
		for (SysModule e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<SysModule>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	/**
	 * 构造树集合
	 * @param list
	 * @return
	 */
	private List<SysModule> makeTree(List<SysModule> list,Long parentId) {
		List<SysModule> parent = new ArrayList<SysModule>();
		// get parentId 获取父级节点
		for (SysModule e : list) {
			if (e.getId().equals(parentId)) {
				e.setChildren(new ArrayList<SysModule>(0));
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
	private void makeChildren(List<SysModule> parent, List<SysModule> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<SysModule> tmp = new ArrayList<SysModule>();
		for (SysModule c1 : parent) {
			for (SysModule c2 : children) {
				c2.setChildren(new ArrayList<SysModule>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}
	
	/**
	 * 更加父级id构造树集合
	 * @param list
	 * @return
	 */
	public SysModule getTree(Long parentId) {
		SysModule rootModule = get(parentId);
	    List<SysModule> list = findAllByParentId(parentId);
		list.add(rootModule);
		
		List<SysModule> rootList = makeTree(list,parentId);
				
		return rootList.get(0);
	}
	
	public static void main(String[] args) {
	}

	
}
