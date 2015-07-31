package org.troy.biz.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.biz.entity.BizNews;

/**
 * 
 * 新闻相关的Dao
 *
 * @author siren.lb
 * @version $Id: BizNewsDao.java,v 0.1 2014年9月6日 下午6:04:23 siren.lb Exp $
 */
public interface BizNewsDao extends JpaRepository<BizNews, Long> {

    Page<BizNews> findByGmtCreateIsNotNullOrderByTurnAsc(Pageable page);

}
