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
import org.troy.platform.entity.PlatformMember;
import org.troy.platform.entity.PlatformMemberItemRelation;
import org.troy.platform.service.PlatformItemService;
import org.troy.platform.service.PlatformMemberItemRelationService;
import org.troy.platform.service.PlatformMemberService;
import org.troy.system.controller.ViewController;

@Controller
@RequestMapping("/platform/backstage/platformMemberItemRelation")
public class PlatformMemberItemRelationController extends ViewController {

    @Autowired
    private PlatformMemberItemRelationService platformMemberItemRelationService;

    @Autowired
    private PlatformMemberService             platformMemberService;

    @Autowired
    private PlatformItemService               platformItemService;

    private static final String               LIST   = "platform/backstage/platformMemberItemRelation/list";

    private static final String               CREATE = "platform/backstage/platformMemberItemRelation/create";
    private static final String               UPDATE = "platform/backstage/platformMemberItemRelation/update";

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        List<PlatformMember> list = platformMemberService.findAll();
        List<PlatformItem> items = platformItemService.findAll();
        map.put("data", list);
        map.put("items", items);
        return CREATE;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(PlatformMemberItemRelation platformMemberItemRelation) {
        evenName = "添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        platformMemberItemRelationService.save(platformMemberItemRelation);
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        PlatformMemberItemRelation platformMemberItemRelation = platformMemberItemRelationService
            .findById(id);
        Map<String, String> tempMap = new HashMap<String, String>();
        tempMap.put("id", platformMemberItemRelation.getId() + "");
        tempMap.put("itemId", platformMemberItemRelation.getItemId() + "");
        PlatformItem item = platformItemService.findById(platformMemberItemRelation.getItemId());
        if (null != item) {
            tempMap.put("itemName", item.getName());
        }
        PlatformMember member = platformMemberService.findById(platformMemberItemRelation.getMemberId());
        if (null != member) {
            tempMap.put("memberName", member.getName());
        }
        tempMap.put("memberId", platformMemberItemRelation.getMemberId() + "");
        tempMap.put("itemNum", platformMemberItemRelation.getItemNum() + "");
        map.put("obj", tempMap);
        return UPDATE;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(PlatformMemberItemRelation platformMemberItemRelation,
                                       HttpServletRequest request) {
        evenName = "修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        platformMemberItemRelationService.update(platformMemberItemRelation);
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();

        List<PlatformMemberItemRelation> list = platformMemberItemRelationService.findAll();
        for (int i = 0; i < list.size(); i++) {
            PlatformMemberItemRelation relation = list.get(i);
            Map<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("id", relation.getId() + "");
            tempMap.put("itemId", relation.getItemId() + "");
            PlatformItem item = platformItemService.findById(relation.getItemId());
            if (null != item) {
                tempMap.put("itemName", item.getName());
            }
            PlatformMember member = platformMemberService.findById(relation.getMemberId());
            if (null != member) {
                tempMap.put("memberName", member.getName());
            }
            tempMap.put("memberId", relation.getMemberId() + "");
            tempMap.put("itemNum", relation.getItemNum() + "");
            mapList.add(tempMap);
        }
        map.put("page", page);
        map.put("data", mapList);
        return LIST;
    }
    
    @RequestMapping(value = "/delete", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String delete(Long[] ids) {
        evenName = "删除 ";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            List<Long> longIds = new ArrayList<Long>();
            for (Long id : ids) {
                longIds.add(id);
            }
            platformMemberItemRelationService.deleteAll(longIds);
            ajaxObject.setMessage(evenName + "成功！");
        } catch (Exception e) {
            ajaxObject.setMessage(evenName + "失败！");
        }
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

}