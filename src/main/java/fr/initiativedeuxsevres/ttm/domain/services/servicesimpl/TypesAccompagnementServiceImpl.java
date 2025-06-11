package fr.initiativedeuxsevres.ttm.domain.services.servicesimpl;

import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.TypesAccompagnementRepository;
import fr.initiativedeuxsevres.ttm.domain.services.TypesAccompagnementService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TypesAccompagnementServiceImpl implements TypesAccompagnementService {
    private final TypesAccompagnementRepository typesAccompagnementRepository;

    public TypesAccompagnementServiceImpl(TypesAccompagnementRepository typesAccompagnementRepository) {
        this.typesAccompagnementRepository = typesAccompagnementRepository;
    }

    @Override
    public List<TypesAccompagnement> addUserType(UUID userId, int typeId) {
        return typesAccompagnementRepository.addUserType(userId, typeId);
    }

    @Override
    public List<TypesAccompagnement> deleteUserType(UUID userId, int typeId) {
        return typesAccompagnementRepository.deleteUserType(userId, typeId);
    }

    @Override
    public List<TypesAccompagnement> findTypesByUserId(UUID userId) {
        return typesAccompagnementRepository.findTypesByUserId(userId);
    }

    @Override
    public List<TypesAccompagnement> findAllTypes() {
        return typesAccompagnementRepository.findAllTypes();
    }
}
