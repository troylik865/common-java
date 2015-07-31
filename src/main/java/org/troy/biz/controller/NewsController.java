package org.troy.biz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.entity.BizNews;
import org.troy.biz.service.MemberService;
import org.troy.biz.service.NewsService;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.controller.ViewController;

/**
 * 新闻相关的controller 
 *
 * @author siren.lb
 * @version $Id: NewsController.java,v 0.1 2014年9月7日 上午12:36:40 siren.lb Exp $
 */
@Controller
@RequestMapping("/biz/news")
public class NewsController extends ViewController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private NewsService   newsService;
    

    private static final String         SHOW        = "biz/news/show";

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String listTop(Page page, Map<String, Object> map) {
        List<BizNews> news = newsService.findByGmtCreateIsNotNullOrderByTurnAsc(page);
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        if (!CollectionUtils.isEmpty(news)) {
            for (BizNews ne : news) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("title", ne.getTitle());
                tempMap.put("id", ne.getId() + "");
                mapList.add(tempMap);
            }
        }
        return PageUtils.toJsonString(page, mapList);
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Map<String, Object> map) {
        BizNews news = newsService.get(id);
        map.put("news", news);
        return SHOW;
    }
}
