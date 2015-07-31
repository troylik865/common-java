package org.troy.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.system.dao.RoleModuleDao;
import org.troy.system.entity.main.SysRoleModule;
import org.troy.system.service.RoleModuleService;

/**
 * 系统角色模块接口实现类
 * @author wangj
 * 2013-5-17
 */
@Service
public class RoleModuleServiceImpl extends BaseJdbcServiceImpl implements RoleModuleService {

	@Autowired
	private RoleModuleDao roleModuleDao;
	
	@Transactional
	public void save(SysRoleModule roleModule) {
		roleModuleDao.save(roleModule);
	}

	@Transactional
	public void delete(Long id) {
		roleModuleDao.delete(id);
	}

	@Transactional
	public void update(SysRoleModule roleModule) {
		roleModuleDao.save(roleModule);
	}

	@Transactional(readOnly=true)
	public SysRoleModule get(Long id) {
		return roleModuleDao.findOne(id);
	}

	/**   
	 * @param roleId
	 * @return  
	 * @see org.troy.system.service.service.RoleModuleService#find(Long)  
	 */
	@Transactional(readOnly=true)
	public List<SysRoleModule> find(Long roleId) {
		return roleModuleDao.findByRoleId(roleId);
	}
	
	/**   
	 * @param moduleId
	 * @return  
	 * @see org.troy.system.service.service.RoleModuleService#find(Long,Long)  
	 */
	@Transactional(readOnly=true)
	public SysRoleModule findByRoleIdAndModuleId(Long roleId,Long moduleId) {
		return roleModuleDao.findByRoleIdAndModuleId(roleId,moduleId);
	}

}
