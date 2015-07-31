package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizLinkMessage ;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:LinkMessageService </p> 
 *
 * <p>Description:LinkMessage 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
public interface LinkMessageService extends BaseJdbcService{

	/**
	 * 增加
	 * @param linkMessage
	 * @throws ServiceException
	 */
	void save(BizLinkMessage linkMessage) throws ServiceException;

	/**
	 * 删除
	 * @param id
	 * @throws ServiceException
	 */
	void delete(Long id) throws ServiceException;
	
	/**
	 * 修改
	 * @param linkMessage
	 * @throws ServiceException
	 */
	void update(BizLinkMessage linkMessage)  throws ServiceException;

	/**
	 * 根据主键获取对象
	 * @param id
	 */
	BizLinkMessage get(Long id);
	
	/**
	 * 分页查询
	 * @param page
	 */
	List<BizLinkMessage> findAll(Page page);
	
}
