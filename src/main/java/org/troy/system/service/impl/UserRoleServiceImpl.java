package org.troy.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.system.dao.UserRoleDao;
import org.troy.system.entity.main.SysUserRole;
import org.troy.system.service.UserRoleService;

/**
 * 系统用户角色业务接口实现类
 * @author wangj
 * 2013-5-17
 */
@Service
public class UserRoleServiceImpl extends BaseJdbcServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;

	@Transactional
	public void save(SysUserRole userRole) {
		userRoleDao.save(userRole);
	}

	@Transactional
	public void delete(Long id) throws ServiceException {
		userRoleDao.delete(id);
	}

	@Transactional
	public void update(SysUserRole userRole) {
		userRoleDao.save(userRole);
	}

	@Transactional(readOnly=true)
	public SysUserRole get(Long id) {
		return userRoleDao.findOne(id);
	}

	/**   
	 * @param userId
	 * @return  
	 * @see org.troy.system.service.service.UserRoleService#find(Long)  
	 */
	@Transactional(readOnly=true)
	public List<SysUserRole> find(Long userId) {
		return userRoleDao.findByUserId(userId);
	}
	
	/**   
	 * @param roleId
	 * @return  
	 * @see org.troy.system.service.service.UserRoleService#find(Long)  
	 */
	@Transactional(readOnly=true)
	public List<SysUserRole> findByRoleId(Long roleId) {
		return userRoleDao.findByRoleId(roleId);
	}

}
