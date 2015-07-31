package org.troy.biz.service;

public interface TroyCacheService<T> {

    T getObject(String key);

    boolean put(String key, Object obj);

    boolean reflush(String key);

    boolean reflushAll();

    T getPermObject(String key);

    boolean putPerm(String key, Object obj);

    boolean reflushPerm(String key);

    boolean reflushPermAll();

}
