package com.lgu.ccss.notify.service.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lgu.ccss.common.util.DateUtils;
import com.lgu.ccss.common.util.keygenerator.KeyGenerator;
import com.lgu.ccss.notify.constant.SMSMessageConst;
import com.lgu.ccss.notify.model.SmsVO;
import com.lgu.ccss.notify.model.TargetVO;

@Component
public class SMSQueueMessageWorker {
	private final Logger logger = LoggerFactory.getLogger(SMSQueueMessageWorker.class);
	
	@Value("#{systemProperties.SERVER_ID}")
	private String svrId;
	@Value("#{config['notify.minValue']}")
	private int minValue;
	@Value("#{config['notify.maxValue']}")
	private int maxValue;
	@Value("#{config['common.systemId']}")
	private String systemId;
	@Value("#{config['notify.callBackNum']}")
	private String callBackNum;
	
	
	public SmsVO createSMSMessageQueue(TargetVO target, String smsMsg) {
		String msgId = "";
		
		SmsVO smsVo = new SmsVO();
		
		smsVo.setMsgStatus(SMSMessageConst.SMS_STATUS_WAIT_VAL);
		smsVo.setMsgTitle(SMSMessageConst.SMS_NOTIFY_TITLE);
		smsVo.setMsgCont(smsMsg);
		
		smsVo.setSendType(SMSMessageConst.SMS_SEND_TYPE);
		smsVo.setRegId(systemId);
		smsVo.setUpdId(systemId);
		smsVo.setSendDt(DateUtils.getCurrentTime("yyyyMMddHHmmss"));
		smsVo.setSvrId(svrId);
		smsVo.setOrgNo(callBackNum);
		smsVo.setCallbackNo(callBackNum);
		smsVo.setSendTryCnt(0);
		try {
			msgId = KeyGenerator.createCommonShardKey();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("InterruptedException : ",e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception : ",e);
		}
		smsVo.setMsgId(msgId);
		smsVo.setRecvPhoneNo(target.getClphNo());
		
		
		logger.debug("=====SMS Message!=====");
		logger.debug(smsVo.toString());
		logger.debug("=======================");
		return smsVo;
	}
	
//	public List<SmsVO> createSMSMessageQueue(List<TargetListVO> targetList, String smsMsg) {
//		logger.debug("createSMSMessageQueue!");
//		
//		List smsVoList = new ArrayList<>();
//		int msgId = 0;
//		
//		
//		for(int i=0;i<targetList.size();i++) {
//			SmsVO smsVo = new SmsVO();
//			
//			smsVo.setMsgStatus(SMSMessageConst.SMS_STATUS_WAIT_VAL);
//			smsVo.setMsgTitle(SMSMessageConst.SMS_NOTIFY_TITLE);
//			smsVo.setMsgCont(smsMsg);
//			
//			smsVo.setSendType("");
//			smsVo.setRegId(systemId);
//			smsVo.setUpdId(systemId);
//			smsVo.setSendDt(DateUtils.getCurrentTime("yyyyMMddHHmmss"));
//			smsVo.setSvrId(svrId);
//			smsVo.setOrgNo(callBackNum);
//			smsVo.setCallbackNo(callBackNum);
//			smsVo.setSendTryCnt(0);
//			
//			logger.debug("=====SMS Message!=====");
//			logger.debug(smsVo.toString());
//			logger.debug("-----Target List!-----");
//			
//			try {
//				msgId = Integer.parseInt(KeyGenerator.createSmsMagId(svrId, instanceId, minValue, maxValue));
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			logger.debug("MsgId : "+msgId);
//			smsVo.setMsgId(msgId);
//			TargetListVO target = targetList.get(i);
//			logger.debug("Target : "+target.getMngrId());
//			smsVo.setRecvPhoneNo(target.getClphNo());
//			smsVoList.add(smsVo);
//		}
//		logger.debug("=======================");
//		return smsVoList;
//	}
}
