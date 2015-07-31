package org.troy.system.service;

import java.util.List;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysRole;

/**
 * 系统角色业务接口
 * @author wangj
 * 2013-5-17
 */

public interface RoleService extends BaseJdbcService{
	
    void save(SysRole role) throws ServiceException;
	
	void delete(Long id) throws ServiceException;
	
	void update(SysRole role) throws ServiceException;
	
	SysRole get(Long id);
	
	List<SysRole> findByName(String name);
	
	List<SysRole> findAll(Page page);
	/**
	 * 分页查询角色
	 * @param page
	 * @param name
	 * @return
	 */
	List<SysRole> find(Page page, String name);
}
