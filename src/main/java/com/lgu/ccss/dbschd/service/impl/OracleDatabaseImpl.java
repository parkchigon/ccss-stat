package com.lgu.ccss.dbschd.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgu.ccss.common.util.DateUtils;
import com.lgu.ccss.dbschd.mapper.oracle.OracleDAO;
import com.lgu.ccss.dbschd.model.CommonVo;
import com.lgu.ccss.dbschd.service.OracleDatabase;

@Service
public class OracleDatabaseImpl implements OracleDatabase {

	private final Logger logger = LoggerFactory.getLogger(OracleDatabaseImpl.class);

	@Autowired
	private OracleDAO oracleDAO;

	@Value("#{config['ora.table.name'].split(',')}")
	private String[] partionTableList;
	
	@Value("#{config['ora.partition.add.month']}")
	private String partitionAddMonth;

	public void processPartition() {
		CommonVo commonVo = new CommonVo();

		try {

			System.out.println("partionTableList.length: " + partionTableList.length);
			for (int idx = 0; idx < partionTableList.length; idx++) {
				String[] targetTable = partionTableList[idx].split(":");

				String tableNm = targetTable[0];
				int previousDelete = Integer.parseInt(targetTable[1]);
				logger.info("[TB_NM]:" + tableNm + " [previous Monte]:" + previousDelete);
				// dropDate = DateUtils.getCurrentMonth(-previousDelete,"yyMM");
				// commonVo.setPartitionName(tableNm+ "_P" + dropDate);
				commonVo.setTableName(tableNm);
				// commonVo.setPartitionMonth(dropDate);

				List<CommonVo> partitionMapList = getExistPartitionName(commonVo);
				
				for (int i = 0; i < partitionMapList.size(); i++) {

					String partitionName = partitionMapList.get(i).getPartitionName();

					String partitionDateName = partitionName.substring(partitionName.length()-4, partitionName.length());
					String monthBefore = DateUtils.getCurrentMonth(-previousDelete, "yyMM");
					
					Date partitionDate = DateUtils.getDate(partitionDateName, "yyMM");
					Date beforeDate = DateUtils.getDate(monthBefore, "yyMM");
					
					logger.info("######################################################################################");
					logger.info("[tableNm]: " + tableNm + " [partitionName]: " + partitionName
							+ " [partitionDate]: " + partitionDate + " [beforeDate]: " + beforeDate);
					logger.info("######################################################################################");
					
					if (partitionDate.compareTo(beforeDate) < 0) {

						commonVo.setPartitionName(partitionName);

						if (getExistPartition(commonVo)) {
							oracleDAO.deletePartition(commonVo);
							logger.info("DELETE  partition " + "[TB_NM]:" + commonVo.getTableName()
									+ "[Partition Name]:" + commonVo.getPartitionName());

						} else {
							logger.warn("Not exist partition " + "[TB_NM]:" + commonVo.getTableName()
									+ "[Partition Name]:" + commonVo.getPartitionName());
						}
					}
				}
				
				// 파티션 추가
				Integer addMonth = Integer.parseInt(partitionAddMonth);
				for (int j = 1; j <= addMonth; j++) {
					commonVo = new CommonVo();
					String createMonth = DateUtils.getCurrentMonth(j, "yyMM");
					String monthAfterNext = DateUtils.getCurrentMonth(j + 1, "yyyyMM");
					
					commonVo.setPartitionName(tableNm + "_P" + createMonth);
					commonVo.setTableName(tableNm);
					commonVo.setPartitionMonth(createMonth);
					commonVo.setPartitionDay(monthAfterNext);
					commonVo.setHighValue("to_date( '" + monthAfterNext + "01' , 'YYYYMMDD' )");

					if (getExistPartition(commonVo)) {
						logger.warn("exist partition " + commonVo.toString());
						
					} else {
						oracleDAO.createPartition(commonVo);
					}
					logger.info("Add  partition " + "[TB_NM]:" + commonVo.getTableName() + "[Partition Name]:"
							+ commonVo.getPartitionName());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Transactional
	public boolean getExistPartition(CommonVo commonVo) {
		logger.debug("## getExistPartition CommonVo : " + commonVo.toString());

		int isExist = oracleDAO.getPartition(commonVo);
		boolean isResult = false;
		if (isExist > 0) {
			isResult = true;
		}
		return isResult;
	}

	@Transactional
	public List<CommonVo> getExistPartitionName(CommonVo commonVo) {
		List<CommonVo> partitionNameList = oracleDAO.getExistPartitionName(commonVo);
		return partitionNameList;
	}

	public static void main(String[] args) {
		String name = "TB_API_CALL_HIS_B_P1510";
		
		String dateName = name.substring(name.length()-4, name.length());
		
		System.out.println(dateName);
	}
}
