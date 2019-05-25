package com.sivadas.anand.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.hazelcast.leader.LeaderInitiator;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.leader.event.LeaderEventPublisher;

import com.sivadas.anand.node.NodeCandidate;

@Configuration
public class ServiceConfig {

	@Autowired
	public HazelcastConfiguration hazelcastConfiguration;
	
	@Autowired
	private LeaderEventPublisher leaderEventPublisher;
	
	@Value(value="${app.servicename}")
	private String serviceName;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Bean
	public Candidate nodeServiceCandidate() {
		
		return new NodeCandidate(getServiceName(), "");
	}
	
	@Bean
	public LeaderInitiator initator() {
		
		LeaderInitiator leaderInitiator = new LeaderInitiator(hazelcastConfiguration.hazelcastInstance(), nodeServiceCandidate());
		leaderInitiator.setLeaderEventPublisher(leaderEventPublisher);
		
		return leaderInitiator;
	}
	
	
}
