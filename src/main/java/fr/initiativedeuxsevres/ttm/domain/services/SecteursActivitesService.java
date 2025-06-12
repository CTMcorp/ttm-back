package fr.initiativedeuxsevres.ttm.domain.services;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface SecteursActivitesService {
    List<SecteursActivites> addUserSecteur(UUID userId, int secteurId);
    List<SecteursActivites> deleteUserSecteur(UUID userId, int secteurId);
    List<SecteursActivites> findSecteursByUserId(UUID userId);
    List<SecteursActivites> findAllSecteurs();
}