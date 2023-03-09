package com.lgu.ccss.dbschd.model;

public class CommonVo {

	private String partitionDay;
	private String partitionMonth;
	private String tableName;
	private String partitionName;
	
	private String highValue;

	public String getPartitionDay() {
		return partitionDay;
	}

	public void setPartitionDay(String partitionDay) {
		this.partitionDay = partitionDay;
	}

	public String getPartitionMonth() {
		return partitionMonth;
	}

	public void setPartitionMonth(String partitionMonth) {
		this.partitionMonth = partitionMonth;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(String partitionName) {
		this.partitionName = partitionName;
	}
	
	
	public String getHighValue() {
		return highValue;
	}

	public void setHighValue(String highValue) {
		this.highValue = highValue;
	}

	@Override
	public String toString() {
		return "CommonVo [partitionDay=" + partitionDay + ", partitionMonth="
				+ partitionMonth + ", tableName=" + tableName
				+ ", partitionName=" + partitionName + ", highValue="
				+ highValue + "]";
	}

	

	

	

}
