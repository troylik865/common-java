package org.troy.system.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.troy.system.entity.main.SysModule;

/**
 * 系统功能模块dao
 * @author wangj
 * 2013-5-17
 */

public interface ModuleDao extends JpaRepository<SysModule, Long> {
		
	/**
	 * 根据父级id查询
	 * @param parentId
	 * @param pageable
	 * @return
	 */
	Page<SysModule> findByParentId(Long parentId, Pageable pageable);
	
	/**
	 * 根据父级id和模块名称查询
	 * @param parentId
	 * @param name
	 * @param pageable
	 * @return
	 */
	Page<SysModule> findByParentIdAndNameContaining(Long parentId, String name, Pageable pageable);
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="org.troy.system.entity.main")
		}
	)
	@Query("from SysModule m order by m.priority ASC")
	List<SysModule> findAllWithCache();
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="org.troy.system.entity.main")
		}
	)
	@Query("from SysModule m where m.parent.id = ?1 order by m.priority ASC")
	List<SysModule> findByParentId(Long parentId);
	
	/**
	 * 根据actions,methods，查找Module
	 * 描述
	 * @param actions
	 * @param methods
	 * @return
	 */
	List<SysModule> findByActionsAndMethods(String actions,String methods);
}
