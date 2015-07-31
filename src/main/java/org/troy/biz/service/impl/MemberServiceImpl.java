package org.troy.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.biz.constant.BizConstant;
import org.troy.biz.dao.BizFinanceDao;
import org.troy.biz.dao.BizFinanceRecordDao;
import org.troy.biz.dao.BizFinanceTransDetailDao;
import org.troy.biz.dao.BizInvestionDao;
import org.troy.biz.dao.BizMemberDao;
import org.troy.biz.dao.BizSpreadRecordDao;
import org.troy.biz.entity.BizFinance;
import org.troy.biz.entity.BizFinanceRecord;
import org.troy.biz.entity.BizFinanceTransDetail;
import org.troy.biz.entity.BizInvestion;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizSpreadRecord;
import org.troy.biz.enums.FinanceEnum;
import org.troy.biz.enums.TransTypeEnum;
import org.troy.biz.enums.TransUserEnum;
import org.troy.biz.service.MemberService;
import org.troy.biz.util.SpreadMemberUtil;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;

/***
 * <p>Title:MemberServiceImpl </p> 
 *
 * <p>Description:Member 业务接口实现 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Service
public class MemberServiceImpl extends BaseJdbcServiceImpl implements MemberService {

    private static final Logger      logger = Logger.getLogger(MemberServiceImpl.class);

    @Autowired
    private BizMemberDao             memberDao;

    @Autowired
    private BizFinanceDao            bizFinanceDao;

    @Autowired
    private BizSpreadRecordDao       bizSpreadRecordDao;

    @Autowired
    private BizInvestionDao          bizInvestionDao;

    @Autowired
    private BizFinanceTransDetailDao bizFinanceTransDetailDao;

    @Autowired
    private BizFinanceRecordDao      bizFinanceRecordDao;

    /**   
     * @param member  
     * @see org.troy.biz.service.MemberService#save(org.troy.biz.entity.BizMember )  
     */
    @Transactional
    public void save(final BizMember member) throws ServiceException {
        //保存会员信息
        memberDao.save(member);
        String dateStr = DateUtils.formatDate(new Date(), BizConstant.DATE_FORMAT_SHORT);
        member.setMemberNo(dateStr + member.getId());

        //根据新增会员的ID生成对应的大师编号
        memberDao.save(member);

        //将投资方向记录入库
        String investDir = member.getInvestDirection();
        if (StringUtils.isNoneBlank(investDir)) {
            for (String investSingle : StringUtils.split(investDir, BizConstant.SPLIT)) {
                if (StringUtils.isBlank(investSingle)) {
                    continue;
                }
                BizInvestion invest = new BizInvestion();
                invest.setMemberId(member.getId());
                invest.setInvestDirection(investSingle);
                invest.setGmtCreate(member.getGmtCreate());
                invest.setGmtModified(member.getGmtModified());
                bizInvestionDao.save(invest);
            }
        }

        long leftValue = 0L;
        //默认生成一条该会员对应的资金信息
        BizFinance finance = BizFinance.getDefaultFinance(member.getId());
        bizFinanceDao.save(finance);
        BizFinanceRecord record = bizFinanceRecordDao.findByTypeAndFinanceType(
            BizConstant.SYSTEM_LEFT, FinanceEnum.GOLD.getValue());
        if (null == record) {
            BizFinanceRecord temp = new BizFinanceRecord();
            temp.setType(BizConstant.SYSTEM_LEFT);
            temp.setFinanceType(FinanceEnum.GOLD.getValue());
            temp.setFinanceValue(finance.getValue());
            bizFinanceRecordDao.save(temp);
            leftValue = finance.getValue();
        } else {
            leftValue = record.getFinanceValue() + finance.getValue();
            record.setFinanceValue(leftValue);
            bizFinanceRecordDao.save(record);
        }

        //记录到交易明细里面
        BizFinanceTransDetail detail = new BizFinanceTransDetail();
        detail.setMemberId(finance.getMemberId());
        detail.setTransUseId(finance.getId());
        detail.setType(FinanceEnum.GOLD.getValue());
        detail.setValue(finance.getValue());
        detail.setTransUse(TransUserEnum.MEMBER_REGIST.getValue());
        detail.setTransType(TransTypeEnum.IN.getValue());
        Date date = new Date();
        detail.setGmtCreate(date);
        detail.setGmtModified(date);
        detail.setSysLeft(leftValue);
        detail.setAccountBalance(finance.getValue());
        bizFinanceTransDetailDao.save(detail);
    }

    /**   
     * @param member  
     * @see org.troy.biz.service.MemberService#save(org.troy.biz.entity.BizMember )  
     */
    @Transactional
    public void saveWithSpreadKey(BizMember member, String key) throws ServiceException {
        //保存会员信息
        memberDao.save(member);
        String dateStr = DateUtils.formatDate(member.getGmtCreate(), BizConstant.DATE_FORMAT_SHORT);
        member.setMemberNo(dateStr + member.getId());

        //将投资方向记录入库
        String investDir = member.getInvestDirection();
        if (StringUtils.isNoneBlank(investDir)) {
            for (String investSingle : StringUtils.split(investDir, BizConstant.SPLIT)) {
                if (StringUtils.isBlank(investSingle)) {
                    continue;
                }
                BizInvestion invest = new BizInvestion();
                invest.setMemberId(member.getId());
                invest.setInvestDirection(investSingle);
                invest.setGmtCreate(member.getGmtCreate());
                invest.setGmtModified(member.getGmtModified());
                bizInvestionDao.save(invest);
            }
        }

        //根据新增h会员的ID生成对应的大师编号
        memberDao.save(member);

        //默认生成一条该会员对应的资金信息
        BizFinance finance = BizFinance.getDefaultFinance(member.getId());
        bizFinanceDao.save(finance);
        BizFinanceRecord record1 = bizFinanceRecordDao.findByTypeAndFinanceType(
            BizConstant.SYSTEM_LEFT, FinanceEnum.GOLD.getValue());
        if (null == record1) {
            BizFinanceRecord temp = new BizFinanceRecord();
            temp.setType(BizConstant.SYSTEM_LEFT);
            temp.setFinanceType(FinanceEnum.GOLD.getValue());
            temp.setFinanceValue(finance.getValue());
            bizFinanceRecordDao.save(temp);
        } else {
            record1.setFinanceValue(record1.getFinanceValue() + finance.getValue());
            bizFinanceRecordDao.save(record1);
        }

        String real = SpreadMemberUtil.getRealMemberIdBySec(key);
        if (StringUtils.isEmpty(real)) {
            return;
        }

        //组装推广信息的对象
        BizSpreadRecord record = new BizSpreadRecord();
        record.setRegistMemberId(member.getId());
        record.setSpreadMemberId(getId(real));
        record.setGmtCreate(member.getGmtCreate());
        record.setGmtModified(member.getGmtModified());

        //保存推广对象
        bizSpreadRecordDao.save(record);
    }

    private long getId(String idStr) {
        try {
            return Long.parseLong(idStr);
        } catch (Exception e) {
            logger.error("解析得到的Id格式异常，id:[" + idStr + "]");
            return -1;
        }
    }

    /**   
     * @param id  
     * @see org.troy.biz.service.MemberService#delete(java.lang.Long)  
     */
    @Transactional
    public void delete(Long id) throws ServiceException {
        memberDao.delete(id);
    }

    /**   
     * @param member  
     * @see org.troy.biz.service.MemberService#update(org.troy.biz.entity.BizMember )  
     */
    @Transactional
    public void update(BizMember member) throws ServiceException {
        memberDao.save(member);
        //将投资方向记录入库
        String investDir = member.getInvestDirection();

        List<BizInvestion> list = bizInvestionDao.findByMemberId(member.getId());
        for (String investSingle : StringUtils.split(investDir, BizConstant.SPLIT)) {
            boolean exist = false;
            BizInvestion inv = null;
            for (BizInvestion investion : list) {
                if (StringUtils.equals(investion.getInvestDirection(), investSingle)) {
                    exist = true;
                    inv = investion;
                    break;
                }
            }
            Date date = DateUtil.getNowDate();
            if (!exist) {
                BizInvestion invest = new BizInvestion();
                invest.setMemberId(member.getId());
                invest.setInvestDirection(investSingle);
                invest.setGmtCreate(date);
                invest.setGmtModified(date);
                bizInvestionDao.save(invest);
            } else {
                inv.setGmtModified(date);
                bizInvestionDao.save(inv);
            }
        }
    }

    @Transactional
    public void updateWithFinance(BizMember member, String coinValue) throws ServiceException {

        if (StringUtils.isNotBlank(coinValue)) {

            //保存余额信息 
            BizFinance finance = bizFinanceDao.findByMemberIdAndType(member.getId(),
                FinanceEnum.GOLD.getValue());
            long addValue = Long.parseLong(coinValue);
            finance.setValue(finance.getValue() + addValue);
            bizFinanceDao.save(finance);

            BizFinanceRecord record = bizFinanceRecordDao.findByTypeAndFinanceType(
                BizConstant.SYSTEM_LEFT, FinanceEnum.GOLD.getValue());
            long leftValue = 0;
            if (null == record) {
                BizFinanceRecord temp = new BizFinanceRecord();
                temp.setType(BizConstant.SYSTEM_LEFT);
                temp.setFinanceType(FinanceEnum.GOLD.getValue());
                temp.setFinanceValue(addValue);
                bizFinanceRecordDao.save(temp);
                leftValue = finance.getValue();
            } else {
                leftValue = record.getFinanceValue() + addValue;
                record.setFinanceValue(leftValue);
                bizFinanceRecordDao.save(record);
            }

            //添加一条交易明细记录
            BizFinanceTransDetail detail = new BizFinanceTransDetail();
            detail.setMemberId(member.getId());
            detail.setTransUseId(finance.getId());
            detail.setType(FinanceEnum.GOLD.getValue());
            detail.setValue(addValue);
            detail.setTransUse(TransUserEnum.MEMBER_RECHARGE.getValue());
            detail.setTransType(TransTypeEnum.IN.getValue());
            Date date = new Date();
            detail.setGmtCreate(date);
            detail.setGmtModified(date);
            detail.setAccountBalance(finance.getValue());
            detail.setSysLeft(leftValue);
            bizFinanceTransDetailDao.save(detail);
        }

        memberDao.save(member);
        //将投资方向记录入库
        String investDir = member.getInvestDirection();

        List<BizInvestion> list = bizInvestionDao.findByMemberId(member.getId());
        for (String investSingle : StringUtils.split(investDir, BizConstant.SPLIT)) {
            boolean exist = false;
            BizInvestion inv = null;
            for (BizInvestion investion : list) {
                if (StringUtils.equals(investion.getInvestDirection(), investSingle)) {
                    exist = true;
                    inv = investion;
                    break;
                }
            }
            Date date = DateUtil.getNowDate();
            if (!exist) {
                BizInvestion invest = new BizInvestion();
                invest.setMemberId(member.getId());
                invest.setInvestDirection(investSingle);
                invest.setGmtCreate(date);
                invest.setGmtModified(date);
                bizInvestionDao.save(invest);
            } else {
                inv.setGmtModified(date);
                bizInvestionDao.save(inv);
            }
        }
    }

    /**   
     * @param id
     * @return  
     * @see org.troy.biz.service.MemberService#get(java.lang.Long)  
     */
    @Transactional(readOnly = true)
    public BizMember get(Long id) {
        return memberDao.findOne(id);
    }

    /**   
     * @return  
     * @see org.troy.biz.service.MemberService#findAll(com.cnnct.common.utils.dwz.Page)  
     */
    @Transactional(readOnly = true)
    public List<BizMember> findAll(Page page) {
        org.springframework.data.domain.Page<BizMember> springDataPage = memberDao
            .findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public BizMember getBizMemberByUserName(String userName) {
        return memberDao.findByUserName(userName);
    }

    @Override
    @Transactional(readOnly = true)
    public BizMember getBizMemberByMemberNo(String memberNo) {
        return memberDao.findBizMemberByMemberNo(memberNo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizMember> findBizMembers(Specification<BizMember> specification, Page page) {
        org.springframework.data.domain.Page<BizMember> springDataPage = memberDao.findAll(
            specification, PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BizMember> findBizMemberByInvestAndValidate(String type, String vali) {
        List<BizInvestion> temp = bizInvestionDao.findByInvestDirectionAndIsValidated(type, vali);
        if (CollectionUtils.isEmpty(temp)) {
            return null;
        }
        List<BizMember> list = new ArrayList<BizMember>();
        for (BizInvestion invest : temp) {
            Long memberId = invest.getMemberId();
            BizMember member = memberDao.findOne(memberId);
            if (null == member) {
                continue;
            }
            list.add(member);
        }
        return list;
    }

    @Override
    public BizMember getAdminAccount() {
        return memberDao.findByUserName(BizConstant.ADMIN);
    }

    @Override
    public List<BizMember> getBizMemberByName(String name) {
        return memberDao.findByNameContaining(name);
    }

    @Override
    public BizMember getSingleBizMemberByName(String name) {
        return memberDao.findByName(name);
    }
}