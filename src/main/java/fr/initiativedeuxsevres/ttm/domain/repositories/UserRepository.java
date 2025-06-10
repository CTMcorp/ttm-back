package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User register(String firstname, String lastname, String email, String password, String role);

    User logIn(String email);

    User findById(UUID userId);

    User updateUser(User user);

    List<User> getAllUsers(User user);
    List<User> getAllParrains(User parrains);
}
