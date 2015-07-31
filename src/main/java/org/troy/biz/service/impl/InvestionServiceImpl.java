package org.troy.biz.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.troy.biz.dao.BizInvestionDao;
import org.troy.biz.dao.BizSiteMessageDao;
import org.troy.biz.dao.BizTransRecordStatisDao;
import org.troy.biz.entity.BizInvestion;
import org.troy.biz.entity.BizSiteMessage;
import org.troy.biz.entity.BizTransRecordStatis;
import org.troy.biz.service.InvestionService;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/**
 * 
 * 投资方向实现类
 *
 *
 * @author siren.lb
 * @version $Id: InvestionServiceImpl.java,v 0.1 2014年7月28日 下午10:32:07 siren.lb Exp $
 */
@Service
public class InvestionServiceImpl extends BaseJdbcServiceImpl implements InvestionService {

    @Autowired
    private BizInvestionDao         bizInvestionDao;

    @Autowired
    private BizSiteMessageDao       bizSiteMessageDao;

    @Autowired
    private BizTransRecordStatisDao bizTransRecordStatisDao;

    @Override
    public BizInvestion findByMemberIdAndInvestDirection(Long memberId, String investDirection) {
        return bizInvestionDao.findByMemberIdAndInvestDirection(memberId, investDirection);
    }

    @Override
    public List<BizInvestion> findByInvestDirectionAndIsValidated(String investDirection,
                                                                  String isValidated) {
        return bizInvestionDao.findByInvestDirectionAndIsValidated(investDirection, isValidated);
    }

    @Override
    public List<BizInvestion> findByMemberId(Long memberId) {
        return bizInvestionDao.findByMemberId(memberId);
    }

    @Override
    public void update(BizInvestion bizInvestion) throws ServiceException {
        bizInvestionDao.save(bizInvestion);
    }

    @Override
    public void save(BizInvestion bizInvestion) throws ServiceException {
        bizInvestionDao.save(bizInvestion);
    }

    private void updateSiteMessage(BizSiteMessage message) {
        bizSiteMessageDao.save(message);
    }

    @Override
    public void saveWithUpdateSiteMessage(BizInvestion bizInvestion, BizSiteMessage message)
                                                                                            throws ServiceException {
        bizInvestionDao.save(bizInvestion);
        updateSiteMessage(message);

    }

    @Override
    public void updateWithUpdateSiteMessage(BizInvestion bizInvestion, BizSiteMessage message)
                                                                                              throws ServiceException {
        bizInvestionDao.save(bizInvestion);
        updateSiteMessage(message);

    }

    @Override
    public List<BizInvestion> findAll(Page page) {
        return PageUtils.getListData(bizInvestionDao
            .findByGmtCreateIsNotNullOrderByMemberIdAsc(PageUtils.createPageable(page)), page);
    }

    @Override
    public List<BizInvestion> findAllByInvestDirectionAndIsValidated(Page page,
                                                                     String investDirect,
                                                                     String isValidated) {
        return PageUtils.getListData(bizInvestionDao
            .findByGmtCreateIsNotNullAndInvestDirectionAndIsValidatedOrderByMemberIdAsc(
                PageUtils.createPageable(page), investDirect, isValidated), page);
    }

    @Override
    public List<BizInvestion> findByMemberId(Page page, Long memberId) {
        return PageUtils.getListData(bizInvestionDao.findByMemberIdOrderByGmtCreateDesc(
            PageUtils.createPageable(page), memberId), page);
    }

    @Override
    public List<BizInvestion> findByMemberIdIn(Page page, List<Long> ids) {
        return PageUtils
            .getListData(bizInvestionDao.findByMemberIdInOrderByMemberIdAsc(
                PageUtils.createPageable(page), ids), page);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BizInvestion> findByMemberIdInAndIsValidated(Page page, List<Long> ids,
                                                             String isValidated) {

        if (StringUtils.equals(isValidated, "0")) {
            String[] id = new String[ids.size()];
            for (int j = 0; j < ids.size(); j++) {
                id[j] = ids.get(j) + "";

            }

            Object[] params = new Object[2];
            int perPageSize = page.getNumPerPage();
            int curPage = page.getPlainPageNum();
            curPage = curPage - 1;
            params[0] = perPageSize * curPage;
            params[1] = page.getNumPerPage();
            String sql = "select * from biz_investion where member_id in ("
                         + StringUtils.join(id, ",")
                         + ") and (is_validated = '0' or is_validated is null) order by member_id asc limit ?,?";
            try {
                String sql1 = "select count(id) from biz_investion where member_id in ("
                              + StringUtils.join(id, ",")
                              + ") and (is_validated = '0' or is_validated is null) order by member_id";
                int count = super.queryForInt(sql1, new Object[] {});
                page.setTotalCount(count);
                page.setTotalPage(count / perPageSize + 1);
                return (List<BizInvestion>) super.queryForList(sql, params, BizInvestion.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return PageUtils.getListData(
            bizInvestionDao.findByMemberIdInAndIsValidatedOrderByMemberIdAsc(
                PageUtils.createPageable(page), ids, isValidated), page);
    }

    @Override
    public List<BizInvestion> findByIdIn(List<Long> ids) {
        return bizInvestionDao.findByIdIn(ids);
    }

    @Override
    public void updateInvestions(List<BizInvestion> bizInvestions) throws ServiceException {
        if (CollectionUtils.isEmpty(bizInvestions)) {
            return;
        }
        bizInvestionDao.save(bizInvestions);
    }

    @Override
    public void updateInvestionsWithStatis(List<BizInvestion> bizInvestions)
                                                                            throws ServiceException {
        if (CollectionUtils.isEmpty(bizInvestions)) {
            return;
        }
        bizInvestionDao.save(bizInvestions);
        for (BizInvestion invest : bizInvestions) {
            BizTransRecordStatis statis = bizTransRecordStatisDao.findByMemberIdAndInvestType(
                invest.getMemberId(), invest.getInvestDirection());
            if (null == statis) {
                continue;
            }
            statis.setGmtModified(DateUtil.getNowDate());
            statis.setIsValidated(invest.getIsValidated());
            bizTransRecordStatisDao.save(statis);
        }

    }

    @Override
    public List<BizInvestion> findByMemberIdInAndInvestDirectionAndIsValidated(Page page,
                                                                               List<Long> ids,
                                                                               String investDirection,
                                                                               String isValidated) {
        return PageUtils.getListData(
            bizInvestionDao.findByMemberIdInAndInvestDirectionAndIsValidated(
                PageUtils.createPageable(page), ids, investDirection, isValidated), page);
    }

    @Override
    public List<BizInvestion> findByIsValidated(Page page, String isValidated) {
        if (StringUtils.equals(isValidated, "0")) {
            return PageUtils.getListData(bizInvestionDao.findByIsValidatedOrIsValidatedIsNull(
                PageUtils.createPageable(page), "0"), page);
        }
        return PageUtils.getListData(
            bizInvestionDao.findByIsValidated(PageUtils.createPageable(page), isValidated), page);
    }

}
