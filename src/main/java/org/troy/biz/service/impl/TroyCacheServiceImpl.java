package org.troy.biz.service.impl;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.troy.biz.service.TroyCacheService;

/**
 * 缓存服务实现
 * 
 * @author troy
 * @version $Id: TroyCacheServiceImpl.java, v 0.1 2014年7月5日 下午11:55:00 troy Exp $
 */
@Service
public class TroyCacheServiceImpl<T> implements TroyCacheService<T> {

    /**
     * 30分钟
     */
    private final static String BIZ_DEFAULT_CACHE      = "org.troy.biz.cache";

    /**
     * 12小时
     */
    private final static String BIZ_DEFAULT_PERM_CACHE = "org.troy.biz.permCache";

    /**
     * 日志对象
     */
    private static final Logger logger                 = Logger
                                                           .getLogger(TroyCacheServiceImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public T getPermObject(String key) {
        Element obj = CacheManager.getInstance().getCache(BIZ_DEFAULT_PERM_CACHE).get(key);
        return null == obj ? null : (T) obj.getObjectValue();
    }

    @Override
    public boolean putPerm(String key, Object obj) {
        try {
            CacheManager.getInstance().getCache(BIZ_DEFAULT_PERM_CACHE).put(new Element(key, obj));
            return true;
        } catch (Exception e) {
            logger.error("缓存存放异常 key:[" + key + "],value:[" + obj + "]", e);
        }
        return false;
    }

    @Override
    public boolean reflushPerm(String key) {
        try {
            return CacheManager.getInstance().getCache(BIZ_DEFAULT_PERM_CACHE).remove(key);
        } catch (Exception e) {
            logger.error("缓存刷新异常 key:[" + key + "]", e);
        }
        return false;
    }

    @Override
    public boolean reflushPermAll() {
        try {
            CacheManager.getInstance().getCache(BIZ_DEFAULT_PERM_CACHE).removeAll();
            return true;
        } catch (Exception e) {
            logger.error("缓存刷新异常", e);
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject(String key) {
        Element obj = CacheManager.getInstance().getCache(BIZ_DEFAULT_CACHE).get(key);
        return null == obj ? null : (T) obj.getObjectValue();
    }

    @Override
    public boolean put(String key, Object obj) {
        try {
            CacheManager.getInstance().getCache(BIZ_DEFAULT_CACHE).put(new Element(key, obj));
            return true;
        } catch (Exception e) {
            logger.error("缓存存放异常 key:[" + key + "],value:[" + obj + "]", e);
        }
        return false;
    }

    @Override
    public boolean reflush(String key) {
        try {
            return CacheManager.getInstance().getCache(BIZ_DEFAULT_CACHE).remove(key);
        } catch (Exception e) {
            logger.error("缓存刷新异常 key:[" + key + "]", e);
        }
        return false;
    }

    @Override
    public boolean reflushAll() {
        try {
            CacheManager.getInstance().getCache(BIZ_DEFAULT_CACHE).removeAll();
            return true;
        } catch (Exception e) {
            logger.error("缓存刷新异常", e);
        }
        return false;
    }
}
