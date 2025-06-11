package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.UserRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User register(String firstname, String lastname, String email, String password, String role) {
        jdbcTemplate.update(
                "INSERT INTO users(firstname, lastname, email, password, role) VALUES (?, ?, ?, ?, ?)",
                firstname, lastname, email, password, role
        );
        return logIn(email);
    }

    @Override
    public User logIn(String email) {
        String query = "SELECT * FROM users LEFT JOIN users_secteurs us ON users.id = us.users_id WHERE email = ?";

        List<User> users = jdbcTemplate.query(query, new Object[]{email}, (rs, rowNum) -> new User(
                UUID.fromString(rs.getString("id")),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("description"),
                rs.getString("role"),
                new ArrayList<>(),
                new ArrayList<>()
        ));
        if(users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public User findById(UUID userId) {
        String query = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(query,
                new Object[]{userId.toString()}, (rs, rowNum) ->
                        new User(
                                UUID.fromString(rs.getString("id")),
                                rs.getString("firstname"),
                                rs.getString("lastname"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("description"),
                                rs.getString("role"),
                                new ArrayList<>(),
                                new ArrayList<>()
                        ));
    }

    @Override
    public User updateUser(User user) {
        String query = "SELECT * FROM users WHERE id = ?";
        return null;
    }

    //Méthode pour récup tous les users afin que les admin puissent voir tous les profils
    @Override
    public List<User> getAllUsers(User user) {
        String query = "SELECT * FROM users";
        return jdbcTemplate.query(query, (rs, rowNum) ->
                new User(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("description"),
                        rs.getString("role"),
                        new ArrayList<>(),
                        new ArrayList<>()
                )
        );
    }

    //Méthode pour que les porteurs voient les parrains
    public List<User>getAllParrains(){
        String query = "SELECT * FROM users WHERE role = 'PARRAIN'";
        return jdbcTemplate.query(query, (rs, rowNum) ->
                new User(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("description"),
                        rs.getString("role"),
                        new ArrayList<>(),
                        new ArrayList<>()
                )
        );
    }



}
