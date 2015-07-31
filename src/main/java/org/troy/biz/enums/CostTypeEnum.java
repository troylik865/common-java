package org.troy.biz.enums;

import org.apache.commons.lang3.StringUtils;
import org.troy.common.utils.enums.BaseEnum;

/**
 * 性别
 * 
 * @author troy
 * @version $Id: IsValidatedEnum.java, v 0.1 2014年6月16日 下午7:44:21 troy Exp $
 */
public enum CostTypeEnum implements BaseEnum<String>{

	COSTTYPE_00("支付宝", "00"),

	COSTTYPE_01("银行卡", "01");

    private String value;

    private String name;

    CostTypeEnum(String name, String value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 通过value来获取枚举值
     * 
     * @param value
     * @return
     */
    public CostTypeEnum getInvestDirectionEnum(String value) {
        for (CostTypeEnum single : CostTypeEnum.values()) {
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
