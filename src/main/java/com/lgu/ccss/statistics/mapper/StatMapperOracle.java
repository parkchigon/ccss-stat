package com.lgu.ccss.statistics.mapper;

import java.util.List;

import com.lgu.ccss.config.annontation.Master;
import com.lgu.ccss.statistics.model.StatVO;


@Master
public interface StatMapperOracle {
	int insertStatHistory(List<StatVO> statVo);

}
