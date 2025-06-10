package fr.initiativedeuxsevres.ttm.domain.services;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.web.dto.LoginRequestDto;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    User register(String firstname, String lastname, String email, String password, String role);

    User loadUserByUsername(String email);

    String logIn(LoginRequestDto loginRequestDto);

    User findById(UUID userId);

    List<User> findAllUsers(User user);

    List <User> findAllParrains(User parrains);

}
