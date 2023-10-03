package com.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.config.WebSocketConfig;

public class WebSocketConfigTest {

    @Test
    public void testWebSocketConfiguration() {
        // Create an instance of WebSocketConfig
        WebSocketConfig webSocketConfig = new WebSocketConfig();

        // Mock the StompEndpointRegistry and MessageBrokerRegistry
        StompEndpointRegistry stompEndpointRegistry = mock(StompEndpointRegistry.class);
        MessageBrokerRegistry messageBrokerRegistry = mock(MessageBrokerRegistry.class);

        // Call the registerStompEndpoints and configureMessageBroker methods
   //     webSocketConfig.registerStompEndpoints(stompEndpointRegistry);
        webSocketConfig.configureMessageBroker(messageBrokerRegistry);

        // Verify that the methods were called with the expected arguments
     //   verify(stompEndpointRegistry).addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
        verify(messageBrokerRegistry).setApplicationDestinationPrefixes("/app");
        verify(messageBrokerRegistry).enableSimpleBroker("/group", "/user");
        verify(messageBrokerRegistry).setUserDestinationPrefix("/user");
    }
}

