package com.dev;

import java.time.LocalDateTime;

public class Message {
	public static final String SET_USERNAME_ACTION = "setUserName";
	public static final String SEND_MESSAGE_ACTION = "sendMessage";
	public static final String JOIN_ACTION = "join";
	public static final String NEW_MESSAGE_ACTION = "newMessage";
	public static final String LEFT_ACTION = "left";

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
