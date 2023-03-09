package com.lgu.ccss.dbschd.service.impl;

import org.springframework.stereotype.Service;

import com.lgu.ccss.dbschd.service.AltiDatabase;


@Service
public class AltiDatabaseImpl implements AltiDatabase {
	/*
	private final Logger logger = LoggerFactory.getLogger(AltiDatabaseImpl.class);

	@Autowired
	private AltibaseDAO altibaseDAO;
	
	@Value("#{config['alti.table.name'].split(',')}")
	private String[] partionTableList;
	
	
	public void processPartition() {
		
		StringBuffer sb = new StringBuffer();
		CommonVo commonVo = new CommonVo();
		int dropPartition = -1;
		int addPartition = -1;
		String dropDate;
		
		try {
			
			for(int idx=0; idx < partionTableList.length ; idx ++){
				String[] targetTable = partionTableList[idx].split(":");
				
			
				String tableNm = targetTable[0];
				int previousDelete = Integer.parseInt(targetTable[1]);
				logger.info("TB_NM : " + tableNm + " previous Monteh"+ previousDelete );
				dropDate = DateUtils.getCurrentMonth(previousDelete,"yyMM");
				commonVo.setPartitionName(tableNm+ "_P" + dropDate);
				commonVo.setTableName(tableNm);
				commonVo.setPartitionMonth(dropDate);
			
				if (getExistPartition(commonVo)) {
					dropPartition = altibaseDAO.deletePartition(commonVo);
				} else {
					logger.warn("not exist partition " + commonVo.toString());
				}
				sb.append("DELETE PARTITION(" + dropPartition + ", " + dropDate + "), ");
				
				
				commonVo = new CommonVo();
				String createMonth = DateUtils.getCurrentMonth(1,"yyMM");
				String monthAfterNext = DateUtils.getCurrentMonth(2,"yyMM");
				commonVo.setPartitionName(tableNm+ "_P" + "_" + createMonth);
				commonVo.setTableName(tableNm);
				commonVo.setPartitionMonth(createMonth);
				commonVo.setPartitionDay(monthAfterNext);

				if (getExistPartition(commonVo)) {
					logger.warn("exist partition " + commonVo.toString());
				} else {
					addPartition = altibaseDAO.createPartition(commonVo);
				}
				sb.append("ADD PARTITION(" + addPartition + ", " + createMonth + ")");
				logger.info(sb.toString());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}


	@Transactional
	public boolean getExistPartition(CommonVo commonVo) {
		int isExist = altibaseDAO.getPartition(commonVo);
		boolean isResult = false;
		if (isExist > 0) {
			isResult = true;
		}
		return isResult;
	}
	*/

}
