package fr.initiativedeuxsevres.ttm.domain.services.servicesimpl;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.SecteursActivitesRepository;
import fr.initiativedeuxsevres.ttm.domain.services.SecteursActivitesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SecteursActivitesServiceImpl implements SecteursActivitesService {
    private final SecteursActivitesRepository secteursActivitesRepository;

    public SecteursActivitesServiceImpl(SecteursActivitesRepository secteursActivitesRepository) {
        this.secteursActivitesRepository = secteursActivitesRepository;
    }

    @Override
    public List<SecteursActivites> addUserSecteur(UUID userId, int secteurId) {
        return secteursActivitesRepository.addUserSecteur(userId, secteurId);
    }

    @Override
    public List<SecteursActivites> deleteUserSecteur(UUID userId, int secteurId) {
        return secteursActivitesRepository.deleteUserSecteur(userId, secteurId);
    }

    @Override
    public List<SecteursActivites> findSecteursByUserId(UUID userId) {
        return secteursActivitesRepository.findSecteursByUserId(userId);
    }


    @Override
    public List<SecteursActivites> findAllSecteurs() {
        return secteursActivitesRepository.findAllSecteurs();
    }
}