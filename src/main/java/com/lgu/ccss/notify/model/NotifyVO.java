package com.lgu.ccss.notify.model;

public class NotifyVO {
	private String beforeDt;
	private String nowDt;
	
	
	public String getBeforeDt() {
		return beforeDt;
	}


	public void setBeforeDt(String beforeDt) {
		this.beforeDt = beforeDt;
	}


	public String getNowDt() {
		return nowDt;
	}


	public void setNowDt(String nowDt) {
		this.nowDt = nowDt;
	}


	@Override
	public String toString() {
		return "NotifyVO [beforeDt=" + beforeDt + ", nowDt=" + nowDt + "]";
	}
	
	
}
