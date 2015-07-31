package org.troy.system.service;

import java.util.List;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.system.entity.main.SysRoleModule;

/**
 * 系统角色模块业务接口
 * @author wangj
 * 2013-5-17
 */
public interface RoleModuleService extends BaseJdbcService{
	
    void save(SysRoleModule roleModule);
	
	void delete(Long id);
	
	void update(SysRoleModule roleModule);
	
	SysRoleModule get(Long id);
	
	/**
	 * 根据roleId，找到已分配的权限。
	 * 描述
	 * @param userId
	 * @return
	 */
	List<SysRoleModule> find(Long roleId);
	
	/**
	 * 根据roleId,moduleId，找到已分配的权限。
	 * @param roleId
	 * @param moduleId
	 * @return
	 */
	SysRoleModule findByRoleIdAndModuleId(Long roleId,Long moduleId);
}
