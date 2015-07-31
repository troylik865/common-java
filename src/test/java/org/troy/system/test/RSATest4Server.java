package org.troy.system.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.ResourceUtils;
import org.troy.common.utils.RSAUtil;

/**
 * RSACoderTest测试类 
 * @author 杨磊
 * @since 2014-5-7 下午4:01:58
 * Copyright © 2013 - 2014 CNNCT
 */
public class RSATest4Server extends BaseJunitCase{
	private String data; //加密前的数据
	private String encodedData; //加密的密文
	private String sign;
	private String sourceId = "3";
	
	@Autowired
	private JdbcTemplate template;
	
	//@Before
	public void setUp() throws Exception {
		for (int i = 1; i < 15; i++) {
			Map<String, Object> keyMap = RSAUtil.genKeyPair();
			template.update("insert into pay_rsakey values("+i+",'"+ RSAUtil.getPublicKey(keyMap)+"','"+ RSAUtil.getPrivateKey(keyMap)+"')");
		}
		clientEncrypt();
	}

	//客户端使用私钥加密数据,并生成签名
    @Before
	public void clientEncrypt() throws Exception {
		Map<String, Object> keyMap = template.queryForMap("select * from pay_rsakey where source_id=?", sourceId);
		assertTrue(keyMap.get("source_id")!=null);
		assertTrue(keyMap.get("pub_key")!=null);
		assertTrue(keyMap.get("pri_key")!=null);
		//保存生成的key
		storePriKeyToFile((Integer)keyMap.get("source_id"),(String)keyMap.get("pri_key"));
		//读取key
		String pri_key = FileUtils.readFileToString(new File("client"+(Integer)keyMap.get("source_id")+"PriKey.key"), "utf-8");
		assertTrue(StringUtils.isNotBlank((String)keyMap.get("pub_key")));
		assertTrue(StringUtils.isNotBlank(pri_key));
		
		System.err.println("商户"+sourceId+"的公钥: \n\r" + keyMap.get("pub_key"));
		System.err.println("商户"+sourceId+"的私钥： \n\r" + pri_key);
		
		data = "{data:{ids:['3']}}";
		encodedData = RSAUtil.encryptByPrivateKey(data, pri_key);
        System.err.println("密文内容:\r\n"+new String(encodedData));
		// 产生签名
		sign = RSAUtil.sign(encodedData, pri_key);
		System.err.println("密文签名:\r\n" + sign);

	}
	
	private void storePriKeyToFile(Integer source_id,String pri_key) throws IOException{
		FileUtils.writeStringToFile(new File("client"+source_id+"PriKey.key"), pri_key);
	}
	
	@Test
	@Rollback(true)
	public void serverDecodeData() throws Exception{
		Map<String, Object> keyMap = template.queryForMap("select * from pay_rsakey where source_id=?", sourceId);
		assertTrue(keyMap.get("pub_key")!=null);
		// 验证签名
		boolean status = RSAUtil.verify(encodedData, (String) keyMap.get("pub_key"), sign);
		System.err.println("签名验证结果:\r" + status);
		assertTrue(status);
		String decodedData = RSAUtil.decryptByPublicKey(encodedData, (String) keyMap.get("pub_key"));
		System.err.println("加密前: " + data + "\n\r" + "解密后: " + decodedData);
		assertEquals(data, decodedData);
	}
	
	/**
	 * 删除生成的测试key文件
	 * @throws FileNotFoundException
	 */
	@After
	@SuppressWarnings("unchecked")
	public void tearDown() throws FileNotFoundException{
		LinkedList<String> files = (LinkedList<String>)FileUtils.listFiles(ResourceUtils.getFile(System.getProperty("user.dir")), new String[]{"key"}, false);
		assertTrue(files.size()>0);
		for (Object object : files) {
			FileUtils.deleteQuietly(ResourceUtils.getFile(object.toString()));
		}
	}
}
