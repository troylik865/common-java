package org.troy.biz.controller.backstage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.cache.TroyCacheManager;
import org.troy.biz.entity.BizColumnList;
import org.troy.biz.enums.ColumnListEnum;
import org.troy.biz.service.ColumnListService;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

import com.google.gson.Gson;

/**
 * 
 *  软件中心的内容展示
 *
 * @author siren.lb
 * @version $Id: BizColumnListController.java,v 0.1 2014年9月8日 下午3:51:21 siren.lb Exp $
 */
@Controller
@RequestMapping("/biz/backstage/columnList")
public class BizColumnListController extends ViewController {

    @Autowired
    private ColumnListService                     columnListService;

    @Autowired
    private TroyCacheManager<List<BizColumnList>> columnListCacheManager;

    private static final String                   CREATE = "biz/backstage/columnList/create";
    private static final String                   UPDATE = "biz/backstage/columnList/update";
    private static final String                   LIST   = "biz/backstage/columnList/list";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        List<Map<String, String>> columnList = new ArrayList<Map<String, String>>();
        for (ColumnListEnum col : ColumnListEnum.values()) {
            Map<String, String> columnMap = new HashMap<String, String>();
            columnMap.put("value", col.getValue());
            columnMap.put("name", col.getName());
            columnList.add(columnMap);
        }
        Map<String, String> typeMap = new HashMap<String, String>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(typeMap);
        typeMap.put("value", "HTML_A");
        typeMap.put("name", "超链接");
        Map<String, String> typeMap1 = new HashMap<String, String>();
        typeMap1.put("value", "HTML_FILE");
        typeMap1.put("name", "附件");
        list.add(typeMap1);
        map.put("columnMap", columnList);
        map.put("data", list);
        return CREATE;
    }

    @Log(message = "添加了columnList,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(BizColumnList columnList) {
        evenName = "栏目添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        Date date = DateUtil.getNowDate();
        columnList.setGmtCreate(date);
        columnList.setGmtModified(date);
        columnList.setShowPosition("COLUMN");
        columnListService.save(columnList);

        //记录日志
        LogUitl
            .putArgs(LogMessageObject.newWrite().setObjects(new Object[] { columnList.getId() }));
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizColumnList column = columnListService.get(id);
        map.put("columnList", column);
        List<Map<String, String>> columnList = new ArrayList<Map<String, String>>();
        for (ColumnListEnum col : ColumnListEnum.values()) {
            Map<String, String> columnMap = new HashMap<String, String>();
            columnMap.put("value", col.getValue());
            columnMap.put("name", col.getName());
            columnList.add(columnMap);
        }
        Map<String, String> typeMap = new HashMap<String, String>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(typeMap);
        typeMap.put("value", "HTML_A");
        typeMap.put("name", "超链接");
        Map<String, String> typeMap1 = new HashMap<String, String>();
        typeMap1.put("value", "HTML_FILE");
        typeMap1.put("name", "附件");
        list.add(typeMap1);
        map.put("columnMap", columnList);
        map.put("data", list);
        return UPDATE;
    }

    @Log(message = "修改了columnList,id:{0}。")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(BizColumnList columnList) {
        evenName = "栏目修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizColumnList origion = columnListService.get(columnList.getId());
        origion.setItemContent(columnList.getItemContent());
        origion.setItemContentType(columnList.getItemContentType());
        origion.setItemName(columnList.getItemName());
        origion.setItemType(columnList.getItemType());
        origion.setRank(columnList.getRank());
        origion.setGmtModified(DateUtil.getNowDate());
        columnListService.update(origion);
        //记录日志
        LogUitl
            .putArgs(LogMessageObject.newWrite().setObjects(new Object[] { columnList.getId() }));
        return ajaxObject.toString();
    }

    @Log(message = "删除了columnList,id:{0}。")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Long id) {
        evenName = "columnList删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizColumnList columnList = null;
        columnList = columnListService.get(id);
        columnListService.delete(id);
        //记录日志
        LogUitl
            .putArgs(LogMessageObject.newWrite().setObjects(new Object[] { columnList.getId() }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @Log(message = "删除了columnList,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "栏目删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] columnListTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizColumnList columnList = columnListService.get(ids[i]);
            columnListService.delete(columnList.getId());

            columnListTypes[i] = columnList.getId().toString();
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(columnListTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        String itemType = HttpReceiver.getHttpServletReqeuest().getParameter("itemType");
        String showPos = HttpReceiver.getHttpServletReqeuest().getParameter("showPos");
        List<BizColumnList> columnLists = null;
        if (StringUtils.isNotBlank(itemType)) {
            columnLists = columnListService.findByItemTypeAndShowPostion(itemType, showPos, page);
        } else {
            columnLists = columnListService.findAll(page);
        }
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        if (!CollectionUtils.isEmpty(columnLists)) {
            for (BizColumnList column : columnLists) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("id", column.getId() + "");
                tempMap.put("itemName", column.getItemName());
                String type = "";
                if (StringUtils.equals(column.getItemContentType(), "HTML_A")) {
                    type = "超链接";
                } else if (StringUtils.equals(column.getItemContentType(), "HTML_FILE")) {
                    type = "附件";
                }
                tempMap.put("type", type);
                tempMap.put("itemType", ColumnListEnum.getColumnListEnum(column.getItemType())
                    .getName());
                tempMap.put("rank", column.getRank() + "");
                dataList.add(tempMap);
            }
        }
        map.put("page", page);
        map.put("data", dataList);
        return LIST;
    }

    @RequestMapping(value = "/listAllWithC", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String listAllWithC() {
        List<BizColumnList> columnLists = columnListCacheManager.getPerm("");
        Map<String, List<Map<String, String>>> map = new TreeMap<String, List<Map<String, String>>>();
        Map<String, List<Map<String, String>>> finalMap = new LinkedMap<String, List<Map<String, String>>>();
        Gson gson = new Gson();
        if (!CollectionUtils.isEmpty(columnLists)) {
            for (BizColumnList column : columnLists) {
                String itemType = column.getItemType();
                List<Map<String, String>> list = null;
                if (null == (list = map.get(itemType))) {
                    list = new ArrayList<Map<String, String>>();
                    map.put(itemType, list);
                }
                if (list.size() < 5) {
                    Map<String, String> temp = new HashMap<String, String>();
                    temp.put("itemContent", column.getItemContent());
                    temp.put("itemContentType", column.getItemContentType());
                    temp.put("itemName", column.getItemName());
                    list.add(temp);
                }
            }
            for (String key : map.keySet()) {
                finalMap.put(ColumnListEnum.getColumnListEnum(key).getName(), map.get(key));
            }
        }
        return gson.toJson(finalMap);
    }
}
