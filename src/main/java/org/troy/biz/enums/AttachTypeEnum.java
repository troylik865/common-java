package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;
import org.troy.common.utils.enums.BaseEnum;

/**
 * 附件业务类型
 * 
 * @author troy
 * @version $Id: IsValidatedEnum.java, v 0.1 2014年6月16日 下午7:44:21 troy Exp $
 */
public enum AttachTypeEnum implements BaseEnum<String> {

    MEMBER("会员附件", "member"),

    COLUMN("软件中心附件", "softCenter"),

    FINANCIAL_INTERESTS_AND_POSITIONS_PIC1("资金权益及持仓图片1", "biz_trans_record1"),

    FINANCIAL_INTERESTS_AND_POSITIONS_PIC2("交易明细", "biz_trans_record2");

    private String value;

    private String name;

    AttachTypeEnum(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public static AttachTypeEnum getAttachTypeEnum(String value) {
        for (AttachTypeEnum single : AttachTypeEnum.values()) {
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
