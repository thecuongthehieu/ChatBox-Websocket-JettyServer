package com.dev;

import com.google.gson.Gson;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;

/**
 * This object represents a session (a socket connection)
 */
public class ChatWebSocket extends WebSocketAdapter {
	private String username = null; // unique name

	@Override
	public void onWebSocketError(Throwable cause) {
		// should close socket
	}

	@Override
	public void onWebSocketText(String message) {
		Message messageObj = new Gson().fromJson(message, Message.class);
		if (Message.ClientMessageAction.SET_USERNAME.value.equals(messageObj.getAction())) {
			String newUsername = messageObj.getValue();
			ChatWebSocketServlet.chatBox.addUser(newUsername, this);
		} else if (Message.ClientMessageAction.SEND_MESSAGE.value.equals(messageObj.getAction())) {
			String typedText = messageObj.getValue();
			ChatWebSocketServlet.chatBox.displayMessage(this, typedText);
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
