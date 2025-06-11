package fr.initiativedeuxsevres.ttm.domain.models;

public record UserUpdateRequest(
         String firstname,
          String lastname,
          String email,
          String password,
          String description,
          String photo
) {
}
