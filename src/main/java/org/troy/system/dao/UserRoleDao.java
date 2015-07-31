package org.troy.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.system.entity.main.SysUserRole;

/**
 * 系统用户角色dao
 * @author wangj
 * 2013-5-17
 */

public interface UserRoleDao extends JpaRepository<SysUserRole, Long> {
	/**
	 * 根据用户id查询关联角色集合
	 * @param userId
	 * @return
	 */
	List<SysUserRole> findByUserId(Long userId);
	
	/**
	 * 根据角色id查询关联用户集合
	 * @param roleId
	 * @return
	 */
	List<SysUserRole> findByRoleId(Long roleId);
}
