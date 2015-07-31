package org.troy.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;

/**
 * OSS工具类
 * @author 杨磊
 * @since 2014-5-26 上午10:00:05
 * Copyright © 2013 - 2014 CNNCT
 */
public final class OssUtil {
	//私有化构造方法
	private OssUtil(){}
	private static class SingletonHolder{
	  //静态初始化器，由JVM来保证线程安全
	  private static OssUtil instance=new OssUtil();
	}
	//获取OssUtil实例
	public static OssUtil getInstance(){
	  return SingletonHolder.instance;
	}
	
	private static OSSClient client;
	private static Properties config;
	
	private static String bucketName = "";
	private static String accessId = "";
	private static String accessKey = "";
	
	private static OSSObject obj = null;
	private static Lock lock = new ReentrantLock();

	static{
		try {
			config = PropertiesLoaderUtils.loadAllProperties("app-config.properties");
			accessId = config.getProperty("oss.access.id");
			accessKey = config.getProperty("oss.access.key");
			bucketName = config.getProperty("oss.bucketname");
		} catch (IOException e) {
			throw new RuntimeException("加载OSS配置文件失败!错误信息:"+e.getMessage());
		}
	}
	
	/**
	 * 加载OSSCLIENT
	 */
	public void loadClient() {
		if (client == null) {
			try {
				lock.lock();
				client = new OSSClient(accessId, accessKey);
			} catch (Exception e) {
				throw new RuntimeException("加载OSSCLIENT出现异常！错误信息:"+e.getMessage());
			} finally{
				lock.unlock();
			}
		}
	}

	/**
	 * 下载图片, download操作
	 * @param key
	 * @return
	 */
	public InputStream getOssPic(String key) {
		InputStream fis = null;
		try {
			lock.lock();
			loadClient();
			obj = client.getObject(bucketName, key);
			fis = obj.getObjectContent();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			lock.unlock();
		}

		return fis;
	}

	/**
	 * 下载图片, download操作
	 * @param key
	 * @return
	 */
	public byte[] getOssPicToByte(String key) {
		try {
			lock.lock();
			loadClient();
			obj = client.getObject(bucketName, key);
			return FileCopyUtils.copyToByteArray(obj.getObjectContent());
		} catch (Exception e) {
			throw new RuntimeException("获取图片出现异常！错误信息:"+e.getMessage());
		} finally{
			lock.unlock();
		}
	}
	
	
	/**
	 * 上传图片,upload操作
	 * @param fileName
	 * @param is
	 * @return
	 */
	public boolean uploadPic(String fileName,InputStream is) {
		try {
			lock.lock();
			loadClient();
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentLength(is.available());
			PutObjectResult por = client.putObject(bucketName, fileName, is,objectMeta);
			return StringUtils.isEmpty(por.getETag());
		} catch (Exception e) {
			throw new RuntimeException("上传图片出现异常！错误信息:"+e.getMessage());
		} finally{
			lock.unlock();
		}
	}

	/**
	 * 判断是否启用OSS
	 * 
	 * @return
	 */
	public boolean enableOSS() {
		String enable = config.getProperty("oss.enable");
		return enable.equals("1");
	}
}
