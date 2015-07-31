package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;
import org.troy.common.utils.enums.BaseEnum;

/**
 * 性别
 * 
 * @author troy
 * @version $Id: SexEnum.java, v 0.1 2014年6月16日 下午7:44:21 troy Exp $
 */
public enum SexEnum implements BaseEnum<String> {

    MALE("男", "1"),

    FEMALE("女", "0"),

    UNKNOW("未知", "2"),

    ALL("雌雄同体", "3");

    private String value;

    private String name;

    SexEnum(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public static SexEnum getEnum(String value) {
        for (SexEnum single : SexEnum.values()) {
            if (StringUtils.equals(single.getValue(), value)) {
                return single;
            }
        }
        return UNKNOW;
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

    @Override
    public String getDesc() {
        // TODO Auto-generated method stub
        return null;
    }

}
