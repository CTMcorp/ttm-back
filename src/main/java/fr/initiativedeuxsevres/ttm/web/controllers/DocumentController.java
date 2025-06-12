package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.Document;
import fr.initiativedeuxsevres.ttm.domain.services.DocumentService;
import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/ttm/me/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("nom") String nom,
            @RequestParam("fichier") MultipartFile fichier,
            Principal principal) throws IOException {
        String email = principal.getName();
        System.out.println(email);
        Document doc = documentService.enregistrerPdf(nom, fichier, email);
        return ResponseEntity.ok(doc);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDocuments(Principal principal) {
        String email = principal.getName();
        UUID userId = documentService.findUserIdByEmail(email);
        return ResponseEntity.ok(documentService.findAllByUserId(userId.toString()));
    }

    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        return documentService.findById(uuid);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable UUID id) {
            documentService.deleteDocument(id);
            return ResponseEntity.noContent().build();
    }
}