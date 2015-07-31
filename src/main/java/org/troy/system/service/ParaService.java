package org.troy.system.service;

import java.util.List;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysPara;

/**
 * 系统参数业务接口
 * @author wangj
 * 2013-5-17
 */

public interface ParaService extends BaseJdbcService{
	
    void save(SysPara para) throws ServiceException;
	
	void delete(Long id) throws ServiceException;
	
	void update(SysPara para)  throws ServiceException;
	
	SysPara get(Long id);
	
	List<SysPara> findAll(Page page);
	
	/**
	 * 分页查询系统参数
	 * @param page
	 * @param name
	 * @return
	 */
	List<SysPara> find(Page page, String paraName);
	
}
