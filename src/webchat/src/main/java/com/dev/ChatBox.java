package com.dev;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class ChatBox {
	private final Map<String, ChatWebSocket> userNameToWebSocketMap = new HashMap<>();

	public void addUser(String newUsername, ChatWebSocket newUserWebSocket) {
		if (this.userNameToWebSocketMap.get(newUsername) != null) {
			newUserWebSocket.getSession().close();
			return;
		}
		newUserWebSocket.setUserName(newUsername);
		this.userNameToWebSocketMap.put(newUsername, newUserWebSocket);
		Message addUserMessageObj = new Message(Message.ServerMessageAction.JOIN.value, newUsername, String.valueOf(this.userNameToWebSocketMap.size()));
		broadCastMessage(addUserMessageObj);
	}

	public void removeUser(ChatWebSocket leftUserWebSocket) {
		String leftUserName = leftUserWebSocket.getUsername();
		if (leftUserName != null) {
			userNameToWebSocketMap.remove(leftUserName);
			Message removeUserMessageObj = new Message(Message.ServerMessageAction.LEFT.value, leftUserName, String.valueOf(this.userNameToWebSocketMap.size()));
			broadCastMessage(removeUserMessageObj);
		}
	}

	public void displayMessage(ChatWebSocket sender, String messageText) {
		Message displayedMessageObj = new Message(Message.ServerMessageAction.NEW_MESSAGE.value, sender.getUsername(), messageText);
		broadCastMessage(displayedMessageObj);
	}

	public void broadCastMessage(Message messageObj) {
		for (ChatWebSocket receiverWebSocket : this.userNameToWebSocketMap.values()) {
			if (receiverWebSocket.isConnected()) {
				try {
					String message = new Gson().toJson(messageObj);
					receiverWebSocket.getRemote().sendString(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
