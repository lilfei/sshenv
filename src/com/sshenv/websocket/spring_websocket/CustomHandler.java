package com.sshenv.websocket.spring_websocket;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.sshenv.util.Constant;

@Component
public class CustomHandler extends TextWebSocketHandler {
	private Map<String, WebSocketSession> nameAndSession = new ConcurrentHashMap<>();

	// 建立连接时候触发
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		System.out.print("afterConnectionEstablished");
		String username = getNameFromSession(session);
		nameAndSession.putIfAbsent(username, session);
	}

	// 关闭连接时候触发
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		System.out.print("afterConnectionClosed");
		String username = getNameFromSession(session);
		nameAndSession.remove(username);
	}

	// 处理消息
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.print("handleTextMessage");
		// 防止中文乱码
		String msg = URLDecoder.decode(message.getPayload(), "utf-8");
		String username = getNameFromSession(session);
		// 简单模拟群发消息
		TextMessage reply = new TextMessage(username + " : " + msg);
		nameAndSession.forEach((s, webSocketSession) -> {
			try {
				webSocketSession.sendMessage(reply);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private String getNameFromSession(WebSocketSession session) {
		System.out.print("getNameFromSession");
		Map<String, Object> attributes = session.getAttributes();
		return (String) attributes.get(Constant.USER_NAME);
	}
}
