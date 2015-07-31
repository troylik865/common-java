package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 *短信用途
 *
 * @author siren.lb
 * @version $Id: MsgUseEnum.java,v 0.1 2014年7月29日 下午12:16:44 siren.lb Exp $
 */
public enum MsgUseEnum {

    REGIST("会员注册", "regist"),

    RESEC("密码重置", "resec");

    private String value;

    private String name;

    MsgUseEnum(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public static MsgUseEnum getMsgUseEnum(String value) {
        for (MsgUseEnum single : MsgUseEnum.values()) {
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
