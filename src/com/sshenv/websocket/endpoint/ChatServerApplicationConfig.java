package com.sshenv.websocket.endpoint;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

public class ChatServerApplicationConfig implements ServerApplicationConfig {
	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(
			Set<Class<?>> scanned) {
		return scanned;
	}

	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> scanned) {
		Set<ServerEndpointConfig> result = new HashSet<>();
		if (scanned.contains(ChatEndPoint.class)) {
			result.add(ServerEndpointConfig.Builder
					.create(ChatEndPoint.class,"/websocket/endpoint")
					.build());
		}
		return result;
	}
}
