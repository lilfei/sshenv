package com.sshenv.websocket.spring_ws1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class LlfWebSocketHandler extends TextWebSocketHandler {
	
	/**
	 * 记录连接上的所有session
	 */
	private Map<String, WebSocketSession> sessionMap = Collections
			.synchronizedMap(new HashMap<String, WebSocketSession>());

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 建立连接之后
		System.out.println("[{}:{}] has bean connected" + session.getUri() + ", " + session.getId());
		sessionMap.put(session.getId(), session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 收到消息之后
		String payload = message.getPayload();
		System.out.println("handleTextMessage: " + payload);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 连接断开之后
		System.out.println("[{}:{}] has bean closed" + session.getUri() + ", " + session.getId());
		sessionMap.remove(session.getId());

		if (sessionMap.isEmpty()) {

		}
	}
}
