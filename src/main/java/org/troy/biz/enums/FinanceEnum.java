package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 资金相关的枚举
 * 
 * @author troy
 * @version $Id: FinanceEnum.java, v 0.1 2014年6月16日 下午11:09:59 troy Exp $
 */
public enum FinanceEnum {

    GOLD("金币", "gold"),

    MONEY("钞票", "money"),

    SCORE("积分", "score");

    private String value;

    private String name;

    FinanceEnum(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public static FinanceEnum getFinanceEnum(String value) {
        for (FinanceEnum single : FinanceEnum.values()) {
            if (StringUtils.equals(single.getValue(), value)) {
                return single;
            }
        }
        return GOLD;
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
