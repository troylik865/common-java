package org.troy.system.service;

import java.util.List;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.system.entity.main.SysUserRole;

/** 
 * 系统用户角色业务接口
 * @author wangj
 * 2013-5-17
 */

public interface UserRoleService extends BaseJdbcService{
	
	void save(SysUserRole userRole);
	
	void delete(Long id) throws ServiceException;
	
	void update(SysUserRole userRole);
	
	SysUserRole get(Long id);
	/**
	 * 根据userId，找到已分配的角色。
	 * 描述
	 * @param userId
	 * @return
	 */
	List<SysUserRole> find(Long userId);
}
