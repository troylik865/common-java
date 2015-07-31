package org.troy.biz.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.troy.common.utils.ValidateCodeUtil;
import org.troy.system.controller.ViewController;

/**
 * 生成验证码
 * 
 * @author troy
 * @version $Id: ValidController.java, v 0.1 2014年6月26日 下午4:43:46 troy Exp $
 */
@Controller
@RequestMapping("/biz")
public class ValidController extends ViewController {

    @RequestMapping(value = "/validate")
    public void validate(HttpServletResponse response) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            session.setAttribute(session.getId(),
                ValidateCodeUtil.getCertPic(70, 20, response.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
