package com.sshenv.websocket.spring_ws1;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.socket.TextMessage;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private SocketHandler socketHandler;

	// 服务端Spring MVC拦截该HTTP请求，将HTTP Session载入Websocket Session中，建立会话
	// 加了参数则无法在struts的action中使用
	@RequestMapping(value = "/ws/spring11")
	public String spring11(HttpSession session) {
		logger.info("用户登录建立Websocket连接");
		session.setAttribute("user", "hejun");
		return "home";
	}

	// 模拟服务端发送消息，其中可实现消息的广发或指定对象发送
	@RequestMapping(value = "/ws/springMessage", method = RequestMethod.GET)
	public String springMessage() {
		double rand = Math.ceil(Math.random() * 100);
		socketHandler.sendMessageToUser("hejun", new TextMessage("Websocket测试消息" + rand));
		return "message";
	}
}
