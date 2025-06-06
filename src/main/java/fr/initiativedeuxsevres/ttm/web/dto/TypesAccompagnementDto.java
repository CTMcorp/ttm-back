package fr.initiativedeuxsevres.ttm.web.dto;

import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;

public enum TypesAccompagnementDto {
    RESSOURCES_HUMAINES,
    FINANCE_ET_COMPTABILITE,
    JURIDIQUE,
    INFORMATIQUE,
    COMMERCIAL_ET_COMMUNICATION;

    public static TypesAccompagnementDto mapTypesAccompagnementToTypesAccompagnementDto(TypesAccompagnement typesAccompagnement) {
        return switch (typesAccompagnement) {
            case RESSOURCES_HUMAINES -> TypesAccompagnementDto.RESSOURCES_HUMAINES;
            case FINANCE_ET_COMPTABILITE -> TypesAccompagnementDto.FINANCE_ET_COMPTABILITE;
            case JURIDIQUE -> TypesAccompagnementDto.JURIDIQUE;
            case INFORMATIQUE -> TypesAccompagnementDto.INFORMATIQUE;
            case COMMERCIAL_ET_COMMUNICATION -> TypesAccompagnementDto.COMMERCIAL_ET_COMMUNICATION;
        };
    }
}
