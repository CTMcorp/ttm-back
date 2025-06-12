package fr.initiativedeuxsevres.ttm.domain.services.servicesimpl;

import fr.initiativedeuxsevres.ttm.domain.models.Document;
import fr.initiativedeuxsevres.ttm.domain.repositories.DocumentRepository;
import fr.initiativedeuxsevres.ttm.domain.repositories.UserRepository;
import fr.initiativedeuxsevres.ttm.domain.services.DocumentService;
import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private UserService userService;


    public Document enregistrerPdf(String nom, MultipartFile fichier, String email) throws IOException {
        UUID userId = userService.findUserIdByEmail(email);
        if (userId == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé pour l'email : " + email);
        }
        Document doc = new Document();
        doc.setNom(nom);
        doc.setFichierPdf(fichier.getBytes());
        doc.setUserId(userId.toString());
        return documentRepository.save(doc);
    }

    public List<Document> findAllByUserId(String userId) {
        return documentRepository.findAllByUserId(userId);
    }

    public Document findById(UUID id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document non trouvé: " + id));
    }

    public void deleteDocument(UUID id) {
        documentRepository.deleteById(id);
    }

    public UUID findUserIdByEmail(String email) {
        return userService.findUserIdByEmail(email);
    }
}