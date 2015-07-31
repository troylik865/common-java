package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 投资方向的枚举
 * 
 * @author troy
 * @version $Id: InvestDirectionEnum.java, v 0.1 2014年6月16日 下午7:11:13 troy Exp $
 */
public enum InvestDirectionEnum {

    QI_HUO("期货", "qh"),

    GU_PIAO("股票", "gp"),

    WAI_HUI("外汇", "wh"),

    HUANG_JIN("贵金属", "hj"),

    BAI_YIN("模拟区", "by"),

    DEFAULT("股票", "gp");

    private String value;

    private String name;

    InvestDirectionEnum(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public static InvestDirectionEnum getInvestDirectionEnum(String value) {
        for (InvestDirectionEnum single : InvestDirectionEnum.values()) {
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
