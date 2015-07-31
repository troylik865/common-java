package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizColumnList;

import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/***
 * <p>Title:ColumnListService </p> 
 *
 * <p>Description:ColumnList 业务接口 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
public interface ColumnListService extends BaseJdbcService {

    /**
     * 增加
     * @param columnList
     * @throws ServiceException
     */
    void save(BizColumnList columnList) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param columnList
     * @throws ServiceException
     */
    void update(BizColumnList columnList) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizColumnList get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizColumnList> findAll(Page page);
    

    List<BizColumnList> findAll();

    /**
     * 分页查询
     * @param page
     */
    List<BizColumnList> findByItemType(String itemType, Page page);

    /**
     * 通过栏目类型和展示位置来进行栏目的查询
     * 
     * @param itemType      栏目类型
     * @param showPosition  展示位置
     * @param page
     * @return
     */
    List<BizColumnList> findByItemTypeAndShowPostion(String itemType, String showPosition, Page page);

}
