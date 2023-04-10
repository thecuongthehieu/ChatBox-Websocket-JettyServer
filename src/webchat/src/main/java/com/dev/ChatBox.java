package com.dev;

import com.google.gson.Gson;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChatBox {
	private final ConcurrentMap<String, ChatWebSocket> userNameToWebSocketMap;
	private final ChatBoxMonitor chatBoxMonitor = ChatBoxMonitor.getInstance();

	ChatBox() {
		this.userNameToWebSocketMap = new ConcurrentHashMap<>();
	}

	public void addUser(String newUsername, ChatWebSocket newUserWebSocket) {
		if (this.userNameToWebSocketMap.get(newUsername) != null) {
			newUserWebSocket.getSession().close();
			return;
		}
		newUserWebSocket.setUserName(newUsername);
		this.userNameToWebSocketMap.put(newUsername, newUserWebSocket);
		Message addUserMessageObj = new Message(Message.ServerMessageAction.JOIN.value, newUsername, String.valueOf(this.getNumberOfClients()));
		broadCastMessage(addUserMessageObj);
		chatBoxMonitor.onAddUser();
	}

	public void removeUser(ChatWebSocket leftUserWebSocket) {
		String leftUserName = leftUserWebSocket.getUsername();
		if (leftUserName != null) {
			userNameToWebSocketMap.remove(leftUserName);
			Message removeUserMessageObj = new Message(Message.ServerMessageAction.LEFT.value, leftUserName, String.valueOf(this.getNumberOfClients()));
			broadCastMessage(removeUserMessageObj);
			chatBoxMonitor.onRemoveUser();
		}
	}

	public void displayMessage(ChatWebSocket sender, String messageText) {
		Message displayedMessageObj = new Message(Message.ServerMessageAction.NEW_MESSAGE.value, sender.getUsername(), messageText);
		broadCastMessage(displayedMessageObj);
	}

	public void broadCastMessage(Message messageObj) {
		for (ChatWebSocket receiverWebSocket : this.getAllClientSockets()) {
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

	public int getNumberOfClients() {
		return this.userNameToWebSocketMap.size();
	}

	public Collection<ChatWebSocket> getAllClientSockets() {
		return this.userNameToWebSocketMap.values();
	}
}
