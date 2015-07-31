package org.troy.platform.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.platform.entity.PlatformItem;
import org.troy.platform.service.PlatformItemService;
import org.troy.system.controller.ViewController;

@Controller
@RequestMapping("/platform/backstage/platformItem")
public class PlatformItemController extends ViewController {

    @Autowired
    private PlatformItemService platformItemService;

    private static final String LIST   = "platform/backstage/platformItem/list";

    private static final String CREATE = "platform/backstage/platformItem/create";
    private static final String UPDATE = "platform/backstage/platformItem/update";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(PlatformItem platformItem) {
        evenName = "添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        platformItemService.save(platformItem);
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        PlatformItem platformItem = platformItemService.findById(id);
        map.put("obj", platformItem);
        return UPDATE;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(PlatformItem platformItem, HttpServletRequest request) {
        evenName = "修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        platformItemService.update(platformItem);
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();

        List<PlatformItem> list = platformItemService.findAll();
        for (int i = 0; i < list.size(); i++) {
            PlatformItem item = list.get(i);
            Map<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("id", item.getId() + "");
            tempMap.put("name", item.getName());
            tempMap.put("itemEarn", item.getItemEarn());
            tempMap.put("itemDesc", item.getItemDesc());
            mapList.add(tempMap);
        }
        map.put("page", page);
        map.put("data", mapList);
        return LIST;
    }
    
    @RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String delete(Long[] ids) {
        evenName = "删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            List<Long> longIds = new ArrayList<Long>();
            for (Long id : ids) {
                longIds.add(id);
            }
            platformItemService.deleteAll(longIds);
            ajaxObject.setMessage(evenName + "成功！");
        } catch (Exception e) {
            ajaxObject.setMessage(evenName + "失败！");
        }
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

}