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
		Message addUserMessageObj = new Message(Message.JOIN_ACTION, newUsername, String.valueOf(this.userNameToWebSocketMap.size()));
		broadCastMessage(newUserWebSocket, addUserMessageObj);
	}

	public void removeUser(ChatWebSocket leftUserWebSocket) {
		String leftUserName = leftUserWebSocket.getUsername();
		if (leftUserName != null) {
			userNameToWebSocketMap.remove(leftUserName);
			Message leftUserMessageObj = new Message(Message.LEFT_ACTION, leftUserName, String.valueOf(this.userNameToWebSocketMap.size()));
			broadCastMessage(leftUserWebSocket, leftUserMessageObj);
		}
	}

	public void sendMessage(ChatWebSocket sender, String messageValue) {
		Message newMesseageObj = new Message(Message.NEW_MESSAGE_ACTION, sender.getUsername(), messageValue);
		broadCastMessage(sender, newMesseageObj);
	}

	public void broadCastMessage(ChatWebSocket sender, Message messageObj) {
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
