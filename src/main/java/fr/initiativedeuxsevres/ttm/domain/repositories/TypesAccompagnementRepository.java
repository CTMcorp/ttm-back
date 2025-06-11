package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;
import fr.initiativedeuxsevres.ttm.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface TypesAccompagnementRepository {

    User addUserType(UUID userId, int typeId);
    List<TypesAccompagnement> updateUserTypes(UUID userId, List<TypesAccompagnement> types);

    List<TypesAccompagnement> findTypesByUserId(UUID userId);

    List<TypesAccompagnement> findAllTypes();
}
