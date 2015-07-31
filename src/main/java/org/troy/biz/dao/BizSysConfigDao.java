package org.troy.biz.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizSysConfig;

/**
 * 
 * 系统参数配置表
 *
 * @author siren.lb
 * @version $Id: BizSysConfigDao.java,v 0.1 2014年9月26日 下午5:35:57 siren.lb Exp $
 */
public interface BizSysConfigDao extends JpaRepository<BizSysConfig, Long> {

    List<BizSysConfig> findByType(String type);

}
