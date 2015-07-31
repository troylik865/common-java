package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 交易用途对应的枚举
 * 
 * @author troy
 * @version $Id: TransUserEnum.java, v 0.1 2014年7月19日 上午11:51:29 troy Exp $
 */
public enum TransUserEnum {

    MEMBER_ATTENT("大师关注", "member_attent"),
    
    MEMBER_REGIST("会员注册", "member_regist"),

    MEMBER_ATTENT_PROLONG("大师关注延长", "member_attent_prolong"),

    MEMBER_RECHARGE("资金充值", "member_recharge"),

    OTHER("其他类型", "other");

    private String value;

    private String name;

    TransUserEnum(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public static TransUserEnum getEnum(String value) {
        for (TransUserEnum single : TransUserEnum.values()) {
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
