package org.troy.common.utils;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 共用字符串處理類，用於對字符串的轉換﹐替代﹐轉碼﹐截取﹐解析等常用操作
 * 
 * @author wangj 2013-5-30
 */
public class StringUtil {
    private static final char[] LT_ENCODE = "&lt;".toCharArray();
    private static final char[] GT_ENCODE = "&gt;".toCharArray();

    /**
     * 構造函數
     */
    public StringUtil() {

    }

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     * 
     * @param pObj
     *            待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object pObj) {
        if (pObj == null) {
            return true;
        }
        if (pObj == "") {
            return true;
        }
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素>0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     * 
     * @param pObj
     *            待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null)
            return false;
        if (pObj == "")
            return false;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return false;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return false;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * JavaBean之间对象属性值拷贝
     * 
     * @param pFromObj
     *            Bean源对象
     * @param pToObj
     *            Bean目标对象
     */
    public static void copyPropBetweenBeans(Object pFromObj, Object pToObj) {
        if (pToObj != null) {
            try {
                BeanUtils.copyProperties(pToObj, pFromObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 將一個null的字符串轉換為空串，
     * 
     * @param str
     *            要轉化字符串，可以為null,非null
     * @return
     */
    public static String getEmptyStringIfNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * 
     * 判別String是否為數字?
     * 
     * @param str
     *            String
     * 
     * @return boolean
     */

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;

    }

    /**
     * inParam:需要转换的gb2312中文字符 返回:该中文字符对应的UTF-8编码的字符
     */
    public static String toUTF(String inPara) {
        char temChr;
        int ascChr;
        int i;
        String rtStr = new String("");
        if (inPara == null) {
            inPara = "";
        }
        for (i = 0; i < inPara.length(); i++) {
            temChr = inPara.charAt(i);
            if (checkZm(temChr)) {
                rtStr += temChr;
            } else if (isNumeric(temChr)) {
                rtStr += temChr;
            } else {
                ascChr = temChr + 0;
                rtStr += "&#x" + Integer.toHexString(ascChr) + ";";
            }
        }
        return rtStr;
    }

    /**
     * 判断是否为数字
     * @param c
     * @return
     */
    public static boolean isNumeric(char c) {
        if (!Character.isDigit(c)) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为字母
     * @param c
     * @return
     */
    public static boolean checkZm(char c) {
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为汉字
     * @param str
     * @return
     */
    public static boolean vd(String str) {
        char[] chars = str.toCharArray();
        boolean isGB2312 = false;
        for (int i = 0; i < chars.length; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 2) {
                int[] ints = new int[2];
                ints[0] = bytes[0] & 0xff;
                ints[1] = bytes[1] & 0xff;
                if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
                    isGB2312 = true;
                    break;
                }
            }
        }
        return isGB2312;
    }

    /**
     * 根据length截取字符串
     * @param s
     * @param length
     * @return
     */
    public static String substring(String s, int length) {
        String ret = "";
        if (s != null && s.length() > 0) {
            if (s.length() > length) {
                ret = s.substring(0, length);
            } else {
                ret = s;
            }
        } else {
            ret = "";
        }
        return ret;
    }

    /**
     * 如果輸入的字符串中包含有空格﹐將空格過濾掉﹐如果輸入null的字符串轉換為空串 使用方法：<br>
     * String strValue=StringUtil.trimString(request.getParameter("pro_id"));
     * 
     * @param str
     *            要轉化的字符串，可以為null,非null
     * @return 非null的字符串返回去掉空格后的s﹐null的字符串返回空串
     */
    public static String trimString(String s) {
        if ((s == null) || (s.equalsIgnoreCase("null")))
            return "";
        else
            return s.trim();
    }

    public static String trimString(Object s) {
        if (s == null || s.toString().equalsIgnoreCase("null"))
            return "";
        else
            return s.toString();
    }

    public static String trimString(Long s) {
        if (s == null || s.toString().equalsIgnoreCase("null")
            || s.toString().equalsIgnoreCase("0"))
            return "";
        else
            return s.toString();
    }

    public static String stringToNumber(String s) {
        if (s == null || s.toString().equalsIgnoreCase("null") || s.toString().equalsIgnoreCase(""))
            return "0";
        else
            return s.toString();
    }

    public static String trimString(Object object, Object defaultObject) {
        if (object == null || object.toString().equalsIgnoreCase("null")) {
            if (defaultObject == null || defaultObject.toString().equalsIgnoreCase("null")) {
                return "";
            } else {
                return defaultObject.toString();
            }
        } else {
            return object.toString();
        }
    }

    /**
     * 將字符串轉換成utf-8格式的字符串
     * 
     * @param s
     *            任意字符串
     * @return 返回utf-8的字符
     */
    public static String coventString(String s) {
        String s1 = "";
        try {
            if (s != null) {
                s1 = new String(s.trim().getBytes("ISO-8859-1"), "UTF-8");
            }
        } catch (Exception exception) {
            s1 = "";
        }
        return s1;
    }

    /**
     * 將數組轉為UTF-8碼
     */
    public String[] arraysStringUTF(String as[]) {
        String as1[] = as;
        if (as == null) {
            return null;
        }
        for (int i = 0; i < as1.length; i++) {
            try {
                as1[i] = new String(as1[i].trim().getBytes("ISO-8859-1"), "UTF-8");
            } catch (Exception exception) {
                as1[i] = "";
            }
        }

        return as1;
    }

    /**
     * 將字符串轉換成utf-8的編碼，轉換後的字符串類似：\u8907\u88fd\u6a21
     * 
     * @param s
     *            任意字符
     * @return 返回以utf-8編碼的字符串,類似：\u8907\u88fd\u6a21
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 字符串的解析﹐將給定的字符串按照指定的分隔符解析 使用方法：<br>
     * String strValue=StringUtil.split("my test,split/ string"," ,/");
     * strValue={"my","test","split","string"};
     * 
     * @param s
     *            任意要解析的字符串﹐上例中的 "my test,split/ string"
     * @param delim
     *            字符串﹐即給定的分隔符﹐可以有多個不同的分隔符﹐上例中" ,/"﹐表示三個分隔符﹕空格﹐逗號和斜線
     * @return 返回按照給定分隔符解析后的子串形成的數組
     */
    public static String[] split(String s, String delim) {
        ArrayList<String> list = new ArrayList<String>();
        StringTokenizer token = new StringTokenizer(s, delim);
        while (token.hasMoreTokens()) {
            String a = token.nextToken();
            list.add(a);
        }
        String[] stringArray = new String[list.size()];
        list.toArray(stringArray);
        return stringArray;
    }

    /**
     * 將字符串的某一部分用另外一個字符串替代,和JAVA中的replaceAll類似﹐但是replaceAll對大小寫敏感 例如字符串 cadCAm
     * ,要將ca用test代替，替換後結果為 testdtestm 使用方法： String
     * strValue=StringUtil.replaceIgnoreCase("cadCAm","ca","test")
     * strValue="testdtestm" <br>
     * <b>注意：此方法對字符的大小寫不敏感，</b><br>
     * 
     * @param line
     *            字符串，如：cadCAm
     * @param oldString
     *            要被替換的字符，如：ca
     * @param newString
     *            要替換的新字符,如：test
     * @return 返回被替換後的字符
     */

    public static final String replaceIgnoreCase(String line, String oldString, String newString) {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    /**
     * 主要功能是替換HTML標簽符號，將"<"替換為"&lt;" , 將">"替換為"&gt;" 使用方法： String
     * strValue=StringUtil.escapeHTMLTags("<br>
     * <table>
     * <td></td>
     * <table>
     * ") strValue="&lt;br&gt;&lt;table&gt;&lt;td&gt;&lt;/td&gt;&lt;table&gt;"
     * 
     * @param in
     *            帶HTML標簽的字符串
     * @return 返回被替換後的字符
     */
    public static final String escapeHTMLTags(String in) {
        if (in == null) {
            return null;
        }
        char ch;
        int i = 0;
        int last = 0;
        char[] input = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int) (len * 1.3));
        for (; i < len; i++) {
            ch = input[i];
            if (ch > '>') {
                continue;
            } else if (ch == '<') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(LT_ENCODE);
            } else if (ch == '>') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(GT_ENCODE);
            }
        }

        if (last == 0) {
            return in;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();
    }

    /**
     * 把Array 轉成String,如: '1','2','3'
     * 
     * @param arr
     * @return
     */
    public static String arrayToSqlString(String[] arr) {
        String sqlStr = "";
        if (arr == null || arr.length == 0) {
            return "''";
        } else {
            for (int i = 0; i < arr.length; i++) {
                if (i == 0) {
                    sqlStr += "'" + arr[i] + "'";
                } else {
                    sqlStr += ",'" + arr[i] + "'";
                }
            }
        }
        return sqlStr;
    }

    public static String numberFomatString(int number, int decimal) {
        String str = "";
        if (String.valueOf(number).length() < decimal) {
            for (int i = 0; i < decimal - String.valueOf(number).length(); i++) {
                str = str + "0";
            }
        }
        return str + String.valueOf(number);
    }

    /**
     * 傳入小時分鐘得到日期
     * 
     * @return
     * @throws Exception
     */
    public static Date getDateByHoursAddMinutes(String s) throws Exception {

        SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm");
        ParsePosition parseposition = new ParsePosition(0);
        Date date = simpledateformat.parse(s, parseposition);
        return date;
    }

    /**
     * 定義輸出整數的格式﹐將輸入整數按照指定的格式輸出﹐如果輸入整數原來的位數大于或等于要格式化的位數﹐
     * 則返回原來輸入整數的字符串形式﹐如果不足位﹐則在前面補0 使用方法：<br>
     * String strValue=StringUtil.numberFomat(7,4); strValue="0007";
     * 
     * @param int number 任意要格式化的整數
     * @param int decimal 格式化后的整數位數
     * @return 返回按照指定格式輸出的字符串
     */
    public static String numberFomat(int number, int decimal) {
        String str = "";
        DecimalFormat df = new DecimalFormat();
        df.setMinimumIntegerDigits(decimal);
        str = df.format(number);
        return str;
    }

    /**
     * 功能﹕就是傳入一個字符串﹐然后把其轉化為時間類型
     * 
     * @param s
     * @return
     */
    public static Date strToDate(String s) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition parseposition = new ParsePosition(0);
        Date date = simpledateformat.parse(s, parseposition);
        return date;
    }

    /**
     * 取得系統當前日期，日期格式為:yyyy-MM-dd
     * 
     * @return yyyy-MM-dd樣式結果，例如：2006-09-11 使用方法：<br>
     *         String nowDate=DateUtil.getNowDate(); nowDate="2006-09-11" ;
     */
    public static String getNowDate() {
        java.util.Date date = new java.util.Date();

        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
        String s = simpledateformat.format(date);
        return s;
    }

    public static String getNowYear() {
        java.util.Date date = new java.util.Date();

        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
        String s = simpledateformat.format(date);
        return s;
    }

    public static int strCompar(String s) {
        Date current_time = new Date();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm:ss");
        String current_time_format = simpledateformat.format(current_time);
        String current_time_hours = current_time_format.substring(0, 2);
        String current_time_min = current_time_format.substring(3, 5);
        int current_time_hours_int = Integer.parseInt(current_time_hours);
        int current_time_min_int = Integer.parseInt(current_time_min);
        String s_begin_hours = s.substring(0, 2);
        String s_end_hours = s.substring(6, 8);
        String s_begin_min = s.substring(3, 5);
        String s_end_min = s.substring(9, 11);
        int s_begin_hours_int = Integer.parseInt(s_begin_hours);
        int s_end_hours_int = Integer.parseInt(s_end_hours);
        int s_begin_min_int = Integer.parseInt(s_begin_min);
        int s_end_min_int = Integer.parseInt(s_end_min);
        if ((s_begin_hours_int == s_end_hours_int) && (s_end_hours_int == current_time_hours_int)
            && (s_end_hours_int == current_time_hours_int)) {
            if (s_begin_min_int > s_end_min_int) {
                if (current_time_min_int >= s_begin_min_int
                    && current_time_min_int <= s_end_min_int) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                if (current_time_min_int <= s_begin_min_int
                    && current_time_min_int >= s_end_min_int) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } else if (s_begin_hours_int < s_end_hours_int) {

            if (current_time_hours_int > s_begin_hours_int
                && current_time_hours_int < s_end_hours_int) {
                return 1;
            } else if (current_time_hours_int == s_begin_hours_int
                       && current_time_hours_int != s_end_hours_int) {

                if (s_begin_min_int > current_time_min_int) {
                    return 0;
                } else {
                    if (s_end_min_int >= current_time_min_int) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            } else if (current_time_hours_int == s_end_hours_int
                       && current_time_hours_int != s_begin_hours_int) {

                if (s_end_min_int < current_time_min_int) {
                    return 0;
                } else {
                    if (s_begin_min_int <= current_time_min_int) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            } else {
                return 0;
            }
        } else if (s_begin_hours_int > s_end_hours_int) {

            if (s_begin_hours_int > current_time_hours_int
                && current_time_hours_int < s_end_hours_int) {

                return 1;
            } else if (current_time_hours_int != s_begin_hours_int
                       && current_time_hours_int == s_end_hours_int) {

                if (current_time_min_int <= s_begin_min_int) {
                    return 1;
                } else {
                    return 1;
                }
            } else if (current_time_hours_int == s_begin_hours_int
                       && current_time_hours_int != s_end_hours_int) {

                if (current_time_min_int <= s_end_min_int) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }
        } else
            return 0;
    }

    /**
     * 功能:去格式有空格的字符串
     * 
     * @param str
     * @return
     */
    public static String StringFormatTrim(String str) {
        return str.replace(" ", "");
    }

    public static String getEmptyStringIfNullToMidLine(Object str) {
        String returstr = "";
        if (str == null) {
            returstr = "";
        } else {
            returstr = str.toString();
        }
        return returstr;
    }

    /**
     * 获取某一字符串下包含子串后第一次出现某一子串的位置以及再两者之间子串
     * <p>
     * 
     * @author F1632506
     * @date Apr 2, 2010 11:38:32 AM
     * @param allString
     *            type String 不能够为空，总字串
     * @param containString
     *            type String 不能够为空，子串
     * @param nextString
     *            type String 不能够为空，子串 </ul>
     * @see package.className[#method([param])]
     * @throws exceptionType
     *             如果...將拋出exceptionType異常.
     * @return returnType 返回某一字符串下包含子串后第一次出现某一子串的位置以及再两者之间子串
     */
    public static String[] getNextPositionAtString(String allString, String containString,
                                                   String nextString) throws Exception {
        String targetStringArray[] = new String[2];
        int position = 0;
        if (allString.indexOf(containString) < 0) {
            throw new Exception(allString + "不包含" + containString);
        } else if (allString.indexOf(nextString) < 0) {
            throw new Exception(allString + "不包含" + nextString);
        } else {
            // 去查詢总字符串中包含字符传中所在的位置
            int containStringPosition = allString.indexOf(containString);
            // 截取总字符串从这位置开始的字符串
            String cutAfterAllString = allString.substring(containStringPosition
                                                           + containString.length());// containString.length()为了防止containString包含nextString
            if (cutAfterAllString.indexOf(nextString) < 0) {
                throw new Exception(allString + "中不存在" + containString + "后的" + nextString);
            } else {
                position = cutAfterAllString.indexOf(nextString);
                String targetString = cutAfterAllString.substring(0, position);

                if (position < 0) {
                    throw new Exception(allString + "包含" + containString + "后不存在包含" + nextString);
                }
                position = containStringPosition + containString.length() + position;
                targetStringArray[0] = String.valueOf(position);
                targetStringArray[1] = targetString;
            }

        }
        return targetStringArray;
    }

    /**
     * 去字串中尋找含某個字符的個數
     * <p>
     * 
     * @author F1632506
     * @date Nov 25, 2009 1:51:47 PM
     * @param param1
     *            type 描述
     * @param param2
     *            type 描述
     *            <ul>
     *            <li>value1描述 <li>value2描述
     *            </ul>
     * @see package.className[#method([param])]
     * @throws exceptionType
     *             如果...將拋出exceptionType異常.
     * @return returnType 返回類型描述
     */
    public static int strContainNumber(String str, char subStr) {
        char[] charStr = str.toCharArray();
        int a = 0;
        for (int i = 0; i < charStr.length; i++) {
            if (charStr[i] == subStr) {
                a++;
            }
        }
        return a;
    }

    /**
     * 如果輸入null的字符串轉換為空串,字符串中間包含有空格將保留﹐ 使用方法：<br>
     * String strValue=StringUtil.trimString(request.getParameter("info"));
     * 
     * @param str
     *            要轉化的字符串，可以為null,非null
     * @return null的字符串返回空串
     */

    public static String trimStringStr(String s) {
        if ((s == null) || (s.equalsIgnoreCase("null")))
            return "";
        else
            return s.trim();
    }

    public static String trimStringStr(Object s) {
        if (s == null || s.toString().equalsIgnoreCase("null"))
            return "";
        else
            return s.toString().trim();
    }

    /**
     * 將字符串轉換為long,如果為"",轉換為0
     * <p>
     * 
     * @author F1041724
     * @date Oct 9, 2010 11:56:44 AM
     */
    public static long stringToLong(String str) {
        long l = 0;
        if (!"NULL".equalsIgnoreCase(str) && str != null && !"".equals(str)) {
            l = Long.parseLong(str);
        } else {
            l = 0;
        }
        return l;
    }

    /**
     * 將字符串轉換為int,如果為"",轉換為0
     * <p>
     * 
     * @author F1041724
     * @date Oct 9, 2010 11:56:44 AM
     */
    public static int stringToInt(String str) {
        int i = 0;
        if (!"NULL".equalsIgnoreCase(str) && str != null && !"".equals(str)) {
            i = Integer.parseInt(str);
        } else {
            i = 0;
        }
        return i;
    }

    /**
     * 方法描述
     * <p>
     * 將字符串轉換為double,如果為"",轉換為0
     * 
     * @author F1041724
     * @date Oct 25, 2010 9:38:48 AM
     */
    public static double stringToDouble(String str) {
        double d = 0;
        if (!"NULL".equalsIgnoreCase(str) && str != null && !"".equals(str)) {
            d = Double.parseDouble(str);
        } else {
            d = 0;
        }
        return d;
    }

    public static Object trimObject(Object s) {
        if ((s == null) || (s.toString().equalsIgnoreCase("null")))
            return "";
        else
            return s;
    }

    /**
     * 判断是否为null返回字符串
     */
    public static String processNull(Object value) {
        return ((value == null) ? "" : value.toString());
    }

    /**
     * 变换字符长度 前加0
     */
    public static String getnewLengthStr(Object str, int i) {
        String ss = processNull(str);
        if (ss.length() > i) {
            ss = ss.substring(ss.length() - i, ss.length());
        }
        while (ss.length() < i) {
            ss = "0" + ss;
        }
        return ss;
    }

    /**
     * 变换字符长度 后加0
     * @param str
     * @param i
     * @return
     */
    public static String getafterLengthStr(Object str, int i) {
        String ss = processNull(str);
        if (ss.length() > i) {
            ss = ss.substring(ss.length() - i, ss.length());
        }
        while (ss.length() < i) {
            ss = ss + "0";
        }
        return ss;
    }

    /**
     * Double转String
     */
    public static String doubleToString(Object str) {
        String ss = processNull(str);
        if (ss.equals("")) {
            str = "0";
        }
        int i = Double.valueOf(processNull(str)).intValue();
        return String.valueOf(i).toString();
    }

    /**
     * 把list转换为以逗号分隔的字符串，比如list.add("aa");list.add("bb");list.add("cc");那么输出结果为：aa,bb,cc
     */
    @SuppressWarnings("rawtypes")
    public static String listToString(List list) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (!processNull(list.get(i)).equals("")) {
                str.append("'" + processNull(list.get(i)) + "',");
            }
        }
        if (str.length() > 2 && str.substring(str.length() - 1, str.length()).equals(",")) {
            return str.substring(0, str.length() - 1);
        } else {
            return str.toString();
        }
    }

    /***
     * 返回匹配字符串(用于自动补零)
     * @param str 数字型
     * @param pattern 匹配字符串  如000000
     * @return
     */
    public static String getPatternStr(String str, String pattern) {
        if (isEmpty(str) || isEmpty(pattern))
            return null;
        DecimalFormat df = new DecimalFormat(pattern);
        try {
            Long.valueOf(str);//判断str是不是数字
        } catch (NumberFormatException e) {
            return null;
        }
        return df.format(Long.valueOf(str).longValue());
    }

    /***
     * 返回匹配倒序字符串
     * @param str
     * @return
     */
    public static String reverseSort(String str) {
        String str2 = "";
        for (int i = str.length() - 1; i > -1; i--) {
            str2 += String.valueOf(str.charAt(i));
        }
        return str2;
    }

    /**
     * 以相关规则把字符串倒序
     * @param str 原始字符串
     * @param sub 每组字符个数
     * @param desc 是否倒序排列
     * @return
     */
    public static String groupString(String str, int sub, boolean desc) {
        StringBuffer temp = new StringBuffer();
        if (!isEmpty(str)) {
            int group = str.length() / sub;
            if (desc) {
                for (int i = group - 1; i >= 0; i--) {
                    temp.append(str.substring(i * sub, (i * sub) + sub).toUpperCase());
                }
            } else {
                for (int i = 0; i < group; i++) {
                    temp.append(str.substring(i * sub, (i * sub) + sub).toUpperCase());
                }
            }
        }
        return temp.toString();
    }

    /**
     * 以相关规则把数字字符串转换成16进制，在转换成相应格式
     * @param str 原始字符串
     * @return
     */
    public static String toHexString(Object o) {
        long a = stringToLong(o.toString());
        String b = Long.toHexString(a);
        return groupString(getnewLengthStr(b, 8), 2, true);
    }

    public static String joinWithoutNull(Object[] objs, String split) {
        if (null == objs) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int length = objs.length;
        for (int i = 0; i < objs.length; i++) {
            Object obj = objs[i];
            if (null == obj) {
                continue;
            }
            sb.append(obj.toString());
            if (i != length - 1) {
                sb.append(split);
            }
        }
        return sb.toString();
    }
    
    /**
     * 删除字符串origionStr最后一个字符为tag
     * 
     * @param origionStr
     * @param tag
     * @return
     */
    public static String deleteLastOfChar(StringBuilder origionStr,String tag){
        if(origionStr==null || origionStr.length()==0){
            return origionStr.toString();
        }
        if(StringUtil.isEmpty(tag)){
            return origionStr.toString();
        }
        int length=origionStr.length();
        //如果2个相等才做删除操作
        if(tag.equals(origionStr.substring(length-1, length))){
            return origionStr.delete(length-1,  length).toString();
        }
        return origionStr.toString();
    }

    public static void main(String[] args) {
        System.out.println(deleteLastOfChar(new StringBuilder("11,22,55,11,"),","));
    }
}
