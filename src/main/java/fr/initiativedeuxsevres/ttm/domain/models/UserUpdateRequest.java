package fr.initiativedeuxsevres.ttm.domain.models;

import java.util.UUID;

public record UserUpdateRequest(
    UUID userId,
    String firstname,
    String lastname,
    String email,
    String password,
    String description,
    String photo
) {
}
