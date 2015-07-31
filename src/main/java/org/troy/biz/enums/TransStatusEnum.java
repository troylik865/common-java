/**
 * YY.com Inc.
 * Copyright (c) 2011-2014 All Rights Reserved.
 */
package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;
import org.troy.common.utils.enums.BaseEnum;

/**
 * 
 * @author helloKitty
 * @version $Id: TransStatusEnum.java, v 0.1 2014-8-13 下午11:31:31 helloKitty Exp $
 */
public enum TransStatusEnum implements BaseEnum<String> {

    I("已提交，待审核", "I"),

    S("通过", "S"),

    F("不通过", "F");

    private String value;

    private String name;

    TransStatusEnum(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public static TransStatusEnum getEnum(String value) {
        for (TransStatusEnum single : TransStatusEnum.values()) {
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

    @Override
    public String getDesc() {
        // TODO Auto-generated method stub
        return null;
    }

}
