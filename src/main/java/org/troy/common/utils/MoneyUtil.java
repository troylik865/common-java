/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package org.troy.common.utils;

import java.math.BigDecimal;
import java.util.Currency;

import org.troy.common.entity.Money;

/**
 * 金额操作工具类
 * 
 * @author peng.lanqp
 * @version $Id: MoneyUtil.java, v 0.1 2011-1-20 下午05:22:04 peng.lanqp Exp $
 */
public final class MoneyUtil {
    /**
     * 万元的值
     */
    private final static long   WAN_YUAN_UNIT = 10000;

    /**
     * 0.0
     */
    private final static String DEFAULT_VALUE = "0.0";

    /**
     * 单元为“元”的金额转化成单位为“分”<br>
     * 金额 10.32 ——》1032
     * 
     * @param yuan 单元为“元”的金额
     * @return
     */
    public static String toCent(String yuan) {
        Money money = new Money(yuan);
        return String.valueOf(money.getCent());
    }

    /**
     * 依据币种将单元为“元”的金额转化成单位为“分”<br>
     * RMB 10.32 ——》1032
     * USD 10.32 ——》1032
     * JPY 1032  ——》1032
     * 
     * @param yuan 单元为“元”的金额
     * @param value 币种ISO 4217标准
     * @return
     */
    public static String toCent(String yuan, String value) {

        Currency currency = Currency.getInstance(value);
        Money money = new Money(yuan, currency);
        return String.valueOf(money.getCent());
    }

    /**
     * 单元为“分”的金额转化成单位为“元”<br>
     * 金额 1032 ——》10.32
     * 
     * @param cent 单元为“分”的金额
     * @return
     */
    public static String toYuan(String cent) {
        Money money = new Money();
        money.setCent(Long.valueOf(cent));
        return money.toString();
    }

    /**
     * 整数位前补零,小数位补齐2位<br>
     * 无效格式如123，.10，1.1,<br>
     * 有效格式如1.00，0.10
     * 
     * @param yuan 单元为“元”的金额
     * @return
     */
    public static String formatYuan(String yuan) {
        Money money = new Money(yuan);
        return money.toString();
    }

    /**
     * 单元为“万元”的金额转化成单位为“分”<br>
     * 金额 1.5 ——》15000
     * 
     * @param wYuan 单元为“万元”的金额
     * @return
     */
    public static Money formatWYuan(String wYuan) {
        return new Money(wYuan).multiply(WAN_YUAN_UNIT);
    }

    /**
     * 获取万元的分数值
     * 
     * @param wYuan
     * @return
     */
    public static long getWYuanToCent(String wYuan) {
        return new Money(wYuan).multiply(WAN_YUAN_UNIT).getCent();
    }

    /**
     * 单元为“分”的金额转化成单位为“万元”<br>
     * 金额 15000 ——》1.5
     * 
     * @param cent 单元为“分”的金额
     * @return
     */
    public static String toWYuan(Long cent) {
        if (cent == null) {
            return DEFAULT_VALUE;
        }
        Money money = new Money(cent);
        return money.divide(new BigDecimal(WAN_YUAN_UNIT), Money.DEFAULT_ROUNDING_MODE).toString();
    }
    
    public static void main(String args[]){
        System.out.println(toWYuan((1000L)));
        
    }
}
