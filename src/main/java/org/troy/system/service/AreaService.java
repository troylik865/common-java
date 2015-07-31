package org.troy.system.service;

import java.util.List;
import java.util.Map;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysArea;

/***
 * <p>Title:SysAreaService </p> 
 *
 * <p>Description:行政区划 业务接口 </p> 
 *
 * <p>Author:wangj </p> 
 *
 * <p>Copyright: Copyright (c) 2013 </p>
 * 
 * <p>CreateDate: 2013-06-21 09:59 </p>
 *
 */
public interface AreaService extends BaseJdbcService{

	/**
	 * 增加
	 * @param sysArea
	 * @throws ServiceException
	 */
	void save(SysArea sysArea) throws ServiceException;

	/**
	 * 删除
	 * @param id
	 * @throws ServiceException
	 */
	void delete(Long id) throws ServiceException;
	
	/**
	 * 修改
	 * @param sysArea
	 * @throws ServiceException
	 */
	void update(SysArea sysArea)  throws ServiceException;

	/**
	 * 根据主键获取对象
	 * @param id
	 */
	SysArea get(Long id);
	
	/**
	 * 分页查询
	 * @param page
	 */
	List<SysArea> findAll(Page page);
	
	
	/**
	 * 根据父级id查询
	 * @param parentId
	 * @param page
	 * @return
	 */
	List<SysArea> find(Long parentId, Page page);
	
	/**
	 * 根据父级id和区域名称查询
	 * @param parentId
	 * @param areaName
	 * @param page
	 * @return
	 */
	List<SysArea> find(Long parentId, String areaName, Page page);
	
	/**
	 * 根据父级编码，街道编码查询
	 * @param parentAreaCode
	 * @param page
	 * @return
	 */
	List<SysArea> find(String parentAreaCode, String streetCode);
	
	/**
	 * 根据区域级别查询
	 * @param areaLevel
	 * @return
	 */
	List<SysArea> findByAreaLevel(String areaLevel);
	
	/**
	 * 根据区域级别，编码，登录用户机构级别查询
	 * @param areaLevel
	 * @return
	 */
	List<Map<String, Object>> findByAreaLevelAndAreaCode(String areaLevel,String areaCode,boolean isGlobal);
	
	/**
	 * 获取组织区域树
	 * @return
	 */
	SysArea getTree();
	
	/***
	 * 根据街道编号查找；
	 * @param code
	 * @return
	 */
	SysArea findByCode(String code);
}
