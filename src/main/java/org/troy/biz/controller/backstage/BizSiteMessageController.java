package org.troy.biz.controller.backstage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.constant.BizConstant;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizSiteMessage;
import org.troy.biz.enums.MsgStatusEnum;
import org.troy.biz.enums.SiteMessageTypeEnum;
import org.troy.biz.service.MemberService;
import org.troy.biz.service.SiteMessageService;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

/**
 * 
 * 站内信
 *
 * @author siren.lb
 * @version $Id: BizSiteMessageController.java,v 0.1 2014年7月31日 上午10:46:10 siren.lb Exp $
 */
@Controller
@RequestMapping("/biz/backstage/siteMessage")
public class BizSiteMessageController extends ViewController {

    @Autowired
    private MemberService       memberService;

    @Autowired
    private SiteMessageService  siteMessageService;

    private static final String LIST = "biz/backstage/siteMessage/list";

    private static final String SHOW = "biz/backstage/siteMessage/show";

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map, BizMember member) {
        BizMember admin = memberService.getAdminAccount();
        if (null == admin) {
            return LIST;
        }
        String type = HttpReceiver.getHttpServletReqeuest().getParameter("keywords");
        List<BizSiteMessage> list = null;
        if (StringUtils.isBlank(type)) {
            list = siteMessageService.findAll(page);
        } else {
            list = siteMessageService.findByMessageType(page, type);
        }
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (BizSiteMessage message : list) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap
                    .put("type", SiteMessageTypeEnum.getEnum(message.getMessageType()).getName());
                BizMember tempMem = memberService.get(message.getMemberId());
                tempMap.put("id", message.getId() + "");
                tempMap.put("name", (null == tempMem) ? "该用户已经被删除!" : tempMem.getName());
                tempMap.put("content", message.getMessageContent());
                tempMap.put("status", MsgStatusEnum.getMsgUseEnum(message.getStatus()).getName());
                tempMap.put("time",
                    DateUtil.date2String(message.getGmtCreate(), BizConstant.DATE_TIME_FORMAT));
                mapList.add(tempMap);
            }
        }
        List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
        for (SiteMessageTypeEnum enu : SiteMessageTypeEnum.values()) {
            Map<String, String> temp = new HashMap<String, String>();
            temp.put("name", enu.getName());
            temp.put("value", enu.getValue());
            tempList.add(temp);
        }
        map.put("types", tempList);
        map.put("page", page);
        map.put("data", mapList);
        return LIST;
    }

    @RequestMapping(value = "/show/{id}", method = { RequestMethod.POST, RequestMethod.GET })
    public String approval(@PathVariable Long id, Map<String, Object> map) {
        BizSiteMessage siteMessage = siteMessageService.get(id);
        if (null == siteMessage) {
            return SHOW;
        }
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("content", siteMessage.getMessageContent());
        dataMap.put("status", MsgStatusEnum.getMsgUseEnum(siteMessage.getStatus()).getName());
        dataMap.put("id", siteMessage.getId() + "");
        dataMap.put("type", SiteMessageTypeEnum.getEnum(siteMessage.getMessageType()).getName());
        dataMap.put("createTime",
            DateUtil.date2String(siteMessage.getGmtCreate(), BizConstant.DATE_TIME_FORMAT));
        BizMember mem = memberService.get(siteMessage.getMemberId());
        if (null != mem) {
            dataMap.put("name", (null == mem) ? "该用户已经被删除！" : mem.getName());
        }
        map.put("data", dataMap);
        return SHOW;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "站内信删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            for (Long l : ids) {
                siteMessageService.delete(l);
            }
        } catch (Exception e) {
            ajaxObject = new AjaxObject(evenName + "失败！");
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(ids) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }
}
