package fr.initiativedeuxsevres.ttm.domain.services;

import fr.initiativedeuxsevres.ttm.domain.models.Message;
import fr.initiativedeuxsevres.ttm.domain.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /// crée un nouvel objet Message et le sauvegarde en base
    public Message saveMessage(String content) {
        Message message = new Message();
        message.setContent(content);
        return messageRepository.save(message);
    }

    ///  récupère tous les messages depuis la base
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}