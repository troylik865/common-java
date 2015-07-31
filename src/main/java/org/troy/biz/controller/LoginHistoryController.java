package org.troy.biz.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.entity.BizLoginHistory;
import org.troy.biz.service.LoginHistoryService;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

/***
 * <p>Title:LoginHistoryController </p> 
 *
 * <p>Description:LoginHistory 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Controller
@RequestMapping("/biz/loginHistory")
public class LoginHistoryController extends ViewController{

	@Autowired
	private LoginHistoryService loginHistoryService;
	
	private static final String CREATE = "biz/loginHistory/create";
	private static final String UPDATE = "biz/loginHistory/update";
	private static final String LIST = "biz/loginHistory/list";
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了loginHistory,id:{0}。")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(BizLoginHistory loginHistory) {
		evenName = "loginHistory添加";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		
		loginHistoryService.save(loginHistory);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{loginHistory.getId()}));
		return ajaxObject.toString();
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		BizLoginHistory loginHistory = loginHistoryService.get(id);
		map.put("loginHistory", loginHistory);
		return UPDATE;
	}
	
	@Log(message="修改了loginHistory,id:{0}。")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(BizLoginHistory loginHistory) {
		evenName = "loginHistory修改";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		
		loginHistoryService.update(loginHistory);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{loginHistory.getId()}));
		return ajaxObject.toString();
	}
	
	@Log(message="删除了loginHistory,id:{0}。")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		evenName = "loginHistory删除";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		BizLoginHistory loginHistory = null;
		loginHistory = loginHistoryService.get(id);
		loginHistoryService.delete(id);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{loginHistory.getId()}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	@Log(message="删除了loginHistory,ids:{0}。")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		evenName = "loginHistory删除";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		String[] loginHistoryTypes = new String[ids.length];
		for (int i = 0; i < ids.length; i++) {
			BizLoginHistory loginHistory = loginHistoryService.get(ids[i]);
			loginHistoryService.delete(loginHistory.getId());
			
			loginHistoryTypes[i] = loginHistory.getId().toString();
		}
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(loginHistoryTypes)}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page,  Map<String, Object> map) {
		List<BizLoginHistory> loginHistorys = null;
		loginHistorys = loginHistoryService.findAll(page);
		map.put("page", page);
		map.put("loginHistorys", loginHistorys);
		return LIST;
	}

}
