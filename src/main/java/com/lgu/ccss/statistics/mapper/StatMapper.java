package com.lgu.ccss.statistics.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.ccss.statistics.model.StatVO;

@Component
public class StatMapper {
	@Autowired
	StatMapperOracle statMapperOracle;
	
	public int insertStatistics(List<StatVO> statList) {
		return statMapperOracle.insertStatHistory(statList);
	}
}
