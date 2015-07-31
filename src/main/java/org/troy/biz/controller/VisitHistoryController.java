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
import org.troy.biz.entity.BizVisitHistory;
import org.troy.biz.service.VisitHistoryService;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

/***
 * <p>Title:VisitHistoryController </p> 
 *
 * <p>Description:VisitHistory 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:19 </p>
 *
 */
@Controller
@RequestMapping("/biz/visitHistory")
public class VisitHistoryController extends ViewController {

    @Autowired
    private VisitHistoryService visitHistoryService;

    private static final String CREATE = "biz/visitHistory/create";
    private static final String UPDATE = "biz/visitHistory/update";
    private static final String LIST   = "biz/visitHistory/list";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了visitHistory,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    String create(BizVisitHistory visitHistory) {
        evenName = "visitHistory添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        visitHistoryService.save(visitHistory);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { visitHistory.getId() }));
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizVisitHistory visitHistory = visitHistoryService.get(id);
        map.put("visitHistory", visitHistory);
        return UPDATE;
    }

    @Log(message = "修改了visitHistory,id:{0}。")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    String update(BizVisitHistory visitHistory) {
        evenName = "visitHistory修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        visitHistoryService.update(visitHistory);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { visitHistory.getId() }));
        return ajaxObject.toString();
    }

    @Log(message = "删除了visitHistory,id:{0}。")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable Long id) {
        evenName = "visitHistory删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizVisitHistory visitHistory = null;
        visitHistory = visitHistoryService.get(id);
        visitHistoryService.delete(id);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { visitHistory.getId() }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @Log(message = "删除了visitHistory,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    String deleteMany(Long[] ids) {
        evenName = "visitHistory删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] visitHistoryTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizVisitHistory visitHistory = visitHistoryService.get(ids[i]);
            visitHistoryService.delete(visitHistory.getId());

            visitHistoryTypes[i] = visitHistory.getId().toString();
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(visitHistoryTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        List<BizVisitHistory> visitHistorys = null;
        visitHistorys = visitHistoryService.findAll(page);
        map.put("page", page);
        map.put("visitHistorys", visitHistorys);
        return LIST;
    }

}
