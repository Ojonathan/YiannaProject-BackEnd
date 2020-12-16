package be.yianna.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${websocket.url}")
    private String url;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // These are endpoints the client can subscribes to.
        config.enableSimpleBroker( "/topic", "/user");
        //config.setUserDestinationPrefix("/user");
        // topic and queue. They follow the convention that destinations for messages to be carried
        // on to all subscribed clients via the pub-sub model should be prefixed with topic.
        // On the other hand, destinations for private messages are typically prefixed by queue.

        // Message received with one of those below destinationPrefixes will be automatically
        // router to controllers @MessageMapping , that means /app/chat for example
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Handshake endpoint


        registry
                .addEndpoint("/ws")
                .setAllowedOrigins(url)
                .withSockJS();
    }
}