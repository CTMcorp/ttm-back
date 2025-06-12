package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SecteursActivitesRepository {

    User addUserSecteur(UUID userId, int secteurId);

    List<SecteursActivites> updateUserSecteurs(UUID userId, List<SecteursActivites> secteurs);

    List<SecteursActivites> findSecteursByUserId(UUID userId);

    List<SecteursActivites> findAllSecteurs();
}
