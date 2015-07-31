package org.troy.system.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.troy.system.entity.main.SysGroup;

/**
 * 系统组织机构dao
 * @author wangj
 * 2013-5-17
 */
public interface GroupDao extends JpaRepository<SysGroup, Long>{
	
	/**
	 * 根据父级id查询
	 * @param parentId
	 * @param pageable
	 * @return
	 */
	Page<SysGroup> findByParentId(Long parentId, Pageable pageable);
	
	/**
	 * 根据父级id和机构名称查询
	 * @param parentId
	 * @param name
	 * @param pageable
	 * @return
	 */
	Page<SysGroup> findByParentIdAndNameContaining(Long parentId, String name, Pageable pageable);
	
	@QueryHints(value={
			@QueryHint(name="org.hibernate.cacheable",value="true"),
			@QueryHint(name="org.hibernate.cacheRegion",value="org.troy.system.entity.main")
		}
	)
	@Query("from SysGroup")
	List<SysGroup> findAllWithCache();
}
