package org.troy.system.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.troy.system.dao.AreaDao;
import org.troy.system.dao.GroupDao;
import org.troy.system.dao.ModuleDao;
import org.troy.system.service.CacheService;

/** 
 * 缓存业务接口实现类
 * @author wangj
 * 2013-5-17
 */
@Service
public class CacheServiceImpl implements CacheService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private ModuleDao moduleDao;
	
	/**
	 * @see org.troy.system.service.service.CacheService#clearAllCache()
	 */
	public void clearAllCache() {
		em.getEntityManagerFactory().getCache().evictAll();
	}

	/**
	 * @see org.troy.system.service.service.CacheService#clearAllCache()
	 */
	public void updateCache() {
		em.getEntityManagerFactory().getCache().evictAll();
		areaDao.findAllWithCache();//更新行政区划缓存
		groupDao.findAllWithCache();//更新组织机构缓存
		moduleDao.findAllWithCache();//更新菜单权限缓存
	}
}
