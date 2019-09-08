package com.sshenv.websocket.servlet_websocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket/servlet/{param}")
public class WebServelt extends HttpServlet {

	private HttpServletRequest request;
	private HttpServletResponse response;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4853458796319080467L;

//	@OnOpen
//	public void onOpen() {
//		System.out.println("WebServlet: WebSocket onOpen");
//	}

	@OnOpen
	public void onOpen(Session session, @PathParam("param") String param) {
		System.out.println("WebSocket onOpen: p1 => " + param);
		
		// TODO 添加对参数的处理
		
		Map<String, String> map = session.getPathParameters();
		System.out.println("WebSocket onOpen: session.getPathParameters() " + map.toString());

		String uri = session.getRequestURI().toString();
		System.out.println("WebSocket onOpen: session.getRequestURI().toString() " + uri);
	}

	@OnClose
	public void onClose() {
		System.out.println("WebServlet: WebSocket onClose");
	}

	@OnMessage
	public void onMessage(Session session, String msg) {
		System.out.println("WebServlet: WebSocket onMessage: " + msg);
		if (session.isOpen()) {
			try {
				// 将websocket传过来的值返回回去
				session.getBasicRemote().sendText("haha, i have received msg: " + msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
