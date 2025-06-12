package fr.initiativedeuxsevres.ttm.domain.services;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;
import fr.initiativedeuxsevres.ttm.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface TypesAccompagnementService {
    User addUserType(UUID userId, int typeId);

    List<TypesAccompagnement> findTypesByUserId(UUID userId);

    List<TypesAccompagnement> findAllTypes();
}
