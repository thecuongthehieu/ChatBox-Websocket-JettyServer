package com.dev;

import org.eclipse.jetty.websocket.servlet.*;

public class ChatWebSocketServlet extends WebSocketServlet {
	private static final int IDLE_TIMEOUT = 24 * 3600 * 1000; // 24 hours
	public static final ChatBox chatBox = new ChatBox();
	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.getPolicy().setIdleTimeout(IDLE_TIMEOUT);
		factory.setCreator(new WebSocketCreator() {
			@Override
			public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
				return new ChatWebSocket();
			}
		});
	}
}
