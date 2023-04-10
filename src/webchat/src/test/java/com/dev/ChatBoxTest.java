package com.dev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChatBoxTest {
	private ChatBox chatBox;

	@BeforeEach
	public void initChatBox() {
		this.chatBox = new ChatBox();
	}

	@Test
	public void testAddUser() {
		assertEquals(this.chatBox.getNumberOfClients(), 0);
		this.chatBox.addUser("user1", new ChatWebSocket());
		assertEquals(this.chatBox.getNumberOfClients(), 1);
		this.chatBox.addUser("user2", new ChatWebSocket());
		assertEquals(this.chatBox.getNumberOfClients(), 2);
	}

	@Test
	public void testRemoveUser() {
		assertEquals(this.chatBox.getNumberOfClients(), 0);
		ChatWebSocket user1WebSocket = new ChatWebSocket();
		ChatWebSocket user2WebSocket = new ChatWebSocket();
		this.chatBox.addUser("user1", user1WebSocket);
		this.chatBox.addUser("user2", user2WebSocket);
		assertEquals(this.chatBox.getNumberOfClients(), 2);

		this.chatBox.removeUser(user1WebSocket);
		assertEquals(this.chatBox.getNumberOfClients(), 1);
		this.chatBox.removeUser(user2WebSocket);
		assertEquals(this.chatBox.getNumberOfClients(), 0);
	}
}
