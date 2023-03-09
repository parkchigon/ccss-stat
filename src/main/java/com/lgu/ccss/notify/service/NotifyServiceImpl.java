package com.lgu.ccss.notify.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgu.ccss.common.util.DateUtils;
import com.lgu.ccss.notify.mapper.NotifyMapper;
import com.lgu.ccss.notify.model.ApiCallHisVO;
import com.lgu.ccss.notify.model.NotifyVO;
import com.lgu.ccss.notify.model.SmsVO;
import com.lgu.ccss.notify.model.TargetVO;
import com.lgu.ccss.notify.service.worker.ApiCallHisWorker;
import com.lgu.ccss.notify.service.worker.SMSQueueMessageWorker;

@Service
public class NotifyServiceImpl implements NotifyService{
	private final Logger logger = LoggerFactory.getLogger(NotifyServiceImpl.class);
	
	@Autowired
	private NotifyMapper notifyMapper;
	@Autowired
	private ApiCallHisWorker apiCallHisWorker;
	@Autowired
	private SMSQueueMessageWorker smsQueueMessageWorker;
	
	@Override
	public void doTask() {
		// TODO Auto-generated method stub
		logger.info("NotifyService Start!");
		
		String nowDt = DateUtils.getCurrentTime("yyyyMMddHHmmss");
		String beforeDt = DateUtils.getCurrentDate(Calendar.MINUTE, -1, "yyyyMMddHHmmss");
		String smsMsg = "";		
		logger.debug("Search Date : "+ beforeDt+" ~ "+nowDt);
		
		NotifyVO notifyVo = new NotifyVO();
		notifyVo.setBeforeDt(beforeDt);
		notifyVo.setNowDt(nowDt);
		
		ArrayList<ApiCallHisVO> apiCallHisList = new ArrayList<>();
		apiCallHisList = notifyMapper.selectNotifyStat(notifyVo);
		
		logger.debug("apiCallHis : "+apiCallHisList.size());
		
		if(apiCallHisList.size() > 0) {
			smsMsg = apiCallHisWorker.getMakeSMSMsg(apiCallHisList);
		}
		
		
		
		if(smsMsg == "") {
			logger.debug("Not Send SMS Message");
			
		}else {
			logger.debug("Send SMS Message to Admin");
			List<TargetVO> targetList = new ArrayList<>();
			targetList = notifyMapper.selectTargetList();
			
			
			for(int i=0; i< targetList.size();i++) {
				SmsVO smsVo = smsQueueMessageWorker.createSMSMessageQueue(targetList.get(i), smsMsg);
				
				logger.debug(smsVo.toString());
				
				notifyMapper.insertSmsSendQueue(smsVo);
			}
		}
	}

}
