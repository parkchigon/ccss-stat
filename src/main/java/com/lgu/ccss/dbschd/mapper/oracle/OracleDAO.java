package com.lgu.ccss.dbschd.mapper.oracle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.ccss.dbschd.model.CommonVo;



@Component
public class OracleDAO {

	@Autowired
	OracleDAOMaster oracleDAOMaster;
	/** SELECT */
	public int getPartition(CommonVo commonVo) {
		return oracleDAOMaster.getPartition(commonVo);
	}
	/** ADD */
	public int createPartition(CommonVo commonVo) {
		return oracleDAOMaster.createPartition(commonVo);
	}
	/** DROP */
	public int deletePartition(CommonVo commonVo) {
		return oracleDAOMaster.deletePartition(commonVo);
	}
	
	/** DROP */
	public List<CommonVo> getExistPartitionName(CommonVo commonVo) {
		return oracleDAOMaster.getExistPartitionName(commonVo);
	}
	
}
