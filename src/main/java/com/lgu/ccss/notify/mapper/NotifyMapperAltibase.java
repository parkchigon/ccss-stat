package com.lgu.ccss.notify.mapper;

import com.lgu.ccss.config.annontation.Slave;
import com.lgu.ccss.notify.model.SmsVO;

@Slave
public interface NotifyMapperAltibase {
	int insertSmsSendQueue(SmsVO smsVO);
}
