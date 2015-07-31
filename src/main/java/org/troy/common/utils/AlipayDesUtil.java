/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package org.troy.common.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 加密工具类
 * 
 * @author troy
 * @version $Id: AlipayDesUtil.java, v 0.1 2014-3-1 上午10:24:18 troy Exp $
 */
public class AlipayDesUtil {

    /** 日志. */
    private static final Logger logger  = Logger.getLogger(AlipayDesUtil.class);

    /**
     * 采用DES 加/解 密
     */
    private final static String DES     = "DES";

    /**
     * 编码方式
     */
    private final static String CHARSET = "UTF-8";

    /**
     * 加密数据
     * 
     * @param data          待加密的数据
     * @param key           密钥
     * @return              密文
     */
    public static String encrypt(String data, String key) {
        byte[] bt = null;
        try {
            bt = encrypt(data.getBytes(CHARSET), key.getBytes(CHARSET));
            return (null == bt) ? "" : Base64.encode(bt);
        } catch (Exception e) {
            logger.error("加密数据异常", e);
        }
        return "";
    }

    /*** 加密数据
     * 
     * @param data      待加密数据
     * @param key       密钥
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = null;
        Cipher cipher = null;
        try {
            dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);
            cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("加密数据异常", e);
        }
        return null;
    }

    /**
     * 解密数据
     * 
     * @param data  待解密的数据
     * @param key   密钥
     * @return
     */
    public static String decrypt(String data, String key) {
        if (StringUtils.isBlank(data)) {
            return "";
        }
        byte[] buf;
        try {
            buf = Base64.decode(data);
            byte[] bt = decrypt(buf, key.getBytes(CHARSET));
            return new String(bt, CHARSET);
        } catch (Exception e) {
            logger.error("解密数据异常", e);
        }
        return "";
    }

    /**
     * 解密
     * 
     * @param data      待解密数据
     * @param key       密钥
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }
    
    public static void main(String[] args) {
        System.out.println(AlipayDesUtil.encrypt("11", "asd11111"));
    }
}
