package com.dev;

import com.google.gson.Gson;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;
// This object represents a session (socket connection)
public class ChatWebSocket extends WebSocketAdapter {
	private String username = null; // unique name

	@Override
	public void onWebSocketError(Throwable cause) {
		// should close socket
	}

	@Override
	public void onWebSocketText(String message) {
		Message messageObj = new Gson().fromJson(message, Message.class);
		if ("setUserName".equals(messageObj.getAction())) {
			String newUsername = messageObj.getValue();
			ChatWebSocketServlet.chatBox.addUser(newUsername, this);
		} else if ("sendMessage".equals(messageObj.getAction())) {
			String typedText = messageObj.getValue();
			ChatWebSocketServlet.chatBox.sendMessage(this, typedText);
		}
	}

	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		ChatWebSocketServlet.chatBox.removeUser(this);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUserName(String username) {
		this.username = username;
	}
}
