package org.troy.biz.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.troy.biz.service.TroyCacheService;

/**
 * 缓存操作父类
 * 
 * @author troy
 * @version $Id: TroyCacheManager.java, v 0.1 2014年7月5日 下午11:43:12 troy Exp $
 */
public abstract class TroyCacheManager<T> {

    protected String            prefix = this.getClass().getName() + "-";

    @Autowired
    private TroyCacheService<T> troyCacheService;

    /**
     * 需要业务自己实现的方法
     * 
     * @param key
     * @return
     */
    public abstract T getObjectFromDB(String key);

    /**
     * 通过缓存key获取缓存
     * 
     * @param key
     * @return
     */
    public T get(String key) {
        T obj = troyCacheService.getObject(getRealKey(key));
        if (null != obj) {
            return obj;
        }
        T t = getObjectFromDB(key);
        if (null != t) {
            troyCacheService.put(getRealKey(key), t);
        }
        return t;
    }

    /**
     * 通过缓存key获取缓存 缓存时间为12小时
     * 
     * @param key
     * @return
     */
    public T getPerm(String key) {
        T obj = troyCacheService.getPermObject(getRealKey(key));
        if (null != obj) {
            return obj;
        }
        T t = getObjectFromDB(key);
        if (null != t) {
            troyCacheService.putPerm(getRealKey(key), t);
        }
        return t;
    }

    /**
     * 存放缓存
     * 
     * @param key
     * @param obj
     * @return
     */
    public boolean put(String key, T obj) {
        return troyCacheService.put(getRealKey(key), obj);
    }

    /**
     * 刷新单个缓存
     * 
     * @param key
     * @return
     */
    public boolean reflush(String key) {
        return troyCacheService.reflush(getRealKey(key));
    }

    /**
     * 刷新所有缓存
     * 
     * @return
     */
    public boolean reflushAll() {
        return troyCacheService.reflushAll();
    }

    /**
     * 存放缓存
     * 
     * @param key
     * @param obj
     * @return
     */
    public boolean putPerm(String key, T obj) {
        return troyCacheService.putPerm(getRealKey(key), obj);
    }

    /**
     * 刷新单个缓存
     * 
     * @param key
     * @return
     */
    public boolean reflushPerm(String key) {
        return troyCacheService.reflushPerm(getRealKey(key));
    }

    /**
     * 刷新所有缓存
     * 
     * @return
     */
    public boolean reflushPermAll() {
        return troyCacheService.reflushPermAll();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 为缓存加上前缀
     * 
     * @param key
     * @return
     */
    private String getRealKey(String key) {
        return prefix + key;
    }
}
