package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
