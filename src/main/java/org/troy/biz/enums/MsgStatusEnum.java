package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 站内信状态
 *
 *
 * @author siren.lb
 * @version $Id: MsgStatusEnum.java,v 0.1 2014年7月30日 上午10:30:53 siren.lb Exp $
 */
public enum MsgStatusEnum {

    I("待审核", "0"), P("审核中", "1"), S("审核通过", "2"), T("审核被驳回", "3");

    private String value;

    private String name;

    MsgStatusEnum(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public static MsgStatusEnum getMsgUseEnum(String value) {
        for (MsgStatusEnum single : MsgStatusEnum.values()) {
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
