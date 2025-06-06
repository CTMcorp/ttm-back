package fr.initiativedeuxsevres.ttm.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/// service utilitaire pour l'envoi des messages à kafka
/// utilise le kafkaTemplate pour publier un message sur un topic
@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /// pour envoyer un message à un topic
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}