package com.sivadas.anand.conf;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.leader.event.DefaultLeaderEventPublisher;
import org.springframework.integration.leader.event.LeaderEventPublisher;
import org.springframework.messaging.MessageChannel;

import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.PartitionGroupConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.config.PartitionGroupConfig.MemberGroupType;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelcastConfiguration {

	@Value(value = "${hazelcast.port}")
	private String portNumber;

	@Bean
	public HazelcastInstance hazelcastInstance() {

		final Config config = new Config();
		NetworkConfig networkConfig = new NetworkConfig();
		networkConfig.setPort(Integer.valueOf(portNumber));
		networkConfig.setPortAutoIncrement(Boolean.TRUE);
		networkConfig.setPortCount(20);

		MulticastConfig multicastConfig = new MulticastConfig();
		multicastConfig.setEnabled(Boolean.FALSE);
		JoinConfig joinConfig = new JoinConfig();
		joinConfig.setMulticastConfig(multicastConfig);

		String[] members = { "machine1", "node1", "localhost" };
		List<String> memeberList = Arrays.asList(members);
		TcpIpConfig tcpIpConfig = new TcpIpConfig();
		tcpIpConfig.setEnabled(Boolean.TRUE);
		tcpIpConfig.setMembers(memeberList);

		joinConfig.setTcpIpConfig(tcpIpConfig);
		networkConfig.setJoin(joinConfig);

		String[] interfaces = {"127.0.0.1"};
		List<String> interfacesList = Arrays.asList(interfaces);
		InterfacesConfig interfacesConfig = new InterfacesConfig();
		interfacesConfig.setEnabled(Boolean.FALSE);
		interfacesConfig.setInterfaces(interfacesList);
		networkConfig.setInterfaces(interfacesConfig);

		config.setNetworkConfig(networkConfig);
		PartitionGroupConfig partitionGroupConfig = config.getPartitionGroupConfig();
		partitionGroupConfig.setEnabled(Boolean.TRUE).setGroupType(MemberGroupType.HOST_AWARE);

		return Hazelcast.newHazelcastInstance(config);
	}

	@Bean
	public MessageChannel inputJobChannel() {

		return new DirectChannel();
	}

	@Bean
	public LeaderEventPublisher leaderEventPublisher() {

		return new DefaultLeaderEventPublisher();
	}

}
