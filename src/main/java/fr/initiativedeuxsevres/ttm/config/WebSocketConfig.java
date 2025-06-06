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

    /// injection de KafkaTemplate pour pouvoir envoyer les messages à Kafka
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    ///  enregistre un gestionnaire sur l'url /ws
    /// lorsqu'on se connecte à /ws, on utilise WebSocketMessageHandler pour la gestion des messages
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketMessageHandler(kafkaTemplate), "/ws").setAllowedOrigins("*");
    }
}
