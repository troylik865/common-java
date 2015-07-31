package org.troy.biz.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.biz.dao.BizReviewLogDao;
import org.troy.biz.entity.BizReviewLog;
import org.troy.biz.entity.vo.BizReviewRequest;
import org.troy.biz.service.BizReviewLogService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>
 * Title:BizReviewLogServiceImpl
 * </p>
 * 
 * <p>
 * Description:BizReviewLog 业务接口实现
 * </p>
 * 
 * <p>
 * Author:jiangb
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 * <p>
 * CreateDate: 2014-06-23 22:17
 * </p>
 * 
 */
@Service
public class BizReviewLogServiceImpl extends BaseJdbcServiceImpl implements
		BizReviewLogService {

	@Autowired
	private BizReviewLogDao bizReviewLogDao;

	/**
	 * @param bizReviewLog
	 * @see org.troy.biz.service.BizReviewLogService#save(org.troy.biz.dao.BizReviewLogDao
	 *      )
	 */
	@Transactional
	public void save(BizReviewLog bizReviewLog) throws ServiceException {
		bizReviewLogDao.save(bizReviewLog);
	}

	/**
	 * @return
	 * @see org.troy.biz.service.PublishMessageDetailService#findAll(com.cnnct.common.utils.dwz.Page)
	 */
	@Transactional(readOnly = true)
	public List<BizReviewLog> findAll(Page page) {
		org.springframework.data.domain.Page<BizReviewLog> springDataPage = bizReviewLogDao
				.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	@Transactional
	public void create(BizReviewRequest request) {
		BizReviewLog log=new BizReviewLog();
		log.setBizId(request.getBizId());
		log.setBizType(request.getBizType());
		log.setComment(request.getComment());
		log.setParam(request.getParam());
		log.setUserId(request.getUserId());
		Date date=DateUtil.getCurrDate();
		log.setGmtCreate(date);
		log.setGmtModified(date);
		bizReviewLogDao.save(log);
	}

	@Override
	public BizReviewLog getBizReviewLog(Long bizId, String bizType) {
		List<BizReviewLog> list=bizReviewLogDao.findByBizIdAndBizTypeOrderByGmtCreateDesc(bizId, bizType);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

}