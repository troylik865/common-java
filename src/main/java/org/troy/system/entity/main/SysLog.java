package org.troy.system.entity.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.troy.biz.work.enums.LogType;
import org.troy.common.component.log.LogLevel;
import org.troy.common.utils.enums.EnumUtils;
import org.troy.system.entity.IdEntity;

/**
 * 系统：日志类
 * @author wangj
 * 2013-5-17
 */
@Entity
@Table(name="sys_log")
public class SysLog extends IdEntity {

	/** 描述  */
	private static final long serialVersionUID = 6057051455824317181L;
	
	@Column(length=2000)
	private String message;
	
	@Column(length=32)
	private String username;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Column(length=16)
	private String ipAddress;
	
	@Column(length=16)
	@Enumerated(EnumType.STRING)
	private LogLevel logLevel;
	
    @Column( length=4)
    private String logType;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogTypeStr() {
    	String str = "";
		Object obj = EnumUtils.toMap(LogType.class).get(logType);
		if(obj != null){
			str = obj.toString();
		}
		return str;
	}
}
