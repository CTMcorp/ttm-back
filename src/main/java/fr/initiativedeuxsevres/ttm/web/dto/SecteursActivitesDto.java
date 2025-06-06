package fr.initiativedeuxsevres.ttm.web.dto;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;

public enum SecteursActivitesDto {
    SERVICES_ADMINISTRATIFS_ET_SOUTIEN("Services administratifs et soutien"),
    ACTIVITES_SPECIALISEES_TECHNIQUES_ET_SCIENTIFIQUES("Activités spécialisées, techniques et scientifiques"),
    AGRICULTURE_SYLVICULTURE_ET_PECHE("Agriculture, sylviculture et pêche"),
    ARTS_SPECTACLES_ET_ACTIVITES_RECREATIVES("Arts, spectacles et activités récréatives"),
    COMMERCE_ET_REPARATION("Commerce et réparation"),
    CONSTRUCTION_BTP("Contruction_BTP"),
    ENSEIGNEMENT("Enseignement"),
    HOTELS_CAFES_ET_RESTAURANTS("Hôtels, cafés et restaurants"),
    INDUSTRIE("Industrie"),
    INFORMATION_ET_COMMUNICATION("Information et communication"),
    EAU_ASSAINISSEMENT_GESTION_DECHETS_ET_DEPOLLUTION("Eau, assainissement, gestion des déchets et dépollution"),
    ELECTRICITE_GAZ_VAPEUR_AIR_CONDITIONNE("Electricité, gaz, vapeur d'air conditionné"),
    SANTE_HUMAINE_ET_ACTION_SOCIALE("Santé humaine et action sociale"),
    SERVICES_AUX_ENTREPRISES("Services aux entreprises"),
    SERVICES_AUX_PARTICULIERS("Services aux particuliers"),
    TRANSPORTS("Transport");

    public final String name;
    SecteursActivitesDto(String name) {
        this.name = name;
    }

    public static SecteursActivitesDto mapSecteursActivitesToSecteursActivitesDto(SecteursActivites secteursActivites) {
        return switch (secteursActivites) {
            case SERVICES_ADMINISTRATIFS_ET_SOUTIEN -> SecteursActivitesDto.SERVICES_ADMINISTRATIFS_ET_SOUTIEN;
            case ACTIVITES_SPECIALISEES_TECHNIQUES_ET_SCIENTIFIQUES -> SecteursActivitesDto.ACTIVITES_SPECIALISEES_TECHNIQUES_ET_SCIENTIFIQUES;
            case AGRICULTURE_SYLVICULTURE_ET_PECHE -> SecteursActivitesDto.AGRICULTURE_SYLVICULTURE_ET_PECHE;
            case ARTS_SPECTACLES_ET_ACTIVITES_RECREATIVES -> SecteursActivitesDto.ARTS_SPECTACLES_ET_ACTIVITES_RECREATIVES;
            case COMMERCE_ET_REPARATION -> SecteursActivitesDto.COMMERCE_ET_REPARATION;
            case CONSTRUCTION_BTP -> SecteursActivitesDto.CONSTRUCTION_BTP;
            case ENSEIGNEMENT -> SecteursActivitesDto.ENSEIGNEMENT;
            case HOTELS_CAFES_ET_RESTAURANTS-> SecteursActivitesDto.HOTELS_CAFES_ET_RESTAURANTS;
            case INDUSTRIE -> SecteursActivitesDto.INDUSTRIE;
            case INFORMATION_ET_COMMUNICATION -> SecteursActivitesDto.INFORMATION_ET_COMMUNICATION;
            case EAU_ASSAINISSEMENT_GESTION_DECHETS_ET_DEPOLLUTION -> SecteursActivitesDto.EAU_ASSAINISSEMENT_GESTION_DECHETS_ET_DEPOLLUTION;
            case ELECTRICITE_GAZ_VAPEUR_AIR_CONDITIONNE -> SecteursActivitesDto.ELECTRICITE_GAZ_VAPEUR_AIR_CONDITIONNE;
            case SANTE_HUMAINE_ET_ACTION_SOCIALE -> SecteursActivitesDto.SANTE_HUMAINE_ET_ACTION_SOCIALE;
            case SERVICES_AUX_ENTREPRISES -> SecteursActivitesDto.SERVICES_AUX_ENTREPRISES;
            case SERVICES_AUX_PARTICULIERS -> SecteursActivitesDto.SERVICES_AUX_PARTICULIERS;
            case TRANSPORTS -> SecteursActivitesDto.TRANSPORTS;
        };
    }
}
