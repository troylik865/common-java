package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 站内信类型相关的枚举
 * 
 * @author troy
 * @version $Id: SiteMessageTypeEnum.java, v 0.1 2014年7月19日 下午6:20:46 troy Exp $
 */
public enum SiteMessageTypeEnum {

    MEMBER_RANK("大师排行", "rank", "会员${}申请出现在大师排行区域！"),

    MEMBER_REPORT("大师举报", "report", "大师${}被举报！举报内容："),

    MEMBER_APPLY("会员参赛申请", "apply", "会员${}申请参赛！");

    private String value;

    private String name;

    private String desc;

    SiteMessageTypeEnum(String name, String value, String desc) {
        this.value = value;
        this.name = name;
        this.setDesc(desc);
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public static SiteMessageTypeEnum getEnum(String value) {
        for (SiteMessageTypeEnum single : SiteMessageTypeEnum.values()) {
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
