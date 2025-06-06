package fr.initiativedeuxsevres.ttm.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.initiativedeuxsevres.ttm.config.WebSocketMessageHandler;
import fr.initiativedeuxsevres.ttm.domain.models.Message;
import fr.initiativedeuxsevres.ttm.domain.services.MessageService;
import fr.initiativedeuxsevres.ttm.web.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/// service qui écoute Kafka
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    /// quand un message arrive sur ce topic
    @KafkaListener(topics = "message-topic", groupId = "messaging")
    public void listen(String message) throws JsonProcessingException {
        /// récupère l'utilisateur connecté si dispo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth != null ? auth.getName() : "sender";

        /// message enregistré en base
        Message savedMessage = messageService.saveMessage(message);

        /// prépare la réponse à envoyer aux clients websocket
        MessageResponse response = MessageResponse.builder()
                .id(savedMessage.getId())
                .content(savedMessage.getContent())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(response);

        /// envoie le message à tous les clients websocket connectés
        for (WebSocketSession session : WebSocketMessageHandler.getWebSocketSessions()) {
            try {
                session.sendMessage(new TextMessage(jsonResponse));
            } catch (IOException e) {
                log.error("Error sending message to WebSocket session: {}", e.getMessage());
            }
        }
    }
}