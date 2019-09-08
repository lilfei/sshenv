package com.sshenv.websocket.spring_ws1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	private LlfWebSocketHandler llfWebSocketHandler;
	@Autowired
	private LlfHandshakeInterceptor llfHandshakeInterceptor;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		/** Douban FM */
		registry.addHandler(llfWebSocketHandler, "/websocket/springws")
				.addInterceptors(llfHandshakeInterceptor)
				.setAllowedOrigins("*")/* 不加AllowedOrigins，有可能会全拒绝 */;

//		registry.addHandler(llfWebSocketHandler, "/websocket/sockjs/springws").addInterceptors(llfHandshakeInterceptor)
//				.withSockJS();
		/* 用于支持SockJS */
		System.out.println("websocketHandler has registed!!");
	}
	
}
