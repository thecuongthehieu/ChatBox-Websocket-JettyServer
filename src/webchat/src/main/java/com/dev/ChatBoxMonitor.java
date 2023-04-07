package com.dev;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;

public class ChatBoxMonitor {
	private final Gauge numOnlineUsers;
	ChatBoxMonitor() {
		CollectorRegistry registry = CollectorRegistry.defaultRegistry;
		numOnlineUsers = Gauge.build("chatbox:numOnlineUsers", "numOnlineUsers").register(registry);
	}
	private static final ChatBoxMonitor INSTANCE = new ChatBoxMonitor();
	public static final ChatBoxMonitor getInstance() {
		return INSTANCE;
	}

	public void onAddUser() {
		this.numOnlineUsers.inc();
	}

	public void onRemoveUser() {
		this.numOnlineUsers.dec();
	}
}
