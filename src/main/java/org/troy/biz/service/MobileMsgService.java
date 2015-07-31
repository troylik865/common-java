package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizMobileMsg;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;

/**
 * 短信
 *
 * @author siren.lb
 * @version $Id: MobileMsgService.java,v 0.1 2014年7月29日 上午11:59:12 siren.lb Exp $
 */
public interface MobileMsgService extends BaseJdbcService {

    /**
     * 增加
     * @param bizMobileMsg
     * @throws ServiceException
     */
    void save(BizMobileMsg bizMobileMsg) throws ServiceException;

    /**
     * 删除
     * @param id
     * @throws ServiceException
     */
    void delete(Long id) throws ServiceException;

    /**
     * 修改
     * @param bizMobileMsg
     * @throws ServiceException
     */
    void update(BizMobileMsg bizMobileMsg) throws ServiceException;

    /**
     * 根据主键获取对象
     * @param id
     */
    BizMobileMsg get(Long id);

    /**
     * 分页查询
     * @param page
     */
    List<BizMobileMsg> findAll(Page page);

}
