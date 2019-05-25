package com.sivadas.anand.node;

import org.springframework.integration.leader.DefaultCandidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.leader.Context;

public class NodeCandidate extends DefaultCandidate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NodeCandidate.class);
	
	public NodeCandidate(String nodeId, String role) {
		super(nodeId, role);
	}
	
	@Override
	public void onGranted(Context context) {
		super.onGranted(context);
		LOGGER.info("Leader granted to: {}", context.toString());
	}
	
	@Override
	public void onRevoked(Context context) {
		super.onRevoked(context);
		LOGGER.info("Leader revoked to: {}", context.toString());
	}
	
}
