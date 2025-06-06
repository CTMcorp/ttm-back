package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.Message;
import fr.initiativedeuxsevres.ttm.domain.services.MessageService;
import fr.initiativedeuxsevres.ttm.web.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ttm/me")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<String> getCurrentUser(Authentication authentication) {
        /// retourne le nom de l'utilisateur connecté
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(authentication.getName());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/messagerie")
    public ResponseEntity<List<MessageResponse>> getAllMessages() {
        /// récupère tous les messages et les transforme en objets MessageResponse
        List<Message> messages = messageService.getAllMessages();
        List<MessageResponse> messageResponses = messages.stream().map(
                message -> new MessageResponse(message.getId(), message.getContent())
        ).toList();
        return ResponseEntity.ok(messageResponses);
    }
}
