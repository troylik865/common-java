package org.troy.biz.controller.backstage;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.entity.BizMember;
import org.troy.biz.entity.BizNews;
import org.troy.biz.service.NewsService;
import org.troy.biz.util.SpreadMemberUtil;
import org.troy.biz.work.util.HttpReceiver;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.entity.AjaxReturnInfo;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

/**
 * 
 * 新闻相关的后台
 *
 * @author siren.lb
 * @version $Id: BizNewsController.java,v 0.1 2014年9月7日 上午1:41:29 siren.lb Exp $
 */
@Controller
@RequestMapping("/biz/backstage/news")
public class BizNewsController extends ViewController {

    private static final String LIST   = "/biz/backstage/news/list";

    private static final String CREATE = "biz/backstage/news/create";
    private static final String UPDATE = "biz/backstage/news/update";

    @Autowired
    private NewsService         newsService;

    @RequestMapping(value = "/showLink", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String showLink(Page page, Map<String, Object> map) {
        BizMember mem = HttpReceiver.getCurrentMember();
        String link = "http://121.40.94.201:8080/index?key="
                      + SpreadMemberUtil.getSecByMemberId(mem.getId());
        return AjaxReturnInfo.returnSuc(link);
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map, BizMember member) {
        List<BizNews> list = newsService.findByGmtCreateIsNotNullOrderByTurnAsc(page);
        map.put("page", page);
        map.put("news", list);
        return LIST;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(BizNews bizNews) {
        evenName = "新闻添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            Date date = DateUtil.getNowDate();
            bizNews.setGmtCreate(date);
            bizNews.setGmtModified(date);
            newsService.save(bizNews);
        } catch (Exception e) {
            ajaxObject.setMessage(evenName + "失败！");
        }
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizNews news = newsService.get(id);
        map.put("news", news);
        return UPDATE;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(BizNews bizNews) {
        evenName = "修改新闻";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            bizNews.setGmtCreate(DateUtil.getNowDate());
            newsService.update(bizNews);
        } catch (Exception e) {
            ajaxObject = new AjaxObject(evenName + "失败！");
        }
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "新闻删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            for (Long l : ids) {
                newsService.delete(l);
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
