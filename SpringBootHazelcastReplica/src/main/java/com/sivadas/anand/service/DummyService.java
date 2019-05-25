package com.sivadas.anand.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.hazelcast.leader.LeaderInitiator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DummyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DummyService.class);
	
	@Autowired
	private LeaderInitiator leaderInitiator;
	
	@Scheduled(fixedRate = 3000)
	public void distributedInputJob() {
		
		boolean isLeader = leaderInitiator.getContext().isLeader();
		LOGGER.info("Node is leader : {}", isLeader);
		if (isLeader) {
			LOGGER.info("This is the leader printing the job...");
		}
		
	}
	
	
	
}
