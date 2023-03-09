package com.lgu.ccss.notify.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lgu.ccss.notify.service.NotifyService;

@Service
public class NotifyScheduler {
	private final Logger logger = LoggerFactory.getLogger(NotifyScheduler.class);
	
	@Autowired
	private NotifyService notifyService;
	
	@Scheduled(cron="${notify.delay.cron}")
	public void startWork() {
		try {
			logger.info("###### START NOTIFY DAEMON #####");		
			notifyService.doTask();

		} catch (Exception e) {
			logger.error("{}", e);

		} finally {
			logger.info("###### END NOTIFY DAEMON #####");
		}
	}
}
