package org.troy.system.service;

import java.util.List;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysModule;


/**
 * 系统功能模块业务接口
 * @author wangj
 * 2013-5-17
 */

public interface ModuleService extends BaseJdbcService{
	
    void save(SysModule log) throws ServiceException ;
	
	void delete(Long id) throws ServiceException;
	
	void update(SysModule log) throws ServiceException ;
	
	SysModule get(Long id);
	/**
	 * 根据父级id查询
	 * @param parentId
	 * @param page
	 * @return
	 */
	List<SysModule> find(Long parentId, Page page);
	/**
	 * 根据父级id和模块名查询
	 * @param parentId
	 * @param name
	 * @param page
	 * @return
	 */
	List<SysModule> find(Long parentId, String name, Page page);
	
	/**
	 * 根据父级id查询,保护所有下级菜单
	 * @param parentId
	 * @param page
	 * @return
	 */
	List<SysModule> findAllByParentId(Long parentId);
	
	/**
	 * 根据父级id查询
	 * @param parentId
	 * @param page
	 * @return
	 */
	List<SysModule> findByParentId(Long parentId);
	/**
	 * 获取功能模块树
	 * @return
	 */
	SysModule getTree();
	
	/**
	 * 根据父级id获取功能模块树
	 * @return
	 */
	SysModule getTree(Long parentId);
}
