package com.lgu.ccss.dbschd.scheduler;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lgu.ccss.dbschd.service.OracleDatabase;

@Service
public class OracleDBWorkerScheduler {
	private final Logger logger = LoggerFactory.getLogger(OracleDBWorkerScheduler.class);

	@Autowired
	private OracleDatabase oracleDatabase;

	@Scheduled(cron = "${ora.cron.partition.time}")
	//@Scheduled(cron = "* /1 * * * *")
	public void processPartition() {
		logger.info("### [ORACLE] CCSS SERVICE PROCESS PARTITION TASK DAEMON START ...");
		long startTime = System.currentTimeMillis();
		try {
			oracleDatabase.processPartition();
			logger.info("### [ORACLE] CCSS SERVICE PROCESS PARTITION TASK DAEMON END {}ms", (System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
