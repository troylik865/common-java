/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package org.troy.biz.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;
import org.troy.biz.entity.BizTransRecord;
import org.troy.biz.entity.vo.BizTransRecordVO;
import org.troy.common.utils.MoneyUtil;

/**
 * 总体说明
 *
 * <p>具体说明</p>
 *
 * @author wb-chenyy@alipay.com
 * @version $Id: BizTransRecordUtil.java,v 0.1 2014-7-14 下午05:06:28 wb-chenyy Exp $
 */
public class BizTransRecordUtil {

    /**
     * 转化为数据库操作对象BizTransRecord
     * 
     * @param vo
     * @return
     */
    public static BizTransRecord convertBizTransRecord(BizTransRecordVO vo) {
        if (vo == null) {
            return null;
        }
        BizTransRecord record = new BizTransRecord();
        Date date = new Date();
        record.setGmtCreate(date);
        record.setGmtModified(date);
        record.setCurrIncome(StringUtils.isEmpty(vo.getCurrIncomeWYuan()) ? 0 : MoneyUtil
            .getWYuanToCent(vo.getCurrIncomeWYuan()));
        record.setCurrOutcome(StringUtils.isEmpty(vo.getCurrOutcomeWYuan()) ? 0 : MoneyUtil
            .getWYuanToCent(vo.getCurrOutcomeWYuan()));
        record.setCurrValue(StringUtils.isEmpty(vo.getCurrValueWYuan()) ? 0 : MoneyUtil
            .getWYuanToCent(vo.getCurrValueWYuan()));
        record.setLastDayValue(StringUtils.isEmpty(vo.getLastDayValueWYuan()) ? 0 : MoneyUtil
            .getWYuanToCent(vo.getLastDayValueWYuan()));
        record.setFee(StringUtils.isEmpty(vo.getFeeWYuan()) ? 0 : MoneyUtil.getWYuanToCent(vo
            .getFeeWYuan()));
        record.setGainsAndLosses(StringUtils.isEmpty(vo.getGainsAndLossesWYuan()) ? 0 : MoneyUtil
            .getWYuanToCent(vo.getGainsAndLossesWYuan()));
        record.setOrigionValue(StringUtils.isEmpty(vo.getOrigionValueWYuan()) ? 0 : MoneyUtil
            .getWYuanToCent(vo.getOrigionValueWYuan()));
        record.setRecordType(vo.getRecordType());
        record.setImportDate(vo.getImportDate());
        record.setInvestType(vo.getInvestType());
        return record;
    }

    public static BizTransRecord convertToBizTransRecord(BizTransRecordVO vo) {
        if (vo == null) {
            return null;
        }
        BizTransRecord record = new BizTransRecord();
        Date date = new Date();
        record.setGmtCreate(date);
        record.setGmtModified(date);
        record.setCurrIncome(vo.getCurrIncome());
        record.setCurrOutcome(vo.getCurrOutcome());
        record.setCurrValue(vo.getCurrValue());
        record.setLastDayValue(vo.getLastDayValue());
        record.setFee(vo.getFee());
        record.setGainsAndLosses(vo.getGainsAndLosses());
        record.setOrigionValue(vo.getOrigionValue());
        record.setRecordType(vo.getRecordType());
        record.setImportDate(vo.getImportDate());
        record.setInvestType(vo.getInvestType());
        return record;
    }

    /**
     * 转化为数据库操作对象BizTransRecordVO
     * 
     * @param vo
     * @return
     */
    public static BizTransRecordVO convertBizTransRecordVO(BizTransRecord record) {
        if (record == null) {
            return null;
        }
        BizTransRecordVO vo = new BizTransRecordVO();
        vo.setMemberId(record.getMemberId()+"");
        vo.setId(record.getId());
        vo.setCurrIncome(record.getCurrIncome());
        vo.setCurrOutcome(record.getCurrOutcome());
        vo.setCurrValue(record.getCurrValue());
        vo.setLastDayValue(record.getLastDayValue());
        vo.setFee(record.getFee());
        vo.setGainsAndLosses(record.getGainsAndLosses());
        vo.setOrigionValue(record.getOrigionValue());
        vo.setImportDate(record.getImportDate());
        vo.setInvestType(record.getInvestType());
        vo.setRecordType(record.getRecordType());
        vo.setStatus(record.getStatus());
        vo.setTotalGainsAndLosses(record.getTotalGainsAndLosses());
        return vo;
    }

    /**
     * convertBizTransRecordVOList
     * 
     * @param records
     * @return
     */
    public static List<BizTransRecordVO> convertBizTransRecordVOList(List<BizTransRecord> records) {
        List<BizTransRecordVO> recordVOS = new ArrayList<BizTransRecordVO>();
        for (BizTransRecord record : records) {
            recordVOS.add(BizTransRecordUtil.convertBizTransRecordVO(record));
        }
        return recordVOS;
    }
}
