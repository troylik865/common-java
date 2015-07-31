package org.troy.common.component.sync.bean;

import java.io.IOException;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.core.io.ClassPathResource;
import org.troy.common.component.sync.domain.DataSyncCipher;
/**
 * 本地缓存,map持久化
 * @author 杨磊
 * @since 2014-6-6 上午10:33:24
 * Copyright © 2013 - 2014 CNNCT
 */
public class LocalMessageCache {
	private static HTreeMap<String,DataSyncCipher> localCache;
	private static HTreeMap<String,Integer> repeatCounter;
	private static DB db1;
	private static DB db2;
	static{
		try {
			db1 = DBMaker.newFileDB(new ClassPathResource("localCache.cache").getFile())
			        .closeOnJvmShutdown()
			        .encryptionEnable("cnnct123")
			        .make();
			db2 = DBMaker.newFileDB(new ClassPathResource("repeatCounter.cache").getFile())
					.closeOnJvmShutdown()
					.encryptionEnable("cnnct123")
					.make();
			if(localCache==null){
				localCache = db1.getHashMap("localCache");
			}
			if(repeatCounter==null){
				repeatCounter = db2.getHashMap("repeatCounter");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addCount(String key){
		try {
			repeatCounter.put(key, queryCount(key)+1);
			db2.commit();
		} catch (Exception e) {
			e.printStackTrace();
			db2.rollback();
		} finally{
			db2.close();
		}
	}
	
	public static void clearCount(String key){
		try {
			repeatCounter.remove(key);
			db2.commit();
		} catch (Exception e) {
			e.printStackTrace();
			db2.rollback();
		} finally{
			db2.close();
		}
	}
	
	public static Integer queryCount(String key){
		if(repeatCounter.containsKey(key)){
			return repeatCounter.get(key);
		}
		return 0;
	}
	
	public static void put(String key,DataSyncCipher cipher){
		try {
			localCache.put(key, cipher);
			db1.commit();
		} catch (Exception e) {
			e.printStackTrace();
			db1.rollback();
		} finally{
			db1.close();
		}
	}
	
	public static DataSyncCipher get(String key){
		if(localCache.containsKey(key)){
			return localCache.get(key);
		}else{
			return null;
		}
	}
	
	public static void remove(String key){
		try {
			if(localCache.containsKey(key)){
				localCache.remove(key);
			}
			db1.commit();
		} catch (Exception e) {
			e.printStackTrace();
			db1.rollback();
		} finally{
			db1.close();
		}
	}
	public static boolean containsKey(String key){
		return localCache.containsKey(key);
	}
	
}

