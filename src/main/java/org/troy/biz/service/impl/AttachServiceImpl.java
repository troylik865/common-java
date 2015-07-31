package org.troy.biz.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.biz.dao.BizAttachDao;
import org.troy.biz.dao.BizMemberDao;
import org.troy.biz.entity.BizAttach;
import org.troy.biz.entity.BizMember;
import org.troy.biz.service.AttachService;
import org.troy.biz.util.MultipartFileUtil;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>Title:AttachServiceImpl </p> 
 *
 * <p>Description:Attach 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
@Service
public class AttachServiceImpl extends BaseJdbcServiceImpl implements AttachService {

    @Autowired
    private BizAttachDao attachDao;

    @Autowired
    private BizMemberDao bizMemberDao;

    /**   
     * @param attach  
     * @see org.troy.biz.service.AttachService#save(org.troy.biz.entity.BizAttach )  
     */
    @Transactional
    public void save(BizAttach attach) throws ServiceException {
        attachDao.save(attach);
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.AttachService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        attachDao.delete(id);
    }

    /**   
     * @param attach  
     * @see org.troy.biz.service.AttachService#update(org.troy.biz.entity.BizAttach )  
     */
    @Transactional
    public void update(BizAttach attach) throws ServiceException {
        attachDao.save(attach);
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.AttachService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizAttach get(Long id) {
        return attachDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.PublishMessageDetailService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizAttach> findAll(Page page) {
        org.springframework.data.domain.Page<BizAttach> springDataPage = attachDao
            .findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    /**   
     * @return  
     * @see org.troy.biz.service.AttachService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizAttach> findByMemberNoAndAttachTypeOrderByGmtCreateDesc(Page page,
                                                                           String memberNo,
                                                                           String attachType) {
        BizMember member = bizMemberDao.findBizMemberByMemberNo(memberNo);
        if (null == member) {
            return null;
        }
        return PageUtils.getListData(attachDao.findByAttachTypeAndRefIdOrderByGmtCreateDesc(
            PageUtils.createPageable(page), attachType, member.getId()), page);
    }

    /** 
     * @see org.troy.biz.service.AttachService#getAttachFile(java.lang.String)
     */
    @Override
    public File getAttachFile(String attachPath) {
        return MultipartFileUtil.getAttachFileByPath(attachPath);
    }

    @Transactional
    @Override
    public void save(List<BizAttach> attachs) throws ServiceException {
        attachDao.save(attachs);
    }

    @Override
    public List<BizAttach> findAttachByAttachTypeAndRefId(String attachType, Long refId) {
        return attachDao.findByAttachTypeAndRefIdOrderByGmtCreateDesc(attachType, refId);
    }

    @Override
    public List<BizAttach> findByTransRecordIdsAndAttachTypeOrderByGmtCreateDesc(Page page,
                                                                                 List<Long> transRecordIds,
                                                                                 String attachType) {
        return PageUtils.getListData(attachDao.findByAttachTypeAndRefIdInOrderByGmtCreateDesc(
            PageUtils.createPageable(page), attachType, transRecordIds), page);
    }

    @Override
    public List<BizAttach> findByRefIdAndAttachTypeOrderByGmtCreateDesc(Long refId,
                                                                        String attachType) {
        return attachDao.findByAttachTypeAndRefIdOrderByGmtCreateDesc(attachType, refId);
    }

}