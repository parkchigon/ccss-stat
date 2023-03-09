package com.lgu.ccss.statistics.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lgu.ccss.statistics.service.StatService;



@Service
public class StatScheduler {
	private final Logger logger = LoggerFactory.getLogger(StatScheduler.class);

	@Autowired
	private StatService statService;
	
	//0 0/5 14,18 * * ?
	@Scheduled(cron = "${stat.delay.cron}")
	public void startWork() {
		try {
			logger.info("###### START STAT DAEMON #####");		
			statService.doTask();

		} catch (Exception e) {
			logger.error("{}", e);

		} finally {
			logger.info("###### END STAT DAEMON #####");
		}
	}
}
