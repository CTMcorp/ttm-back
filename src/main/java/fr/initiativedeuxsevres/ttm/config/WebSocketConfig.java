package fr.initiativedeuxsevres.ttm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    private final WebSocketMessageHandler webSocketMessageHandler;

    public WebSocketConfig(JwtHandshakeInterceptor jwtHandshakeInterceptor, WebSocketMessageHandler webSocketMessageHandler) {
        this.jwtHandshakeInterceptor = jwtHandshakeInterceptor;
        this.webSocketMessageHandler = webSocketMessageHandler;
    }

    ///  enregistre un gestionnaire sur l'url /ws
    /// lorsqu'on se connecte à /ws, on utilise WebSocketMessageHandler pour la gestion des messages
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketMessageHandler, "/ws")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins("http://localhost:5173")
                .withSockJS();
    }
}
