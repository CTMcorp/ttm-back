package fr.initiativedeuxsevres.ttm.domain.services;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.models.UserUpdateRequest;
import fr.initiativedeuxsevres.ttm.web.dto.LoginRequestDto;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    User register(String firstname, String lastname, String email, String password, String role);

    User loadUserByUsername(String email);

    String logIn(LoginRequestDto loginRequestDto);

    User findById(UUID userId);

    List<User> findAllUsers();

    List <User> findAllParrains();

    User updateUser(UUID userId, UserUpdateRequest userUpdateRequest);

}
