package org.troy.common.component.sync.handler;

import org.troy.common.component.sync.domain.DataSyncCipher;

/**
 * 服务端监听消息处理类(接口)
 * @author 杨磊
 * @since 2014-4-24 下午4:47:16
 * Copyright © 2013 - 2014 CNNCT
 */
public interface ServerHandler {
	void handleMessage(DataSyncCipher cipher);
}
