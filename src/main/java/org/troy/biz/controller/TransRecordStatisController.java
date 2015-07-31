package org.troy.biz.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.biz.service.TransRecordStatisService;
import org.troy.common.entity.AjaxReturnInfo;
import org.troy.system.controller.ViewController;

/**
 * 
 * 交易记录对应的统计信息的控制器
 *
 *
 * @author siren.lb
 * @version $Id: TransRecordStatisController.java,v 0.1 2014年7月24日 上午10:12:05 siren.lb Exp $
 */
@Controller
@RequestMapping("/biz/transRecordStatis")
public class TransRecordStatisController extends ViewController {
    
    @Autowired
    private TransRecordStatisService transRecordStatisService;

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public @ResponseBody String show(HttpServletRequest request) {
        return AjaxReturnInfo.returnSuc();
    }
}
