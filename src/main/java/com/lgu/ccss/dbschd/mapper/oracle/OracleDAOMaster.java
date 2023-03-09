package com.lgu.ccss.dbschd.mapper.oracle;

import java.util.List;

import com.lgu.ccss.config.annontation.Master;
import com.lgu.ccss.dbschd.model.CommonVo;


@Master
public interface OracleDAOMaster {
	
	/** PARTITION validation.*/
	public int getPartition(CommonVo commonVo);

	public int createPartition(CommonVo commonVo);

	public int deletePartition(CommonVo commonVo);
	
	public List<CommonVo> getExistPartitionName(CommonVo commonVo);
	 
}
