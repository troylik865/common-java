package org.troy.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.dao.RoleDao;
import org.troy.system.dao.UserRoleDao;
import org.troy.system.entity.main.SysRole;
import org.troy.system.entity.main.SysUserRole;
import org.troy.system.service.RoleService;
import org.troy.system.shiro.ShiroDbRealm;

/**
 * 系统角色接口实现类
 * @author wangj
 * 2013-5-17
 */
@Service
public class RoleServiceImpl extends BaseJdbcServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired(required = false)
	private UserRoleDao userRoleDao;
	
	@Autowired(required = false)
	private ShiroDbRealm shiroRealm;
	
	/**   
	 * @param role  
	 * @see org.troy.system.service.service.RoleService#save(com.cnnct.system.entity.main.SysRole)  
	 */
	@Transactional
	public void save(SysRole role) throws ServiceException {
		List<SysRole> list = this.findByName(role.getName());
		if(list!=null && list.size()>0){
			throw new ServiceException(role.getName() + "角色已存在。");
		}
		roleDao.save(role);
	}
	/**   
	 * @param id  
	 * @see org.troy.system.service.service.RoleService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException{
		List<SysUserRole> userRoles = userRoleDao.findByRoleId(id);
		
		SysRole role = this.get(id);
		if(!userRoles.isEmpty()){
			throw new ServiceException(role.getName() + "角色已赋权给相关人员，不允许删除。");
		}
		
		roleDao.delete(id);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}

	/**   
	 * @param role  
	 * @see org.troy.system.service.service.RoleService#update(com.cnnct.system.entity.main.SysRole)  
	 */
	@Transactional
	public void update(SysRole role) throws ServiceException{
		List<SysRole> list = this.findByName(role.getName());
		if(list!=null && list.size()>0 && !list.get(0).getId().equals(role.getId())){
			throw new ServiceException(role.getName() + "角色已存在。");
		}
		roleDao.save(role);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**   
	 * @param id  
	 * @see org.troy.system.service.service.RoleService#get(java.lang.Long)  
	 */
	@Transactional(readOnly=true)
	public SysRole get(Long id) {
		return roleDao.findOne(id);
	}
	
	/**   
	 * @param id  
	 * @see org.troy.system.service.service.RoleService#findByName(java.lang.String)  
	 */
	@Transactional(readOnly=true)
	public List<SysRole> findByName(String name) {
		return roleDao.findByName(name);
	}
	/**   
	 * @return  
	 * @see org.troy.system.service.service.RoleService#findAll(com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<SysRole> findAll(Page page) {
		org.springframework.data.domain.Page<SysRole> springDataPage = roleDao.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**   
	 * @param page
	 * @param name
	 * @return  
	 * @see org.troy.system.service.service.RoleService#find(com.cnnct.common.utils.dwz.Page, java.lang.String)  
	 */
	@Transactional(readOnly=true)
	public List<SysRole> find(Page page, String name) {
		org.springframework.data.domain.Page<SysRole> roles = 
				(org.springframework.data.domain.Page<SysRole>)roleDao.findByNameContaining(name, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, roles);
		return roles.getContent();
	}

}
