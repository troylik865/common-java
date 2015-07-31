package org.troy.system.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.system.entity.main.SysUser;

/**
 * 系统用户dao
 * @author wangj
 * 2013-5-17
 */
public interface UserDao extends JpaRepository<SysUser, Long> {
	/**
	 * 根据登录名查找用户
	 * @param Username
	 * @return
	 */
	SysUser findByUsername(String Username);
	
	/**
	 *  根据实名查找用户
	 * @param realname
	 * @return
	 */
	SysUser findByRealname(String realname);
	
	/**
	 * 根据包含登录名(类似like)查找用户
	 * @param Username
	 * @param pageable
	 * @return
	 */
	Page<SysUser> findByUsernameContaining(String Username, Pageable pageable);
}