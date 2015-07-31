package org.troy.system.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.troy.system.entity.main.SysPara;

/**
 * 系统 参数dao
 * @author wangj
 * 2013-5-17
 */

public interface ParaDao extends JpaRepository<SysPara, Long> {
	/**
	 * 根据参数名称(类似like)查找数据
	 * @param paraName
	 * @param pageable
	 * @return
	 */
	Page<SysPara> findByParaNameContaining(String paraName, Pageable pageable);
	
	/**
	 * 根据参数关键字和参数值查找对象
	 * @param paraKey
	 * @param paraValue
	 * @return
	 */
	SysPara findByParaKeyAndParaValue(String paraKey, String paraValue);
}
