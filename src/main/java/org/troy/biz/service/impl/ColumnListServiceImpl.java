package org.troy.biz.service.impl;

import java.util.List;

import org.troy.biz.cache.TroyCacheManager;
import org.troy.biz.dao.BizColumnListDao;
import org.troy.biz.entity.BizColumnList;
import org.troy.biz.service.ColumnListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>Title:ColumnListServiceImpl </p> 
 *
 * <p>Description:ColumnList 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
@Service
public class ColumnListServiceImpl extends BaseJdbcServiceImpl implements ColumnListService {

    @Autowired
    private BizColumnListDao                      columnListDao;

    @Autowired
    private TroyCacheManager<List<BizColumnList>> columnListCacheManager;

    /**   
     * @param columnList  
     * @see org.troy.biz.service.ColumnListService#save(org.troy.biz.entity.BizColumnList )  
     */
    @Transactional
    public void save(BizColumnList columnList) throws ServiceException {
        columnListCacheManager.reflushPermAll();
        columnListDao.save(columnList);
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.ColumnListService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        columnListCacheManager.reflushPermAll();
        columnListDao.delete(id);
    }

    /**   
     * @param columnList  
     * @see org.troy.biz.service.ColumnListService#update(org.troy.biz.entity.BizColumnList )  
     */
    @Transactional
    public void update(BizColumnList columnList) throws ServiceException {
        columnListCacheManager.reflushPermAll();
        columnListDao.save(columnList);
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.ColumnListService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizColumnList get(Long id) {
        return columnListDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.ColumnListService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizColumnList> findAll(Page page) {
        return PageUtils.getListData(
            columnListDao.findByGmtCreateIsNotNullOrderByItemTypeAsc(PageUtils.createPageable(page)),
            page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizColumnList> findByItemType(String itemType, Page page) {
        return PageUtils.getListData(
            columnListDao.findByItemTypeOrderByItemTypeAsc(itemType, PageUtils.createPageable(page)),
            page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizColumnList> findByItemTypeAndShowPostion(String itemType, String showPosition,
                                                            Page page) {
        return PageUtils.getListData(
            columnListDao.findByItemTypeAndShowPosition(itemType, showPosition,
                PageUtils.createPageable(page)), page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizColumnList> findAll() {
        return columnListDao.findByItemTypeIsNotNullOrderByItemTypeAsc();
    }

}