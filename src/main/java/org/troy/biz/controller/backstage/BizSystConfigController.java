package org.troy.biz.controller.backstage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.entity.BizSysConfig;
import org.troy.biz.service.BizSysConfigService;
import org.troy.common.utils.DateUtil;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.system.controller.ViewController;

/**
 * 网站首页的信息展示
 *
 * @author siren.lb
 * @version $Id: BizSystConfigController.java,v 0.1 2014年9月26日 下午5:40:58 siren.lb Exp $
 */
@Controller
@RequestMapping("/biz/backstage/sysConfig")
public class BizSystConfigController extends ViewController {

    @Autowired
    private BizSysConfigService bizSysConfigService;

    private static final String LIST = "biz/backstage/sysConfig/list";

    private static final String SHOW = "biz/backstage/sysConfig/show";

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String updatey(HttpServletRequest request) {
        evenName = "站内配置修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        try {
            List<BizSysConfig> list = bizSysConfigService.findByType("site");
            String headPhone1 = request.getParameter("headPhone1");
            String headPhone2 = request.getParameter("headPhone2");
            String footPhone = request.getParameter("footPhone");
            String qq = request.getParameter("qq");
            String tel = request.getParameter("tel");
            Date date = DateUtil.getNowDate();
            for (BizSysConfig conf : list) {
                if (StringUtils.equals("headPhone1", conf.getParamName())) {
                    conf.setParamValue(headPhone1);
                    conf.setGmtModified(date);
                } else if (StringUtils.equals("headPhone2", conf.getParamName())) {
                    conf.setParamValue(headPhone2);
                    conf.setGmtModified(date);
                } else if (StringUtils.equals("footPhone", conf.getParamName())) {
                    conf.setParamValue(footPhone);
                    conf.setGmtModified(date);
                } else if (StringUtils.equals("qq", conf.getParamName())) {
                    conf.setParamValue(qq);
                    conf.setGmtModified(date);
                } else if (StringUtils.equals("tel", conf.getParamName())) {
                    conf.setParamValue(tel);
                    conf.setGmtModified(date);
                }
                bizSysConfigService.update(conf);
            }
        } catch (Exception e) {
            ajaxObject = new AjaxObject(evenName + "失败！");
        }
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updatey(Long[] ids) {
        evenName = "站内配置修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/show")
    public String show(Map<String, Object> map) {
        evenName = "站内配置修改";
        List<BizSysConfig> list = bizSysConfigService.findByType("site");
        for (BizSysConfig config : list) {
            map.put(config.getParamName(), config.getParamValue());
        }
        return SHOW;
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Page page, Map<String, Object> map) {
        return LIST;
    }

}
