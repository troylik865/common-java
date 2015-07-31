/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package org.troy.biz.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.util.CollectionUtils;
import org.aspectj.util.FileUtil;
import org.springframework.web.multipart.MultipartFile;
import org.troy.biz.entity.BizAttach;
import org.troy.biz.enums.AttachTypeEnum;
import org.troy.biz.enums.CostTypeEnum;
import org.troy.common.utils.DateUtil;

/**
 * MultipartFile工具
 *
 * <p>MultipartFile,都是静态方法</p>
 *
 * @author wb-chenyy@alipay.com
 * @version $Id: MultipartFileUtil.java,v 0.1 2014-7-7 下午01:30:37 wb-chenyy Exp $
 */
public class MultipartFileUtil {

    private static String FILE_PATH      = "C:\\filePath\\";

    private static String MONTH_TAG      = "yyyyMM";

    private static String FILE_SEPARATOR = File.separator;

    /**
     * 
     * 
     * @param multipartFile
     * @param bizType
     * @param bizId
     * @return
     */
    public static List<BizAttach> convertFileInfo(MultipartFile[] multipartFile,
                                                  AttachTypeEnum attachType, CostTypeEnum costType,
                                                  long bizId) {
        if (null == multipartFile || multipartFile.length == 0) {
            return null;
        }
        List<BizAttach> attachList = new ArrayList<BizAttach>(multipartFile.length);
        Date currentDate = new Date();
        try {
            for (MultipartFile myfile : multipartFile) {
                //1、初始化attach基本参数
                BizAttach attach = new BizAttach();
                attach.setRefId((Long) bizId);
                attach.setAttachSize((int) myfile.getSize());
                //文件展示的名称
                attach.setAttachName(myfile.getName());
                attach.setAttachRealName(myfile.getOriginalFilename());
                attach.setAttachType(attachType.getValue());
                attach.setCostType(costType.getValue());
                attach.setValue((long) 0);
                attach.setGmtCreate(currentDate);
                attach.setGmtModified(currentDate);
                //2、初始化系统文件部分参数
                String attachFilePath = newFileAttachPath(attachType);
                File file = createFile(myfile, attachFilePath);
                attach.setAttachName(file.getName());
                attach.setAttachPath(attachFilePath);
                FileUtil.copyStream(myfile.getInputStream(), FileUtils.openOutputStream(file));
                attachList.add(attach);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attachList;
    }

    /**
     * 
     * 
     * @param multipartFile     文件列表
     * @param attachType        附件类型
     * @param costType           花费的资金类型：主要是用于下载附件时候的定价
     * @param bizId                业务主键
     * @return
     */
    public static List<BizAttach> convertFileInfo(Collection<MultipartFile> multipartFile,
                                                  AttachTypeEnum attachType, String costType,
                                                  long bizId) {
        if (CollectionUtils.isEmpty(multipartFile)) {
            return null;
        }
        List<BizAttach> attachList = new ArrayList<BizAttach>(multipartFile.size());
        Date currentDate = new Date();
        try {
            for (MultipartFile myfile : multipartFile) {
                //1、初始化attach基本参数
                BizAttach attach = new BizAttach();
                attach.setRefId((Long) bizId);
                attach.setAttachSize((int) myfile.getSize());
                //文件展示的名称
                attach.setAttachName(myfile.getName());
                attach.setAttachRealName(myfile.getOriginalFilename());
                attach.setAttachType(attachType.getValue());
                attach.setCostType(costType);
                attach.setValue((long) 0);
                attach.setGmtCreate(currentDate);
                attach.setGmtModified(currentDate);
                //2、初始化系统文件部分参数
                String attachFilePath = newFileAttachPath(attachType);
                File file = createFile(myfile, attachFilePath);
                attach.setAttachName(file.getName());
                attach.setAttachPath(attachFilePath);
                FileUtil.copyStream(myfile.getInputStream(), FileUtils.openOutputStream(file));
                attachList.add(attach);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attachList;
    }

    /**
     * 获取下载文件
     * 
     * @param path
     * @return
     */
    public static File getAttachFileByPath(String attachFilePath) {
        String filePath = FILE_PATH + FILE_SEPARATOR + attachFilePath;
        return new File(filePath);
    }

    /**
     * 获取新建附件的相对路径
     * 
     * @param attachType
     * @return
     */
    private static String newFileAttachPath(AttachTypeEnum attachType) {
        return attachType.getValue() + FILE_SEPARATOR + DateUtil.getCurrentTime(MONTH_TAG)
               + FILE_SEPARATOR + getUUIDFileName();
    }

    /**
     * 创建文件
     * 
     * @param myfile
     * @return
     * @throws IOException 
     */
    private static File createFile(MultipartFile myfile, String attachFilePath) throws IOException {
        File file = new File(FILE_PATH + FILE_SEPARATOR + attachFilePath);
        FileUtils.forceMkdir(file.getParentFile());
        file.createNewFile();
        return file;
    }

    /**
     * 获取文件存放名称，不是真正展示的文件名称
     * 
     * @return
     */
    private static String getUUIDFileName() {
        return UUID.randomUUID().toString();
    }

    public static void main(String args[]) {
        String path = FILE_PATH + FILE_SEPARATOR
                      + AttachTypeEnum.FINANCIAL_INTERESTS_AND_POSITIONS_PIC1.getValue()
                      + FILE_SEPARATOR + DateUtil.getCurrentTime(MONTH_TAG) + FILE_SEPARATOR;

        System.out.println(path);
    }

}
