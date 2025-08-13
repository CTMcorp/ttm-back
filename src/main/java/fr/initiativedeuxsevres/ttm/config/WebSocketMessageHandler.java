package fr.initiativedeuxsevres.ttm.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.initiativedeuxsevres.ttm.web.dto.MessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/// Gestionnnaire des messages
@Slf4j
public class WebSocketMessageHandler extends TextWebSocketHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final KafkaTemplate<String, String> kafkaTemplate;
    /// liste des sessions web socket connectées
    private static final List<WebSocketSession> webSocketSessions = new CopyOnWriteArrayList<>();

    public WebSocketMessageHandler(JwtTokenProvider jwtTokenProvider, KafkaTemplate<String, String> kafkaTemplate) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            webSocketSessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ///  recoit un message texte du client websocket
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());
        String type = jsonNode.get("type").asText();

        if ("AUTH".equals(type)) {
            String token = jsonNode.get("token").asText();
            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsername(token);
                session.getAttributes().put("username", username);
            } else {
                session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Invalid token"));
            }
        } else if ("Message".equals(type)) {
            String content = jsonNode.get("content").asText();
            String receiver = jsonNode.get("receiver").asText();
            String sender = jsonNode.get("sender").asText();

            MessageRequest messageRequest = new MessageRequest(content, receiver, sender);
            String json = objectMapper.writeValueAsString(messageRequest);
            log.info("Sending message: {}", json);
            kafkaTemplate.send("message-topic", json);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        /// supprime la session de la liste quand un client se déconnecte
        webSocketSessions.remove(session);
    }

    /// getter qui garde une liste des sessions web socket ouvertes pour pouvoir renvoyer des messages plus tard
    public static List<WebSocketSession> getWebSocketSessions() {
        /// permet à d'autres classes d'accéder aux sessions actives
        return webSocketSessions;
    }
}
