package com.lgu.ccss.statistics.service.worker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lgu.ccss.statistics.constant.StatConst;
import com.lgu.ccss.statistics.model.StatVO;

@Component
public class StatSetWorker {
	private final Logger logger = LoggerFactory.getLogger(StatSetWorker.class);
	@Value("#{config['common.systemId']}")
	private String systemId;
	@Value("#{config['stat.blackList'].split(',')}")
	private List<String> blackList;
	
	public StatVO statStrToStatVo(String[] logLineList) {
		StatVO statVo = new StatVO();
		
		long elapsedTime = 0;
		
		Map<String,String> tloMap = new HashMap<String,String>();
		for(String logLine : logLineList) {
			String pair[] = logLine.split(StatConst.EQUAL);
			String key = pair[0];
			String value = "";
			if(pair.length == 2) {
				value = pair[1];
			}
			logger.debug("Map input Date [ key : "+key +" | value : "+value +" ]");
			tloMap.put(key, value);
		}
		
		if(tloMap.get(StatConst.STAT_REQ_TIME).equals("") || tloMap.get(StatConst.STAT_RSP_TIME).equals("")) {
			elapsedTime = 0;
		}else {
//			elapsedTime = Long.parseLong(tloMap.get(StatConst.STAT_RSP_TIME)) - Long.parseLong(tloMap.get(StatConst.STAT_REQ_TIME));
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			try {
				Date rspTime = transFormat.parse((tloMap.get(StatConst.STAT_RSP_TIME)));
				Date reqTime = transFormat.parse((tloMap.get(StatConst.STAT_REQ_TIME)));
				
				elapsedTime = (rspTime.getTime() - reqTime.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String ctn;
		if(tloMap.get(StatConst.STAT_SID) == null || tloMap.get(StatConst.STAT_SID).equals("")) {
			ctn = tloMap.get(StatConst.STAT_CLIENT_IP);
		}else {
			ctn = tloMap.get(StatConst.STAT_SID);
		}
		// 요청CTN
		// 처리WAS정보
		statVo.setMembId(tloMap.get(StatConst.STAT_MEMB_ID)); // 회원아이디
		statVo.setMembNo(tloMap.get(StatConst.STAT_MEMB_NO));
		statVo.setReqAppType(tloMap.get(StatConst.STAT_APP_TYPE)); // 요청앱유형
		statVo.setReqCtn(ctn);
		statVo.setApiNm(tloMap.get(StatConst.STAT_SVC_CLASS)); // API이름
		statVo.setResultStatus(tloMap.get(StatConst.STAT_RESULT_CODE)); // 처리결과
//		statVo.setProcessDt(String.valueOf(processDt)); // 처리시간 - 요청들어온시간부터 응답시간
		statVo.setProcessDt(tloMap.get(StatConst.STAT_REQ_TIME)); // 처리시간 - 요청들어온시간부터 응답시간
		statVo.setElapsedTime(String.valueOf(elapsedTime));
		statVo.setWasInfo(tloMap.get(StatConst.STAT_SERVER_ID)); 
		statVo.setSessionId(tloMap.get(StatConst.STAT_SESSION_ID));
//		statVo.setRegDt(tloMap.get(StatConst.STAT_REQ_TIME)); // 등록시간은 요청처리시간으로
		statVo.setRedId(systemId); // 시스템 아이디
		statVo.setDeviceType(tloMap.get(StatConst.STAT_DEVICE_TYPE));	//디바이스타입

		/** 2019.01.17 신규 추가 */
		statVo.setDeviceModelNm(tloMap.get(StatConst.STAT_DEV_MODEL));	//디바이스모델명
		/** 2019.01.17 신규 추가 */
		
		
		if(blackList.contains(statVo.getApiNm())) {
			return null;
		}
		
		return statVo;
	}
}
