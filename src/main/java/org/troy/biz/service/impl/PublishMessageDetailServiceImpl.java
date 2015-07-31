package org.troy.biz.service.impl;

import java.util.List;

import org.troy.biz.constant.BizConstant;
import org.troy.biz.dao.BizPublishMessageDetailDao;
import org.troy.biz.entity.BizPublishMessageDetail;
import org.troy.biz.service.PublishMessageDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>Title:PublishMessageDetailServiceImpl </p> 
 *
 * <p>Description:PublishMessageDetail 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
@Service
public class PublishMessageDetailServiceImpl extends BaseJdbcServiceImpl implements
                                                                        PublishMessageDetailService {

    @Autowired
    private BizPublishMessageDetailDao publishMessageDetailDao;

    /**   
     * @param publishMessageDetail  
     * @see org.troy.biz.service.PublishMessageDetailService#save(org.troy.biz.entity.BizPublishMessageDetail )  
     */
    @Transactional
    public void save(BizPublishMessageDetail publishMessageDetail) throws ServiceException {
        publishMessageDetailDao.save(publishMessageDetail);
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.PublishMessageDetailService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        publishMessageDetailDao.delete(id);
    }

    /**   
     * @param publishMessageDetail  
     * @see org.troy.biz.service.PublishMessageDetailService#update(org.troy.biz.entity.BizPublishMessageDetail )  
     */
    @Transactional
    public void update(BizPublishMessageDetail publishMessageDetail) throws ServiceException {
        publishMessageDetailDao.save(publishMessageDetail);
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.PublishMessageDetailService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizPublishMessageDetail get(Long id) {
        return publishMessageDetailDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.PublishMessageDetailService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizPublishMessageDetail> findAll(Page page) {
        org.springframework.data.domain.Page<BizPublishMessageDetail> springDataPage = publishMessageDetailDao
            .findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizPublishMessageDetail> findByAcceptMemberId(Page page, long acceptMemberId) {
        return PageUtils.getListData(
            publishMessageDetailDao.findByAcceptMemberIdOrderByGmtCreateDesc(
                PageUtils.createPageable(page), acceptMemberId), page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizPublishMessageDetail> findByAcceptMemberId(long acceptMemberId) {
        return publishMessageDetailDao.findByAcceptMemberId(acceptMemberId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizPublishMessageDetail> findByMessageId(long messageId) {
        return publishMessageDetailDao.findByMessageId(messageId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizPublishMessageDetail> findByAcceptMemberIdAndPublishMemberIdAndGmtCreateBetween(Page page,
                                                                                                   Long acceptMemberId,
                                                                                                   Long publishMemberId,
                                                                                                   String begin,
                                                                                                   String end) {
        if (null == publishMemberId) {
            return PageUtils.getListData(publishMessageDetailDao
                .findByAcceptMemberIdAndGmtCreateBetweenOrderByGmtCreateDesc(
                    PageUtils.createPageable(page), acceptMemberId,
                    DateUtil.string2Date(begin, BizConstant.DATE_FORMAT),
                    DateUtil.string2Date(end, BizConstant.DATE_FORMAT)), page);
        }

        return PageUtils.getListData(publishMessageDetailDao
            .findByAcceptMemberIdAndPublishMemberIdAndGmtCreateBetweenOrderByGmtCreateDesc(
                PageUtils.createPageable(page), acceptMemberId, publishMemberId,
                DateUtil.string2Date(begin, BizConstant.DATE_FORMAT),
                DateUtil.string2Date(end, BizConstant.DATE_FORMAT)), page);
    }

    @Override
    public List<BizPublishMessageDetail> findByAcceptMemberIdAndPublishMemberIdAndStatusAndGmtCreateBetween(Page page,
                                                                                                            Long acceptMemberId,
                                                                                                            Long publishMemberId,
                                                                                                            String status,
                                                                                                            String begin,
                                                                                                            String end) {
        if (null == publishMemberId) {
            return PageUtils.getListData(publishMessageDetailDao
                .findByAcceptMemberIdAndStatusAndGmtCreateBetweenOrderByGmtCreateDesc(
                    PageUtils.createPageable(page), acceptMemberId, status,
                    DateUtil.string2Date(begin, BizConstant.DATE_FORMAT),
                    DateUtil.string2Date(end, BizConstant.DATE_FORMAT)), page);
        }

        return PageUtils
            .getListData(
                publishMessageDetailDao
                    .findByAcceptMemberIdAndPublishMemberIdAndStatusAndGmtCreateBetweenOrderByGmtCreateDesc(
                        PageUtils.createPageable(page), acceptMemberId, publishMemberId, status,
                        DateUtil.string2Date(begin, BizConstant.DATE_FORMAT),
                        DateUtil.string2Date(end, BizConstant.DATE_FORMAT)), page);
    }
}