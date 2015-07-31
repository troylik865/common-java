package org.troy.system.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.troy.biz.constant.BizConstant;
import org.troy.common.utils.DateUtil;

/**
 * 工作日计算的工具类
 * 
 * @author troy
 * @version $Id: WorkDayUtil.java, v 0.1 2014年7月16日 上午10:38:26 troy Exp $
 */
public class WorkDayUtil {

    private static final Set<String> isRestButNeedRemoveSet = new HashSet<String>();

    private static final Set<String> isNeedRestSet          = new HashSet<String>();

    static {
        isNeedRestSet.add("2014-07-17");
        isRestButNeedRemoveSet.add("2014-07-19");
    }

    /**
     * 根据指定的日期获取最近的一个工作日
     * 
     * @param date
     * @return
     */
    public static Date getRecentlyWorkDate(Date date) {
        Date recentlyWorkDay = date;
        while (true) {
            recentlyWorkDay = DateUtil.getNextDate(recentlyWorkDay);
            if (isWorkDay(recentlyWorkDay)) {
                return recentlyWorkDay;
            }
        }

    }

    public static Date getLastWorkDate(Date date) {
        Date recentlyWorkDay = date;
        while (true) {
            recentlyWorkDay = DateUtil.getBeforeDate(recentlyWorkDay);
            if (isWorkDay(recentlyWorkDay)) {
                return recentlyWorkDay;
            }
        }

    }

    /**
     * 获取距离当前时间最近的一个工作日
     * 
     * @return
     */
    public static Date getRecentlyWorkDate() {
        return getRecentlyWorkDate(new Date());
    }

    /**
     * 判断是否是工作日
     * 
     * @param date
     * @return
     */
    public static boolean isWorkDay(Date date) {

        /*是休息日的情况：
            1. 是双休日并且是不需要被移除的
            2. 是法定节假日
        */
        return !((isRestDay(date) && !isRestButNeedRemove(date)) || isHoliday(date));
    }

    /**
     * 是否是节假日
     * 
     * @param date
     * @return
     */
    public static boolean isHoliday(Date date) {
        String day = DateUtil.date2String(date, BizConstant.DATE_FORMAT);
        return isNeedRestSet.contains(day);
    }

    /**
     * 是否是双休日(星期六，星期天)
     * 
     * @param date
     * @return
     */
    public static boolean isRestDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == 1 || dayOfWeek == 7);
    }

    /**
     * 是否双休日，但是需要移出休息日范围的
     * 
     * @return
     */
    public static boolean isRestButNeedRemove(Date date) {
        String day = DateUtil.date2String(date, BizConstant.DATE_FORMAT);
        return isRestButNeedRemoveSet.contains(day);
    }

    @Test
    public void test() {
        System.out.println(WorkDayUtil.getRecentlyWorkDate());
    }
}
