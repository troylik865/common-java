package org.troy.system.dao;


import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.troy.system.entity.main.SysArea;

/***
 * <p>Title:SysAreaDao </p> 
 *
 * <p>Description:行政区划 接口 </p> 
 *
 * <p>Author:wangj </p> 
 *
 * <p>Copyright: Copyright (c) 2013 </p>
 * 
 * <p>CreateDate: 2013-06-21 09:59 </p>
 *
 */
public interface AreaDao extends JpaRepository<SysArea, Long> {
	
	/**
	 * 根据区域编码查找
	 * @param areaCode
	 * @return
	 */
	SysArea findByAreaCode(String areaCode);
	/**
	 * 根据父级id查询
	 * @param parentId
	 * @param pageable
	 * @return
	 */
	Page<SysArea> findByParentId(Long parentId, Pageable pageable);
	
	/**
	 * 根据父级id查询
	 * @param parentId
	 * @return
	 */
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="org.troy.system.entity.main")
		}
	)
	List<SysArea> findByParentId(Long parentId);
	
	/**
	 * 根据区域级别查询
	 * @param areaLevel
	 * @return
	 */
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="org.troy.system.entity.main")
		}
	)
	@Query("from SysArea sa where sa.areaLevel = ?1 order by sa.areaCode asc")
	List<SysArea> findByAreaLevel(String areaLevel);
	
	/**
	 * 根据父级id和区域名称查询
	 * @param parentId
	 * @param areaName
	 * @param pageable
	 * @return
	 */
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="org.troy.system.entity.main")
		}
	)
	Page<SysArea> findByParentIdAndAreaNameContaining(Long parentId, String areaName, Pageable pageable);
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="org.troy.system.entity.main")
		}
	)
	@Query("from SysArea")
	List<SysArea> findAllWithCache();
}
