package org.troy.biz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizMobileMsg;

/**
 * 
 * 短信相关的操作
 *
 * @author siren.lb
 * @version $Id: BizMobileMsgDao.java,v 0.1 2014年7月29日 上午11:57:59 siren.lb Exp $
 */
public interface BizMobileMsgDao extends JpaRepository<BizMobileMsg, Long> {

}
