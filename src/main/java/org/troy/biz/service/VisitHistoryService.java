package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizVisitHistory ;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:VisitHistoryService </p> 
 *
 * <p>Description:VisitHistory 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
public interface VisitHistoryService extends BaseJdbcService{

	/**
	 * 增加
	 * @param visitHistory
	 * @throws ServiceException
	 */
	void save(BizVisitHistory visitHistory) throws ServiceException;

	/**
	 * 删除
	 * @param id
	 * @throws ServiceException
	 */
	void delete(Long id) throws ServiceException;
	
	/**
	 * 修改
	 * @param visitHistory
	 * @throws ServiceException
	 */
	void update(BizVisitHistory visitHistory)  throws ServiceException;

	/**
	 * 根据主键获取对象
	 * @param id
	 */
	BizVisitHistory get(Long id);
	
	/**
	 * 分页查询
	 * @param page
	 */
	List<BizVisitHistory> findAll(Page page);
	
}
