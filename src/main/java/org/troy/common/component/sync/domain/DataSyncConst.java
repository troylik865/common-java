package org.troy.common.component.sync.domain;

/**
 * 数据同步模块相关常量
 * @author 杨磊
 * @since 2014-4-24 下午6:30:29
 * Copyright © 2013 - 2014 CNNCT
 */
public class DataSyncConst {
    /**
     * 上行队列(后台-->支付宝公众平台)
     */
    public static final String UPSTREAM_QUEUE= "data_sync_upstream";
    /**
     * 下行队列(支付宝公众平台-->后台)
     */
    public static final String DOWNSTREAM_QUEUE= "data_sync_downstream";
    /**
     * dml操作类型update
     */
    public static final String DML_UPDATE = "update";
    /**
     * dml操作类型insert
     */
    public static final String DML_INSERT = "insert";
    /**
     * dml操作类型delete
     */
    public static final String DML_DELETE = "delete";
    /**
     * 0:请求处理失败
     */
    public static final int ACK_FAIL = 0;
    /**
     * 1:请求处理成功
     */
    public static final int ACK_OK = 1;
    /**
     * 2:请求处理中
     */
    public static final int ACK_PROCESSING = 2;
    /**
     * 最大重发次数
     */
    public static final int MAX_REPEAT_NUM = 3;
    /**
     * 公众平台第三方支付id
     */
    public static final String ALIPAY_SOURCE = "003";
}
