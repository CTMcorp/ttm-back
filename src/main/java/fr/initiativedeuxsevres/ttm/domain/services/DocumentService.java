package fr.initiativedeuxsevres.ttm.domain.services;

import fr.initiativedeuxsevres.ttm.domain.models.Document;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface DocumentService {
    Document enregistrerPdf(String nom, MultipartFile fichier, String email) throws IOException;
    List<Document> findAllByUserId(String userId);
    Document findById(UUID id);
    void deleteDocument(UUID id);
    UUID findUserIdByEmail(String email);
}
