package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 交易用途对应的枚举
 * 
 * @author troy
 * @version $Id: TransUserEnum.java, v 0.1 2014年7月19日 上午11:51:29 troy Exp $
 */
public enum TransTypeEnum {

    OUT("支出", "out"),

    IN("收入", "in");

    private String value;

    private String name;

    TransTypeEnum(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public static TransTypeEnum getEnum(String value) {
        for (TransTypeEnum single : TransTypeEnum.values()) {
            if (StringUtils.equals(single.getValue(), value)) {
                return single;
            }
        }
        return null;
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
