package org.troy.biz.service.impl;

import java.util.List;

import org.troy.biz.dao.BizAttentionRecordDao;
import org.troy.biz.dao.BizPublishMessageDao;
import org.troy.biz.dao.BizPublishMessageDetailDao;
import org.troy.biz.entity.BizAttentionRecord;
import org.troy.biz.entity.BizPublishMessage;
import org.troy.biz.entity.BizPublishMessageDetail;
import org.troy.biz.service.PublishMessageService;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>Title:PublishMessageServiceImpl </p> 
 *
 * <p>Description:PublishMessage 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
@Service
public class PublishMessageServiceImpl extends BaseJdbcServiceImpl implements PublishMessageService {

    @Autowired
    private BizPublishMessageDao       publishMessageDao;

    @Autowired
    private BizPublishMessageDetailDao bizPublishMessageDetailDao;

    @Autowired
    private BizAttentionRecordDao      bizAttentionRecordDao;

    /**   
     * @param publishMessage  
     * @see org.troy.biz.service.PublishMessageService#save(org.troy.biz.entity.BizPublishMessage )  
     */
    @Transactional
    public void save(BizPublishMessage publishMessage) throws ServiceException {
        publishMessageDao.save(publishMessage);
    }

    @Transactional
    public void saveWithDetail(BizPublishMessage publishMessage) throws ServiceException {

        //保存发布的信息对象
        publishMessageDao.save(publishMessage);
        long publishMemberId = publishMessage.getMemberId();

        //获取关注该大师的会员关注记录
        List<BizAttentionRecord> attentList = bizAttentionRecordDao
            .findByAttentedMemberId(publishMemberId);
        if (CollectionUtils.isEmpty(attentList)) {
            return;
        }

        //根据大师被关注的情况，为每一个关注这生成一条记录
        for (BizAttentionRecord record : attentList) {
            BizPublishMessageDetail detail = new BizPublishMessageDetail();
            detail.setMessageid(publishMessage.getId());
            detail.setAcceptMemberId(record.getMemberId());
            detail.setGmtCreate(publishMessage.getGmtCreate());
            detail.setGmtModified(publishMessage.getGmtModified());
            detail.setPublishMemberId(publishMemberId);
            bizPublishMessageDetailDao.save(detail);
        }
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.PublishMessageService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        publishMessageDao.delete(id);
    }

    /**   
     * @param publishMessage  
     * @see org.troy.biz.service.PublishMessageService#update(org.troy.biz.entity.BizPublishMessage )  
     */
    @Transactional
    public void update(BizPublishMessage publishMessage) throws ServiceException {
        publishMessageDao.save(publishMessage);
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.PublishMessageService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizPublishMessage get(Long id) {
        return publishMessageDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.PublishMessageService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizPublishMessage> findAll(Page page) {
        org.springframework.data.domain.Page<BizPublishMessage> springDataPage = publishMessageDao
            .findByGmtCreateIsNotNullOrderByGmtCreateDesc(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Transactional(readOnly = true)
    public List<BizPublishMessage> findByMemberId(Page page, long memberId) {
        return PageUtils.getListData(
            publishMessageDao.findByMemberIdOrderByGmtCreateDesc(memberId,
                PageUtils.createPageable(page)), page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizPublishMessage> findByMemberIdAndStatus(Page page, long memberId, String status) {
        return PageUtils.getListData(publishMessageDao.findByMemberIdAndStatusOrderByGmtCreateDesc(
            memberId, status, PageUtils.createPageable(page)), page);
    }

    @Override
    public List<BizPublishMessage> findByStatus(Page page, String status) {
        return PageUtils.getListData(
            publishMessageDao.findByStatusOrderByGmtCreateDesc(status,
                PageUtils.createPageable(page)), page);
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> ids) throws ServiceException {
        for (Long id : ids) {
            publishMessageDao.delete(id);
            List<BizPublishMessageDetail> details = bizPublishMessageDetailDao.findByMessageId(id);
            if (CollectionUtils.isEmpty(details)) {
                continue;
            }
            for (BizPublishMessageDetail detail : details) {
                bizPublishMessageDetailDao.delete(detail);
            }
        }
    }
}