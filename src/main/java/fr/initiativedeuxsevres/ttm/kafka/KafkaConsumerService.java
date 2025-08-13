package fr.initiativedeuxsevres.ttm.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.initiativedeuxsevres.ttm.config.WebSocketMessageHandler;
import fr.initiativedeuxsevres.ttm.domain.models.Message;
import fr.initiativedeuxsevres.ttm.domain.services.MessageService;
import fr.initiativedeuxsevres.ttm.web.dto.MessageRequest;
import fr.initiativedeuxsevres.ttm.web.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
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
    public void listen(String messageJson) throws JsonProcessingException {

        ///  désérialisation du message reçu
        MessageRequest messageRequest = objectMapper.readValue(messageJson, MessageRequest.class);

        ///  enregistrement du message avec sender et receiver
        Message savedMessage = messageService.saveMessage(
                messageRequest.getContent(),
                messageRequest.getSender(),
                messageRequest.getReceiver()
        );

        /// prépare la réponse à envoyer aux clients websocket
        MessageResponse response = MessageResponse.builder()
                .id(savedMessage.getId())
                .content(savedMessage.getContent())
                .sender(messageRequest.getSender())
                .receiver(messageRequest.getReceiver())
                .build();

        String jsonResponse = objectMapper.writeValueAsString(response);
        log.info("Sending message: {}", jsonResponse);

        /// envoie le message à tous les clients websocket connectés
        for (WebSocketSession session : WebSocketMessageHandler.getWebSocketSessions()) {
            String sessionUsername = session.getAttributes().get("username").toString();
            if (sessionUsername != null && sessionUsername.equals(messageRequest.getSender())) {
                try {
                    session.sendMessage(new TextMessage(jsonResponse));
                } catch (IOException e) {
                    log.error("Error sending message to WebSocket session: {}", e.getMessage());
                }
            }
        }
    }
}