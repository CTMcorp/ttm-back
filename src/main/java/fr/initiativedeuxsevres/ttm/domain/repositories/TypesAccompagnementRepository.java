package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;
import fr.initiativedeuxsevres.ttm.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface TypesAccompagnementRepository {
    List<TypesAccompagnement> addUserType(UUID userId, int typeId);
    List<TypesAccompagnement> deleteUserType(UUID userId, int typeId);
    List<TypesAccompagnement> findTypesByUserId(UUID userId);
    List<TypesAccompagnement> findAllTypes();
}