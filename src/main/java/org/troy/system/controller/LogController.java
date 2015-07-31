package org.troy.system.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogLevel;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.entity.main.SysLog;
import org.troy.system.service.LogService;

import com.google.common.collect.Lists;

/**
 * 日志Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/system/config/log")
public class LogController extends BaseController{
	@Autowired
	private LogService logService;
	
	private static final String LIST = "system/config/log/list";
	
	@RequiresPermissions("Log:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page, SysLog log,  Date startDate, Date endDate, Map<String, Object> map) {
		LogSpecification logSpecification = new LogSpecification(log, startDate, endDate);
		List<SysLog> logEntities = logService.findByExample(logSpecification, page); 
		
		map.put("log", log);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("page", page);
		map.put("logEntities", logEntities);
		map.put("logLevels", LogLevel.values());
		
		return LIST;
	}
	
	@Log(message="删除了{0}条日志。ids:{1}")
	@RequiresPermissions("Log:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		AjaxObject ajaxObject = new AjaxObject("删除日志成功！");
		for (Long id : ids) {
			logService.delete(id);
		}
		
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{ids.length,Arrays.toString(ids)}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	/**
	 * 定义log查询规则
	 * @author wangj
	 * 2013-6-8
	 */
	private class LogSpecification implements Specification<SysLog> {
		
		private SysLog log;
		private Date startDate;
		private Date endDate; 
		
		public LogSpecification(SysLog log) {
			this.log = log;	
		}
		
		public LogSpecification(SysLog log, Date startDate, Date endDate) {
			this(log);
			this.startDate = startDate;
			this.endDate = endDate;
		}

		/**   
		 * @param root
		 * @param query
		 * @param criteriabuilder
		 * @return  
		 * @see org.springframework.data.jpa.domain.Specification#toPredicate(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaQuery, javax.persistence.criteria.CriteriaBuilder)  
		 */
		public Predicate toPredicate(Root<SysLog> root,
				CriteriaQuery<?> query, CriteriaBuilder criteriabuilder) {
			List<Predicate> predicateList = Lists.newArrayList();
			
			if (log.getLogLevel() != null) {
				Predicate logLevelPredicate = criteriabuilder.equal(root.get("logLevel"), log.getLogLevel());
				predicateList.add(logLevelPredicate);
			}
			
			if (StringUtils.isNotBlank(log.getLogType())) {
				Predicate logTypePredicate = criteriabuilder.equal(root.get("logType"), log.getLogType());
				predicateList.add(logTypePredicate);
			}
			
			if (StringUtils.isNotBlank(log.getUsername())) {
				Predicate usernamePredicate = criteriabuilder.equal(root.get("username"), log.getUsername());
				predicateList.add(usernamePredicate);
			}
			
			if (StringUtils.isNotBlank(log.getIpAddress())) {
				Predicate ipAddressPredicate = criteriabuilder.equal(root.get("ipAddress"), log.getIpAddress());
				predicateList.add(ipAddressPredicate);
			}
			
			if (startDate != null && endDate == null) {
				endDate = new Date();
			} else if (endDate != null && startDate == null) {
				startDate = new Date(0L);
		    } 
			
			if (startDate != null && endDate != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				endDate = calendar.getTime();
		    	
				Predicate datePredicate = criteriabuilder.between(root.<Date>get("createTime"), startDate, endDate);
				predicateList.add(datePredicate);	
			}
			query.orderBy(criteriabuilder.desc(root.get("id")));//id倒序
			Predicate[] predicates = new Predicate[predicateList.size()];
	        predicateList.toArray(predicates);
			
			return criteriabuilder.and(predicates);
		}
		
	}
}
