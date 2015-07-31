/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package org.troy.biz.entity.vo;

/**
 * form表单文件VO
 *
 * <p>form表单文件VO</p>
 *
 * @author wb-chenyy@alipay.com
 * @version $Id: MultipartFileInfo.java,v 0.1 2014-7-7 下午01:28:45 wb-chenyy Exp $
 */
public class MultipartFileInfo {

    /** 文件内容*/
    private byte[] content;

    /** 文件名称*/
    private String name;

    /** 真实的文件名称*/
    private String realName;

    /** 文件大小*/
    private int    size;

    /** 业务Id*/
    private int    bizId;

    /** 花费的类型*/
    private String costType;

    /** 主要是福建关联的业务类型*/
    private String attachType;

    /** 对应的花费值*/
    private String value;

    /**
     * Getter method for property <tt>content</tt>.
     * 
     * @return property value of content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Setter method for property <tt>content</tt>.
     * 
     * @param content value to be assigned to property content
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>realName</tt>.
     * 
     * @return property value of realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Setter method for property <tt>realName</tt>.
     * 
     * @param realName value to be assigned to property realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * Getter method for property <tt>size</tt>.
     * 
     * @return property value of size
     */
    public int getSize() {
        return size;
    }

    /**
     * Setter method for property <tt>size</tt>.
     * 
     * @param size value to be assigned to property size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Getter method for property <tt>bizId</tt>.
     * 
     * @return property value of bizId
     */
    public int getBizId() {
        return bizId;
    }

    /**
     * Setter method for property <tt>bizId</tt>.
     * 
     * @param bizId value to be assigned to property bizId
     */
    public void setBizId(int bizId) {
        this.bizId = bizId;
    }

    /**
     * Getter method for property <tt>costType</tt>.
     * 
     * @return property value of costType
     */
    public String getCostType() {
        return costType;
    }

    /**
     * Setter method for property <tt>costType</tt>.
     * 
     * @param costType value to be assigned to property costType
     */
    public void setCostType(String costType) {
        this.costType = costType;
    }

    /**
     * Getter method for property <tt>attachType</tt>.
     * 
     * @return property value of attachType
     */
    public String getAttachType() {
        return attachType;
    }

    /**
     * Setter method for property <tt>attachType</tt>.
     * 
     * @param attachType value to be assigned to property attachType
     */
    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }

    /**
     * Getter method for property <tt>value</tt>.
     * 
     * @return property value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     * 
     * @param value value to be assigned to property value
     */
    public void setValue(String value) {
        this.value = value;
    }

}
