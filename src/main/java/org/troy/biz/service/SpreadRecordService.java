package org.troy.biz.service;

import java.util.List;

import org.troy.biz.entity.BizSpreadRecord;
import org.troy.common.component.jdbc.BaseJdbcService;
import org.troy.common.utils.dwz.Page;

/**
 * 
 * 推广链接对应的服务
 *
 *
 * @author siren.lb
 * @version $Id: SpreadRecordService.java,v 0.1 2014年9月17日 下午10:23:16 siren.lb Exp $
 */
public interface SpreadRecordService extends BaseJdbcService {

    List<BizSpreadRecord> findAll(Page page);

    List<BizSpreadRecord> findBySpreadMemberId(Page page, long spreadMemberId);

    List<BizSpreadRecord> findBySpreadMemberIds(Page page, List<Long> ids);

}
