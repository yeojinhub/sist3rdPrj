package kr.co.sist.user.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")  // WebSocket 엔드포인트 설정
                .setAllowedOrigins("http://localhost:8080")  // 특정 도메인만 허용 (예: localhost:8080)
                .withSockJS();  // SockJS 사용, 브라우저가 WebSocket을 지원하지 않으면 Fallback으로 사용
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  // 클라이언트가 받을 메시지 경로 설정
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트가 보낼 메시지 경로 설정
    }
}
