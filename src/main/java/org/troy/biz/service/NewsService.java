package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizNews;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/**
 * 新闻相关的service
 *
 * @author siren.lb
 * @version $Id: NewsService.java,v 0.1 2014年9月6日 下午6:25:38 siren.lb Exp $
 */
public interface NewsService extends BaseJdbcService {

    /**
     * 增加
     * @param bizNews
     * @throws ServiceException
     */
    void save(BizNews bizNews) throws ServiceException;

    void save(List<BizNews> bizNews) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param bizNews
     * @throws ServiceException
     */
    void update(BizNews bizNews) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizNews get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizNews> findAll(Page page);

    /**
     * 根据排序来获取通知内容
     * 
     * @param page
     * @return
     */
    List<BizNews> findByGmtCreateIsNotNullOrderByTurnAsc(Page page);

}
