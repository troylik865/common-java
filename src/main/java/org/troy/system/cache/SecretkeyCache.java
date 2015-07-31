package org.troy.system.cache;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.troy.common.utils.RSAUtil;
import org.troy.system.util.SpringBeanLoader;

import com.google.common.collect.Maps;

/**
 * Created by flyer on 2014/5/20.
 */
public class SecretkeyCache {
    private static final JdbcTemplate jdbcTemplate;
    private static final Map<String, Map<String, Object>> secretkeyCache = Maps.newConcurrentMap();

    static {
        jdbcTemplate = (JdbcTemplate) SpringBeanLoader.getSpringBean("jdbcTemplate");
        buildSecretkeyCache();
    }

    private static void buildSecretkeyCache() {
        if (!secretkeyCache.isEmpty()) {
            secretkeyCache.clear();
        }
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from pay_rsakey");
        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> item : list) {
                if (item.containsKey("source_id")) {
                    secretkeyCache.put("secretkey" + item.get("source_id").toString(), item);
                }
            }
        }
    }

    /**
     * 获取缓存的密钥对
     * @param key
     * @return
     */
    public static Map<String, Object> getSecretkeyCache(String key) {
        if (secretkeyCache.containsKey("secretkey" + key)) {
            return secretkeyCache.get("secretkey" + key);
        } else {
            return Maps.newConcurrentMap();
        }
    }

    /**
     * 获取缓存的私钥
     * @param source
     * @return
     */
    public static String getPrivateKeyString(String source) {
        String privateKey = null;
        if (secretkeyCache.containsKey("secretkey" + source)) {
            Map<String, Object> keyMap = secretkeyCache.get("secretkey" + source);
            if (keyMap.containsKey(RSAUtil.PRIVATE_KEY)) {
                privateKey = (String) keyMap.get(RSAUtil.PRIVATE_KEY);
            }
        }
        return privateKey;
    }

    /**
     * 获取缓存的公钥
     * @param source
     * @return
     */
    public static String getPublicKeyString(String source) {
        String publicKey = null;
        if (secretkeyCache.containsKey("secretkey" + source)) {
            Map<String, Object> keyMap = secretkeyCache.get("secretkey" + source);
            if (keyMap.containsKey(RSAUtil.PUBLIC_KEY)) {
                publicKey = (String) keyMap.get(RSAUtil.PUBLIC_KEY);
            }
        }
        return publicKey;
    }
}
