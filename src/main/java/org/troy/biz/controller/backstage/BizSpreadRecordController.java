package org.troy.biz.controller.backstage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizSpreadRecord;
import org.troy.biz.service.MemberService;
import org.troy.biz.service.SpreadRecordService;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

/**
 * 
 * 推广链接
 *
 * @author siren.lb
 * @version $Id: BizSpreadRecordController.java,v 0.1 2014年9月17日 下午10:18:29 siren.lb Exp $
 */
@Controller
@RequestMapping("/biz/backstage/spreadRecord")
public class BizSpreadRecordController extends ViewController {

    @Autowired
    private MemberService       memberService;

    @Autowired
    private SpreadRecordService spreadRecordService;

    private static final String LIST = "biz/backstage/spreadRecord/list";

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        String name = HttpReceiver.getParameter("name");
        List<BizSpreadRecord> list = null;
        if (StringUtils.isBlank(name)) {
            list = spreadRecordService.findAll(page);
        } else {
            List<BizMember> m = memberService.getBizMemberByName(name);
            if (!CollectionUtils.isEmpty(m)) {
                List<Long> ids = new ArrayList<Long>();
                for (BizMember mem : m) {
                    ids.add(mem.getId());
                }
                list = spreadRecordService.findBySpreadMemberIds(page, ids);
            }
        }
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        if (!CollectionUtils.isEmpty(list)) {
            for (BizSpreadRecord record : list) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("id", record.getId() + "");
                BizMember mem = memberService.get(record.getSpreadMemberId());
                tempMap.put("name", (null == mem) ? "该会员已经删除！" : mem.getName());
                BizMember mem2 = memberService.get(record.getRegistMemberId());
                tempMap.put("beName", (null == mem2) ? "该会员已经删除！" : mem2.getName());
                tempMap.put("gmtCreate",
                    DateUtil.date2String(record.getGmtCreate(), DateUtil.PATTERN_STANDARD));
                data.add(tempMap);
            }
        }
        map.put("page", page);
        map.put("data", data);
        return LIST;
    }
}
