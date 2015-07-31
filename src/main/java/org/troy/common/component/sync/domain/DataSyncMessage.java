package org.troy.common.component.sync.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 数据同步的报文格式
 * 
 * @author 杨磊
 * @since 2014-4-24 下午5:17:47 Copyright © 2013 - 2014 CNNCT
 */
public class DataSyncMessage {
	/**
	 * ack为消息响应包 0:请求处理失败,1:请求处理成功,2:请求处理中
	 */
	private int ack = DataSyncConst.ACK_PROCESSING;
	/**
	 * 请求id 用于确认业务报文的完成情况(requestId由请求发起方生成,缓存在发起方是为了确认对方是否处理完成本次请求)
	 */
	private String requestId; 
	/**
	 * 业务数据 多表关联时通过sql的weight来确定优先级,一个List中的TransportSQL处于同一个事务中
	 */
	private List<TransportSQL> data;
	/**
	 * 用于传输额外的信息,例如错误信息
	 */
	private String extra; 

	public DataSyncMessage() {
	}

	public DataSyncMessage(String requestId) {
		this.requestId = requestId;
	}

	public int getAck() {
		return ack;
	}

	public void setAck(int ack) {
		this.ack = ack;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public List<TransportSQL> getData() {
		return data;
	}

	public void setData(List<TransportSQL> data) {
		//按照SQL的weight倒序, weight值越大 优先执行
		Collections.sort(data,new Comparator<TransportSQL>(){
            @Override
            public int compare(TransportSQL b1, TransportSQL b2) {
                return b1.getWeight()>b2.getWeight()?0:1;  
            }
        });
		this.data = data;
	}

}
