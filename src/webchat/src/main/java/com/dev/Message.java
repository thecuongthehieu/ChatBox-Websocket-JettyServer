package com.dev;

import java.time.LocalDateTime;

public class Message {
	public static enum ClientMessageAction {
		SET_USERNAME("setUserName"),
		SEND_MESSAGE("sendMessage");
		public final String value;

		private ClientMessageAction(String value) {
			this.value = value;
		}
	}

	public static enum ServerMessageAction {
		JOIN("join"),
		NEW_MESSAGE("newMessage"),
		LEFT("left");
		public final String value;

		private ServerMessageAction(String value) {
			this.value = value;
		}
	}

	private String action;
	private String value;
	private String username;
	private String date;

	public Message(String action, String username, String value) {
		this.action = action;
		this.username = username;
		this.value = value;
		this.date = LocalDateTime.now().toString();
	}

	public String getAction() {
		return action;
	}

	public String getValue() {
		return value;
	}

	public String getUsername() {
		return username;
	}

	public String getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Message{"
				+ "action=" + action
				+ ", value=" + value
				+ ", username=" + username
				+ ", date=" + date + '}';
	}
}
