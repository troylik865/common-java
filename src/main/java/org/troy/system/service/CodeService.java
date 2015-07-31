package org.troy.system.service;

import java.util.List;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysCode;

/**
 * 系统数据字典业务接口
 * @author wangj
 * 2013-5-17
 */

public interface CodeService extends BaseJdbcService{
	
    void save(SysCode code) throws ServiceException;
	
	void delete(Long id) throws ServiceException;
	
	void update(SysCode code)  throws ServiceException;
	
	SysCode get(Long id);
	
	List<SysCode> findAll(Page page);
	
	/**
	 * 分页查询数据字典
	 * @param page
	 * @param name
	 * @return
	 */
	List<SysCode> find(Page page, String codeType);
	
	/**
	 * 根据查询条件查询数据字典集合
	 * @param tableName 查询表面
	 * @param where   查询条件
	 * @param orderBy   排序
	 * @param valuefield  选项值字段
	 * @param displayfield 显示列字段
	 * @return
	 */
	List<String> findCodeListBySql(String tableName,String where,String orderBy,
			String valuefield,String displayfield) throws ServiceException;
	
	/**
	 * 提供标签查询数据字典
	 * @param codeType
	 * @param notIn
	 * @return
	 */
	List<SysCode> findCodeListWithCache(String codeType,String notIn) throws ServiceException;
	
	/**
	 * 获取所有数字字典记录(缓存查询)
	 * @return
	 */
	List<SysCode> findAllWithCache();
}
