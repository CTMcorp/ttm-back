package fr.initiativedeuxsevres.ttm.domain.models;

import java.util.Arrays;

public enum SecteursActivites {
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
    SecteursActivites(String name) {
        this.name = name;
    }

    public static SecteursActivites fromLabel(String label) {
        return Arrays.stream(SecteursActivites.values())
                .filter(value -> value.name.equalsIgnoreCase(label.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(label + " n'existe pas"));
    }
}
