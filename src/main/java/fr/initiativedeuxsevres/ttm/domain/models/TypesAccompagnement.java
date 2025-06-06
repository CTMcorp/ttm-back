package fr.initiativedeuxsevres.ttm.domain.models;

import java.util.Arrays;

public enum TypesAccompagnement {
    RESSOURCES_HUMAINES("Ressources humaines"),
    FINANCE_ET_COMPTABILITE("Finance et comptabilité"),
    JURIDIQUE("Juridique"),
    INFORMATIQUE("Informatique"),
    COMMERCIAL_ET_COMMUNICATION("Commercial et communication");

    public final String name;
    TypesAccompagnement(String name) {
        this.name = name;
    }

    public static TypesAccompagnement fromLabel(String label) {
        return Arrays.stream(TypesAccompagnement.values())
                .filter(value -> value.name.equalsIgnoreCase(label.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(label + " n'existe pas"));
    }
}