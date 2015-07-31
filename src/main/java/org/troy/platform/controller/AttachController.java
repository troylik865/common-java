package org.troy.platform.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.troy.biz.entity.BizAttach;
import org.troy.biz.enums.AttachTypeEnum;
import org.troy.biz.enums.FinanceEnum;
import org.troy.biz.service.AttachService;
import org.troy.biz.util.MultipartFileUtil;
import org.troy.common.component.log.Log;
import org.troy.common.component.log.LogMessageObject;
import org.troy.common.component.log.impl.LogUitl;
import org.troy.common.entity.AjaxReturnInfo;
import org.troy.common.utils.dwz.AjaxObject;
import org.troy.common.utils.dwz.Page;
import org.troy.common.utils.dwz.springdata.PageUtils;
import org.troy.system.controller.ViewController;

/***
 * <p>Title:AttachController </p> 
 *
 * <p>Description:Attach 控制层 </p> 
 *
 * <p>Author:jiangb </p> 
 *
 * <p>Copyright: Copyright (c) 2014 </p>
 * 
 * <p>CreateDate: 2014-06-23 22:17 </p>
 *
 */
@Controller
@RequestMapping("/biz/attach")
public class AttachController extends ViewController {

    @Autowired
    private AttachService       attachService;

    private static final String CREATE = "biz/attach/create";

    private static final String UPDATE = "biz/attach/update";

    private static final String SHOW   = "biz/attach/show";

    @RequiresPermissions("Attach:save")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate(Map<String, Object> map) {
        return CREATE;
    }

    @Log(message = "添加了attach,id:{0}。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody String create(BizAttach attach) {
        evenName = "attach添加";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        attachService.save(attach);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { attach.getId() }));
        return ajaxObject.toString();
    }

    @RequiresPermissions("Attach:edit")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        BizAttach attach = attachService.get(id);
        map.put("attach", attach);
        return UPDATE;
    }

    @Log(message = "修改了attach,id:{0}。")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(BizAttach attach) {
        evenName = "attach修改";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");

        attachService.update(attach);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { attach.getId() }));
        return ajaxObject.toString();
    }

    @Log(message = "删除了attach,id:{0}。")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Long id) {
        evenName = "attach删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        BizAttach attach = null;
        attach = attachService.get(id);
        attachService.delete(id);
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { attach.getId() }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Map<String, Object> map) {
        List<BizAttach> bizAttachs = attachService.findAttachByAttachTypeAndRefId(
            AttachTypeEnum.COLUMN.getValue(), id);
        if (!CollectionUtils.isEmpty(bizAttachs)) {
            BizAttach bizAttach = bizAttachs.get(0);
            if (null != bizAttach) {
                map.put("name", bizAttach.getAttachRealName());
                map.put("attachId", bizAttach.getId() + "");
                map.put("desc", bizAttach.getExtendField());
            } else {
                map.put("name", "附加不存在！");
            }
        } else {
            map.put("name", "附加不存在！");
        }
        return SHOW;
    }

    @Log(message = "删除了attach,ids:{0}。")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String deleteMany(Long[] ids) {
        evenName = "attach删除";
        AjaxObject ajaxObject = new AjaxObject(evenName + "成功！");
        String[] attachTypes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            BizAttach attach = attachService.get(ids[i]);
            attachService.delete(attach.getId());

            attachTypes[i] = attach.getId().toString();
        }
        //记录日志
        LogUitl.putArgs(LogMessageObject.newWrite().setObjects(
            new Object[] { Arrays.toString(attachTypes) }));
        ajaxObject.setCallbackType("");
        return ajaxObject.toString();
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String list(Page page, Map<String, Object> map, HttpServletRequest request) {
        String transRecordIds = request.getParameter("transRecordIds");
        String type = request.getParameter("type");
        List<BizAttach> attachs = null;
        List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
        if (StringUtils.isNotBlank(transRecordIds)) {
            List<Long> ids = new ArrayList<Long>();
            for (String str : StringUtils.split(transRecordIds, ",")) {
                ids.add(Long.parseLong(str));
            }
            attachs = attachService.findByTransRecordIdsAndAttachTypeOrderByGmtCreateDesc(page,
                ids, type);
            for (BizAttach attach : attachs) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("fileId", attach.getId().toString());
                temp.add(tempMap);
            }
        }
        return PageUtils.toJsonString(page, temp);
    }

    /**
     * 下载文件
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/download")
    public ModelAndView downloadFile(HttpServletRequest request, HttpServletResponse response)
                                                                                              throws IOException {
        String fileId = request.getParameter("fileId");
        BizAttach attach = attachService.get(Long.valueOf(fileId));
        if (null == attach) {
            return null;
        }
        File file = attachService.getAttachFile(attach.getAttachPath());
        response.setContentType("application/octet-stream");
        response.setContentLength((int) attach.getAttachSize());
        String userAgent = request.getHeader("user-agent");
        response.setHeader("Content-Disposition",
            "attachment;filename="
                    + convertFilenameByBrowser(userAgent, attach.getAttachRealName()));
        try {
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/upload", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody String upload(HttpServletRequest request) {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        String bizId = request.getParameter("bizId");
        String type = request.getParameter("type");
        String cost = request.getParameter("cost");
        String desc = request.getParameter("desc");
        Map<String, MultipartFile> fileMap = req.getFileMap();
        List<BizAttach> attachs = MultipartFileUtil.convertFileInfo(fileMap.values(),
            AttachTypeEnum.getAttachTypeEnum(type), FinanceEnum.getFinanceEnum(cost).getValue(),
            Long.parseLong(StringUtils.isBlank(bizId) ? "0" : bizId));
        if (CollectionUtils.isNotEmpty(attachs)) {
            for (BizAttach attach : attachs) {
                attach.setExtendField(desc);
            }
            attachService.save(attachs);
        }
        return AjaxReturnInfo.returnSuc();
    }

    /**
     * Convert filename to appropriated filename in HTTP header when download file
     * according to the browser by user agent in request.
     * 
     * @param userAgent user agent, never be null
     * @param srcName source filename
     * @return converted filename
     */
    public static String convertFilenameByBrowser(String userAgent, String srcName) {
        try {
            if (userAgent.toUpperCase().indexOf("MSIE") >= 0) {
                return URLEncoder.encode(srcName, "UTF-8");
            } else if (userAgent.toUpperCase().indexOf("APPLEWEBKIT") >= 0) {
                return MimeUtility.encodeText(srcName, "UTF8", "B");
            } else if (userAgent.toUpperCase().indexOf("MOZILLA") >= 0) {
                return "=?UTF-8?B?" + (new String(Base64.encodeBase64(srcName.getBytes("UTF-8"))))
                       + "?=";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return srcName;
    }

}
