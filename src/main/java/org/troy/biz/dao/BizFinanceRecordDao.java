package org.troy.biz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizFinanceRecord;

/**
 * 交易记录查询的dao
 *
 *
 * @author siren.lb
 * @version $Id: BizFinanceRecordDao.java,v 0.1 2014年10月30日 下午12:09:31 siren.lb Exp $
 */
public interface BizFinanceRecordDao extends JpaRepository<BizFinanceRecord, Long> {

    
    /**
     * 根据记录类型和资金类型 获取到相应的记录
     * 
     * @param type
     * @param financeType
     * @return
     */
    public BizFinanceRecord findByTypeAndFinanceType(String type, String financeType);
}
