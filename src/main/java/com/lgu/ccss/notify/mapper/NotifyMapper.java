package com.lgu.ccss.notify.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.ccss.notify.model.ApiCallHisVO;
import com.lgu.ccss.notify.model.NotifyVO;
import com.lgu.ccss.notify.model.SmsVO;
import com.lgu.ccss.notify.model.TargetVO;

@Component
public class NotifyMapper {
	@Autowired
	NotifyMapperOracle notifyMapperOracle;
	@Autowired
	NotifyMapperAltibase notifyMapperAltibase;
	
	public ArrayList<ApiCallHisVO> selectNotifyStat(NotifyVO notifyVo){
		return notifyMapperOracle.selectNotifyStat(notifyVo);
	}
	
	public int insertSmsSendQueue(SmsVO smsVo) {
		return notifyMapperAltibase.insertSmsSendQueue(smsVo);
	}
	
//	public int insertSmsSendQueue(List<SmsVO> smsVo) {
//		int result;
//		logger.debug("Insert Sms Send Notify Queue!");
//		result = notifyMapperAltibase.insertSmsSendQueue(smsVo);
//		
//		return result;
//	}

	public List<TargetVO> selectTargetList() {
		// TODO Auto-generated method stub
		return notifyMapperOracle.selectTargetList();
	}
}
