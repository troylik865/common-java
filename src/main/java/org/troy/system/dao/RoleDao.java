package org.troy.system.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.system.entity.main.SysRole;

/**
 * 系统角色dao
 * @author wangj
 * 2013-5-17
 */

public interface RoleDao extends JpaRepository<SysRole, Long> {
	
	List<SysRole> findByName(String name);
	/**
	 * 根据包含角色名(类似like)查找角色
	 * @param Username
	 * @param pageable
	 * @return
	 */
	Page<SysRole> findByNameContaining(String name, Pageable pageable);
}
