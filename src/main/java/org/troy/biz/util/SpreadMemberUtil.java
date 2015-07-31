package org.troy.biz.util;

import org.apache.log4j.Logger;
import org.troy.common.utils.AlipayDesUtil;

/**
 * 推广会员功能相关的工具类
 * 
 * @author troy
 * @version $Id: SpreadMemberUtil.java, v 0.1 2014年7月17日 上午11:26:03 troy Exp $
 */
public class SpreadMemberUtil {

    private static final Logger logger = Logger.getLogger(SpreadMemberUtil.class);

    private static final String KEY    = "troySupport";

    /**
     * 密文还原成明文
     * 
     * @param sec   明文内容
     * @return      Id的字符串，为了在方法里面区分 解密失败的场景
     */
    public static String getRealMemberIdBySec(String sec) {
        try {
            return AlipayDesUtil.decrypt(sec, KEY);
        } catch (Exception e) {
            logger.error("密文内容：[" + sec + "]，转换明文的时候异常！", e);
            return null;
        }
    }

    /**
     * 明文Id 转换成密文
     * 
     * @param memberId
     * @return
     */
    public static String getSecByMemberId(long memberId) {
        try {
            return AlipayDesUtil.encrypt(memberId + "", KEY);
        } catch (Exception e) {
            logger.error("明文内容：[" + memberId + "]，转换密文的时候异常！", e);
            return null;
        }
    }
}
