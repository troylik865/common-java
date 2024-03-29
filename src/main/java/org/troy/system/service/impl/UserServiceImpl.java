package org.troy.system.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.troy.common.component.jdbc.BaseJdbcServiceImpl;
import org.troy.common.exception.ServiceException;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.dao.UserDao;
import org.troy.system.entity.main.SysUser;
import org.troy.system.service.UserService;
import org.troy.system.shiro.ShiroDbRealm;
import org.troy.system.shiro.ShiroDbRealm.HashPassword;
import org.troy.system.util.SysConst;

/**
 * 系统用户业务接口实现类
 * @author wangj
 * 2013-5-17
 */
@Service
public class UserServiceImpl extends BaseJdbcServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ShiroDbRealm shiroRealm;
	
	/**
	 * 
	 * @param user
	 * @throws ServiceException  
	 * @see org.troy.system.service.service.UserService#save(com.cnnct.system.entity.main.User)
	 */
	@Transactional
	public void save(SysUser user) throws ServiceException {		
		if (userDao.findByUsername(user.getUsername()) != null) {
			throw new ServiceException("登录名：" + user.getUsername() + "已存在。");
		}
		
		if (userDao.findByRealname(user.getRealname()) != null) {
			throw new ServiceException("真实名：" + user.getRealname() + "已存在。");
		}
		
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
			HashPassword hashPassword = shiroRealm.encrypt(user.getPlainPassword());
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
		}
		
		userDao.save(user);
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}

	/**   
	 * @param user  
	 * @see org.troy.system.service.service.UserService#update(com.cnnct.system.entity.main.User)  
	 */
	@Transactional
	public void update(SysUser user) throws ServiceException{
		if (isSupervisor(user.getId())) {
			logger.warn("操作员{},尝试修改超级管理员用户", SecurityUtils.getSubject().getPrincipal());
			throw new ServiceException("不能修改超级管理员用户");
		}
		//设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
		
		if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
			HashPassword hashPassword = shiroRealm.encrypt(user.getPlainPassword());
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
		}
		
		userDao.save(user);
		shiroRealm.clearCachedAuthorizationInfo(user.getUsername());
	}

	/**   
	 * @param id  
	 * @see org.troy.system.service.service.UserService#delete(java.lang.Long)  
	 */
	@Transactional
	public void delete(Long id) throws ServiceException {
		if (isSupervisor(id)) {
			logger.warn("操作员{}，尝试删除超级管理员用户", SecurityUtils.getSubject()
					.getPrincipal() + "。");
			throw new ServiceException("不能删除超级管理员用户。");
		}
		userDao.delete(id);
	}

	/**   
	 * @param id
	 * @return  
	 * @see org.troy.system.service.service.UserService#get(java.lang.Long)  
	 */
	@Transactional(readOnly=true)
	public SysUser get(Long id) {
		return userDao.findOne(id);
	}
	/**   
	 * @param username
	 * @return  
	 * @see org.troy.system.service.service.UserService#get(java.lang.String)  
	 */
	@Transactional(readOnly=true)
	public SysUser get(String username) {
		return userDao.findByUsername(username);
	}

	/**   
	 * @return  
	 * @see org.troy.system.service.service.UserService#findAll()  
	 */
	@Transactional(readOnly=true)
	public List<SysUser> findAll() {
		return userDao.findAll();
	}
	/**   
	 * @return  
	 * @see org.troy.system.service.service.UserService#findAll(com.cnnct.common.utils.dwz.Page)  
	 */
	@Transactional(readOnly=true)
	public List<SysUser> findAll(Page page) {
		org.springframework.data.domain.Page<SysUser> springDataPage = userDao.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/**   
	 * @param page
	 * @param name
	 * @return  
	 * @see org.troy.system.service.service.UserService#find(com.cnnct.common.utils.dwz.Page, java.lang.String)  
	 */
	@Transactional(readOnly=true)
	public List<SysUser> find(Page page, String name) {
		org.springframework.data.domain.Page<SysUser> pageUser = 
				userDao.findByUsernameContaining(name, PageUtils.createPageable(page));
		PageUtils.injectPageProperties(page, pageUser);
		return pageUser.getContent();
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == SysConst.ROOTID;
	}
}
