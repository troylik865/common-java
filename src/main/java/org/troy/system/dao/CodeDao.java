package org.troy.system.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.troy.system.entity.main.SysCode;

/**
 * 系统 数据字典dao
 * @author wangj
 * 2013-5-17
 */

public interface CodeDao extends JpaRepository<SysCode, Long> {
	/**
	 * 根据代码类型(类似like)查找数据
	 * @param codeType
	 * @param pageable
	 * @return
	 */
	Page<SysCode> findByCodeTypeContaining(String codeType, Pageable pageable);
	
	SysCode findByCodeTypeAndCodeValue(String codeType, String codeValue);
	
	/**
	 * 从缓存中获取正常数据字典
	 * @param where
	 * @param orderBy
	 * @return
	 */
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="org.troy.system.entity.main")
		}
	)
	@Query("from SysCode m where m.codeState = '0' and codeType = ?1 and m.codeValue not in ?2 order by m.ordNo asc")
	List<SysCode> findByCodeTypeAndCodeValueNotIn(String codeType, Collection<String> notIn);
	
	/**
	 * 获取所有数字字典记录(缓存查询)
	 * @return
	 */
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="org.troy.system.entity.main")
		}
	)
	@Query("from SysCode")
	List<SysCode> findAllWithCache();
}
