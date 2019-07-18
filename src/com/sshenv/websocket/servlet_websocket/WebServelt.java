package com.sshenv.websocket.servlet_websocket;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/wsServlet")
public class WebServelt extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4853458796319080467L;

	@OnOpen
	public void onOpen() {
		System.out.println("WEBopen");
	}

	@OnClose
	public void onClose() {
		System.out.println("WEBCLOSE");
	}

	@OnMessage
	public void onMessage(Session session, String msg) {
		System.out.println("send message: " + msg);
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
