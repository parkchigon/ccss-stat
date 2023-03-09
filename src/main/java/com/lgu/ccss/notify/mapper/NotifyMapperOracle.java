package com.lgu.ccss.notify.mapper;

import java.util.ArrayList;

import com.lgu.ccss.config.annontation.Master;
import com.lgu.ccss.notify.model.ApiCallHisVO;
import com.lgu.ccss.notify.model.NotifyVO;
import com.lgu.ccss.notify.model.TargetVO;

@Master
public interface NotifyMapperOracle {
	ArrayList<ApiCallHisVO> selectNotifyStat(NotifyVO notifyVo);

	ArrayList<TargetVO> selectTargetList();
}
