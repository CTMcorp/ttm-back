package fr.initiativedeuxsevres.ttm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.initiativedeuxsevres.ttm.web.dto.MessageRequest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/// Gestionnnaire des messages
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;
    /// liste des sessions web socket connectées
    private static final List<WebSocketSession> webSocketSessions = new CopyOnWriteArrayList<>();

    public WebSocketMessageHandler(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        /// ajoute la session à la liste quand un client se connecte
        webSocketSessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ///  recoit un message texte du client websocket
        ObjectMapper objectMapper = new ObjectMapper();
        /// lors d'un envoi de message : transformé en objet MessageRequest
        MessageRequest messageRequest = objectMapper.readValue(message.getPayload(), MessageRequest.class);
        /// puis envoyé à kafka sur le topic "message-topic"
        kafkaTemplate.send("message-topic", messageRequest.getContent());
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
