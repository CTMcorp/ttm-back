package fr.initiativedeuxsevres.ttm.web.dto;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;

import java.util.List;

public record UserUpdateDto(
        String firstname,
        String lastname,
        String email,
        String password,
        String description,
        String photo,
        List<SecteursActivites> secteursActivites,
        List<TypesAccompagnement> typesAccompagnements
) {
}