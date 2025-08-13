package fr.initiativedeuxsevres.ttm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class WebSocketHandlerConfig {
    @Bean
    public WebSocketMessageHandler webSocketMessageHandler(JwtTokenProvider jwtTokenProvider,
                                                           KafkaTemplate<String, String> kafkaTemplate) {
        return new WebSocketMessageHandler(jwtTokenProvider, kafkaTemplate);
    }
}