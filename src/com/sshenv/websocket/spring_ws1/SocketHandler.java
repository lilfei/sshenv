package com.sshenv.websocket.spring_ws1;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class SocketHandler implements WebSocketHandler {

	private static final Logger logger;
	private static final ArrayList<WebSocketSession> users;

	static {
		users = new ArrayList<WebSocketSession>();
		logger = LoggerFactory.getLogger(SocketHandler.class);
	}

	// Websocket连接建立
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("成功建立Websocket连接");
		users.add(session);
		String username = session.getAttributes().get("user").toString();
		// 判断session中用户信息
		if (username != null) {
			session.sendMessage(new TextMessage("已成功建立Websocket通信"));
		}
	}

	@Override
	public void handleMessage(WebSocketSession arg0, WebSocketMessage<?> arg1) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("hahaha handlerMessage");
	}

	// 当连接出错时，主动关闭当前连接，并从会话列表中删除该会话
	@Override
	public void handleTransportError(WebSocketSession session, Throwable error) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		logger.error("连接出现错误:" + error.toString());
		users.remove(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 */
	public void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession user : users) {
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给某个用户发送消息
	 *
	 * @param userName
	 * @param message
	 */
	public void sendMessageToUser(String userName, TextMessage message) {
		System.out.println("userName: " + userName + ", message: " + message);
		for (WebSocketSession user : users) {
			if (user.getAttributes().get("user").equals(userName)) {
				try {
					if (user.isOpen()) {
						user.sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("Websocket连接已关闭");
		users.remove(session);
	}
}
