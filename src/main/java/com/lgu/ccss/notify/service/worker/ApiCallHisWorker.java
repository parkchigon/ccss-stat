package com.lgu.ccss.notify.service.worker;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lgu.ccss.notify.model.ApiCallHisVO;

@Component
public class ApiCallHisWorker {
	private final Logger logger = LoggerFactory.getLogger(ApiCallHisWorker.class);
	
	@Value("#{config['notify.sendRatio']}")
	private int sendRatio;
	
	public String getMakeSMSMsg(ArrayList<ApiCallHisVO> apiCallHisVoList) {
		int totalCnt = 0;
		int successCnt = 0;
		int failCnt = 0;
		int successRatio = 0;
		String smsMsg = "";
		
		for(ApiCallHisVO apiCallHisVo : apiCallHisVoList) {
			if(apiCallHisVo.getResultStatus().equals("2S000000")) {
				successCnt++;
			}
			totalCnt++;
		}
		
		successRatio = (successCnt*100/totalCnt);
		failCnt = totalCnt - successCnt;
		
		
		if(successRatio < sendRatio) {
			smsMsg = "전체 api호출 횟수 : "+totalCnt+"건, 성공 : "+successCnt+", 실패 : "+failCnt+", 성공률 : "+successRatio+"% 입니다.";
		}else {
			smsMsg = "";
		}
		logger.debug("smsMsg : "+smsMsg);
		return smsMsg;
	}
}
