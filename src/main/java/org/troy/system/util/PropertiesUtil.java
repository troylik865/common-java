package org.troy.system.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取properties文件的内容
 * 
 * @author troy
 * @version $Id: PropertiesUtil.java, v 0.1 2014年6月25日 下午7:16:57 troy Exp $
 */
public class PropertiesUtil {

    public static final Logger  logger    = Logger.getLogger(PropertiesUtil.class);

    private static Properties   prop      = new Properties();

    private static final String FILE_NAME = "sys-config.properties";

    static {
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(FILE_NAME);
        try {
            prop.load(is);
        } catch (IOException e) {
            logger.error("加载" + FILE_NAME + "文件异常！", e);
        }
    }

    /**
     * 通过key来获取对应的属性
     * 
     * @param key
     * @return
     */
    public static String getValue(String key) {
        return (String) prop.get(key);
    }

}
