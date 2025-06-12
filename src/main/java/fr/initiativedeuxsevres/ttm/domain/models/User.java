package fr.initiativedeuxsevres.ttm.domain.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public record User(
        UUID userId,
        String firstname,
        String lastname,
        String email,
        String password,
        String description,
        String role,
        String photo,
        List<SecteursActivites> secteursActivites,
        List<TypesAccompagnement> typesAccompagnements) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}