package fr.initiativedeuxsevres.ttm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketMessageHandler webSocketMessageHandler;

    public WebSocketConfig( WebSocketMessageHandler webSocketMessageHandler) {
        this.webSocketMessageHandler = webSocketMessageHandler;
    }

    ///  enregistre un gestionnaire sur l'url /ws
    /// lorsqu'on se connecte à /ws, on utilise WebSocketMessageHandler pour la gestion des messages
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketMessageHandler, "/ws")
                .setAllowedOrigins("http://localhost:5173");
    }
}
