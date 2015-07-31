package org.troy.system.service;

import java.util.List;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysGroup;

/**
 * 系统组织机构业务接口
 * @author wangj
 * 2013-5-17
 */

public interface GroupService extends BaseJdbcService{
	
	void save(SysGroup group);
		
	void delete(Long id) throws ServiceException;
	
	void update(SysGroup group);
	
	SysGroup get(Long id);
	
	/**
	 * 根据父级id查询
	 * @param parentId
	 * @param page
	 * @return
	 */
	List<SysGroup> find(Long parentId, Page page);
	
	/**
	 * 根据父级id和机构名称查询
	 * @param parentId
	 * @param name
	 * @param page
	 * @return
	 */
	List<SysGroup> find(Long parentId, String name, Page page);
	
	/**
	 * 获取组织机构树
	 * @return
	 */
	SysGroup getTree();
}
