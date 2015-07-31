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
import org.troy.biz.entity.BizFinance;
import org.troy.biz.service.FinanceService;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

/***
 * <p>Title:FinanceController </p> 
 *
 * <p>Description:Finance 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:18 </p>
 *
 */
@Controller
@RequestMapping("/biz/finance")
public class FinanceController extends ViewController{

	@Autowired
	private FinanceService financeService;
	
	private static final String CREATE = "biz/finance/create";
	private static final String UPDATE = "biz/finance/update";
	private static final String LIST = "biz/finance/list";
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了finance,id:{0}。")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(BizFinance finance) {
		evenName = "finance添加";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		
		financeService.save(finance);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{finance.getId()}));
		return ajaxObject.toString();
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		BizFinance finance = financeService.get(id);
		map.put("finance", finance);
		return UPDATE;
	}
	
	@Log(message="修改了finance,id:{0}。")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(BizFinance finance) {
		evenName = "finance修改";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		
		financeService.update(finance);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{finance.getId()}));
		return ajaxObject.toString();
	}
	
	@Log(message="删除了finance,id:{0}。")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		evenName = "finance删除";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		BizFinance finance = null;
		finance = financeService.get(id);
		financeService.delete(id);
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{finance.getId()}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	@Log(message="删除了finance,ids:{0}。")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		evenName = "finance删除";
		AjaxObject ajaxObject = new AjaxObject(evenName+"成功！");
		String[] financeTypes = new String[ids.length];
		for (int i = 0; i < ids.length; i++) {
			BizFinance finance = financeService.get(ids[i]);
			financeService.delete(finance.getId());
			
			financeTypes[i] = finance.getId().toString();
		}
		//记录日志
		LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(financeTypes)}));
		ajaxObject.setCallbackType("");
		return ajaxObject.toString();
	}
	
	
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Page page,  Map<String, Object> map) {
		List<BizFinance> finances = null;
		finances = financeService.findAll(page);
		map.put("page", page);
		map.put("finances", finances);
		return LIST;
	}

}
