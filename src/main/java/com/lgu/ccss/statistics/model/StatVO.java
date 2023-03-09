package com.lgu.ccss.statistics.model;

public class StatVO {
	private String apiHisSeq;
	private String membId;
	private String membNo;
	private String reqAppType;
	private String reqCtn;
	private String apiNm;
	private String resultStatus;
	private String processDt;
	private String elapsedTime;
	private String wasInfo;
	private String sessionId;
	private String regDt;
	private String redId;
	private String svrId;
	private String deviceType;
	
	/** 2019.01.16 Nissan_Leaf 추가개발용 컬럼 추가 */
	private String deviceModelNm;
	
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getApiHisSeq() {
		return apiHisSeq;
	}
	public void setApiHisSeq(String apiHisSeq) {
		this.apiHisSeq = apiHisSeq;
	}
	public String getMembId() {
		return membId;
	}
	public void setMembId(String membId) {
		this.membId = membId;
	}
	public String getMembNo() {
		return membNo;
	}
	public void setMembNo(String membNo) {
		this.membNo = membNo;
	}
	public String getReqAppType() {
		return reqAppType;
	}
	public void setReqAppType(String reqAppType) {
		this.reqAppType = reqAppType;
	}
	public String getReqCtn() {
		return reqCtn;
	}
	public void setReqCtn(String reqCtn) {
		this.reqCtn = reqCtn;
	}
	public String getApiNm() {
		return apiNm;
	}
	public void setApiNm(String apiNm) {
		this.apiNm = apiNm;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getProcessDt() {
		return processDt;
	}
	public void setProcessDt(String processDt) {
		this.processDt = processDt;
	}
	public String getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public String getWasInfo() {
		return wasInfo;
	}
	public void setWasInfo(String wasInfo) {
		this.wasInfo = wasInfo;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRedId() {
		return redId;
	}
	public void setRedId(String redId) {
		this.redId = redId;
	}
	public String getSvrId() {
		return svrId;
	}
	public void setSvrId(String svrId) {
		this.svrId = svrId;
	}
	public String getDeviceModelNm() {
		return deviceModelNm;
	}
	public void setDeviceModelNm(String deviceModelNm) {
		this.deviceModelNm = deviceModelNm;
	}
	@Override
	public String toString() {
		return "StatVO [apiHisSeq=" + apiHisSeq + ", membId=" + membId + ", membNo=" + membNo + ", reqAppType="
				+ reqAppType + ", reqCtn=" + reqCtn + ", apiNm=" + apiNm + ", resultStatus=" + resultStatus
				+ ", processDt=" + processDt + ", elapsedTime=" + elapsedTime + ", wasInfo=" + wasInfo + ", sessionId="
				+ sessionId + ", regDt=" + regDt + ", redId=" + redId + ", svrId=" + svrId + ", deviceModelNm=" + deviceModelNm + "]";
	}
	
	
	
	
}
