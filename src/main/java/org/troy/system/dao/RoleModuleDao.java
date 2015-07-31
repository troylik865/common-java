package org.troy.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.system.entity.main.SysRoleModule;

/**
 * 系统角色模块dao
 * @author wangj
 * 2013-5-17
 */
public interface RoleModuleDao extends JpaRepository<SysRoleModule, Long> {
	/**
	 * 根据角色id查询模块权限集合
	 * @param roleId
	 * @return
	 */
	List<SysRoleModule> findByRoleId(Long roleId);
	/**
	 * 根据模块id查询角色集合
	 * @param moduleId
	 * @return
	 */
	List<SysRoleModule> findByModuleId(Long moduleId);
	
	/**
	 *  根据角色id和模块id查询
	 * @param roleId
	 * @param moduleId
	 * @return
	 */
	SysRoleModule findByRoleIdAndModuleId(Long roleId,Long moduleId);
	
}
