
package org.troy.common.component.jdbc;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * java,数据库表名转换
 * @author 杨磊
 * @since 2012-11-20
 * Copyright © 2012 SunraySoft
 */
public final class NameConverter {
    
    private NameConverter() {
    };
    
    /**
     * Convert a java field name to db field name. As lastLoginDate --> LAST_LOGIN_DATE
     * 
     * @param javaName
     *            a java field name.
     * @return db name.
     */
    public static String java2db(final String javaName) {
        if (javaName == null) { return null; }
        
        List<String> words = splitByCapital(javaName);
        
        if (words == null || words.size() == 0) { return null; }
        
        StringBuffer dbName = new StringBuffer();
        for (int i = 0; i < words.size(); i++) {
            if (i != 0) {
                dbName.append("_");
            }
            dbName.append(words.get(i));
        }
        
        return dbName.toString().toUpperCase();
    }
    
    /**
     * Convert a db field name to java field name. As LAST_LOGIN_DATE --> lastLoginDate
     * 
     * @param dbName
     *            a db field name.
     * @return java field name.
     */
    public static String db2java(final String dbName) {
        if (dbName == null) { return null; }
        
        List<String> words = splitByUnderline(dbName);
        
        if (words == null || words.size() == 0) { return null; }
        
        StringBuffer javaName = new StringBuffer();
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            if (i == 0) {
                javaName.append(word.toLowerCase());
            } else {
                String firstLetter = word.substring(0, 1);
                String otherLetter = word.substring(1);
                javaName.append(firstLetter.toUpperCase()).append(otherLetter.toLowerCase());
            }
        }
        
        return javaName.toString();
    }


    /**
     * 转换表名
     * @param tableName
     * @return
     */
    public static String table2java(String tableName){
        String table = db2java(tableName);
        if(StringUtils.isNotBlank(table)){
            return table.substring(0,1).toUpperCase()+table.substring(1,table.length());
        }else{
            return "";
        }
    }
    
    /**
     * Split a string by capital letter.
     * 
     * @param src
     *            source string
     * @return word list
     */
    private static List<String> splitByCapital(final String src) {
        if (src == null || src.length() == 0) { return null; }
        
        List<String> words = new ArrayList<String>();
        String lastWord = null;
        
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            
            if (c >= 'A' && c <= 'Z') {
                if (lastWord != null) {
                    words.add(lastWord);
                }
                lastWord = "";
                lastWord += c;
            } else {
                if (lastWord == null) {
                    lastWord = "";
                }
                lastWord += c;
            }
        }
        
        if (lastWord != null) {
            words.add(lastWord);
        }
        
        return words;
    }
    
    /**
     * Split a string by underline.
     * 
     * @param src
     *            source string
     * @return word list
     */
    private static List<String> splitByUnderline(final String src) {
        if (src == null || src.length() == 0) { return null; }
        
        List<String> words = new ArrayList<String>();
        String[] splited = src.split("_");
        
        for (String s : splited) {
            if (!s.trim().equals("")) {
                words.add(s.trim());
            }
        }
        
        return words;
    }
    
}
