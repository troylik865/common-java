package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 栏目相关的枚举
 * 
 * @author troy
 * @version $Id: ColumnListEnum.java, v 0.1 2014年7月13日 下午12:57:53 troy Exp $
 */
public enum ColumnListEnum {

    QH_GS("期货公司", "a-qhgs"),

    ZQ_GS("证券公司", "b-zqgs"),

    FX_ZB("分析指标", "c-fxzb"),

    HY_SJ("行业数据", "d-hysj"),

    TZ_SJ("投资书籍", "e-tzsj"),

    JX_SP("教学视频", "f-jxsp"),

    YH_GW("银行官网", "g-yhgw"),

    DEFAULT("其他", "h-other");

    private String value;

    private String name;

    ColumnListEnum(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public static ColumnListEnum getColumnListEnum(String value) {
        for (ColumnListEnum single : ColumnListEnum.values()) {
            if (StringUtils.equals(single.getValue(), value)) {
                return single;
            }
        }
        return DEFAULT;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
