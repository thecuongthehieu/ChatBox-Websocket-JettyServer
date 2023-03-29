package com.dev;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class ChatWebSocketServlet extends WebSocketServlet {
	public static final ChatBox chatBox = new ChatBox();
	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.getPolicy().setIdleTimeout(24 * 3600 * 1000);
		factory.setCreator((req, resp) -> new ChatWebSocket());
	}
}
