package org.troy.biz.service.impl;

import java.util.List;

import org.troy.biz.dao.BizLinkMessageDao;
import org.troy.biz.entity.BizLinkMessage;
import org.troy.biz.service.LinkMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>Title:LinkMessageServiceImpl </p> 
 *
 * <p>Description:LinkMessage 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Service
public class LinkMessageServiceImpl extends BaseJdbcServiceImpl implements LinkMessageService {

    @Autowired
    private BizLinkMessageDao linkMessageDao;

    /**   
     * @param linkMessage  
     * @see org.troy.biz.service.LinkMessageService#save(org.troy.biz.entity.BizLinkMessage )  
     */
    @Transactional
    public void save(BizLinkMessage linkMessage) throws ServiceException {
        linkMessageDao.save(linkMessage);
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.LinkMessageService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        linkMessageDao.delete(id);
    }

    /**   
     * @param linkMessage  
     * @see org.troy.biz.service.LinkMessageService#update(org.troy.biz.entity.BizLinkMessage )  
     */
    @Transactional
    public void update(BizLinkMessage linkMessage) throws ServiceException {
        linkMessageDao.save(linkMessage);
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.LinkMessageService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizLinkMessage get(Long id) {
        return linkMessageDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.LinkMessageService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizLinkMessage> findAll(Page page) {
        org.springframework.data.domain.Page<BizLinkMessage> springDataPage = linkMessageDao
            .findByGmtCreateIsNotNullOrderByGmtCreateDesc(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

}