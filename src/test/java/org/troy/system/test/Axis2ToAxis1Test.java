/**
 * 
 */
package org.troy.system.test;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

/**
 * @author jiangb
 * 2014-5-14
 */
public class Axis2ToAxis1Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RPCServiceClient serviceClient = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			// 指定调用WebService的URL
			options.setAction("getTcdList");
			EndpointReference targetEPR = new EndpointReference(
					"http://172.16.200.164:8080/services/tcsfService");
			options.setTo(targetEPR);
			serviceClient.setTargetEPR(targetEPR);
			QName opAddEntry = new QName(
					"http://server.webService.chairmanzheng.com", "getTcdList");
			String[] result = (String[]) serviceClient
					.invokeBlocking(opAddEntry, new Object[] {"姓名"},
							new Class[] { String[].class })[0];// 返回结果为String数组

			serviceClient.cleanupTransport();
			System.out.println(result[0].toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
