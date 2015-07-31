package org.troy.system.service;

import java.util.List;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysUser;

/**
 * 系统用户业务接口
 * @author wangj
 * 2013-5-17
 */

public interface UserService extends BaseJdbcService{
	
	void save(SysUser user) throws ServiceException;
	
	void delete(Long id) throws ServiceException;
	
	void update(SysUser user) throws ServiceException;
	
	SysUser get(Long id);
	
	List<SysUser> findAll();
	
	List<SysUser> findAll(Page page);
	/**
	 * 根据用户登录名查询用户
	 * @param username
	 * @return
	 */
	SysUser get(String username);
	
	/**
	 * 分页查询用户
	 * @param page
	 * @param name
	 * @return
	 */
	List<SysUser> find(Page page, String name);
}
