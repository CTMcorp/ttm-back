package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User register(String firstname, String lastname, String email, String password, String role);

    User logIn(String email);

    User findById(UUID userId);

    User updateUser(User user);

    List<User> getAllUsers();
    List<User> getAllParrains();

    User fromRS(ResultSet rs) throws SQLException;
}
