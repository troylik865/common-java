package org.troy.common.component.sync.domain;

import java.io.Serializable;

/**
 * Created by flyer on 2014/5/21.
 */
public class DataSyncCipher implements Serializable{
	private static final long serialVersionUID = -5193924811040241820L;
	private String cipher;
    private String sign;
    private String requestId;
    
    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
    
}
