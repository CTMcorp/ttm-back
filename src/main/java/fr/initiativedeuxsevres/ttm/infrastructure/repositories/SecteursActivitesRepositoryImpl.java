package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.SecteursActivitesRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class SecteursActivitesRepositoryImpl implements SecteursActivitesRepository {
    private final JdbcTemplate jdbcTemplate;

    public SecteursActivitesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUserSecteur(UUID userId, int secteurId) {
        String insert = "INSERT INTO users_secteurs (users_id, secteurs_id_number) VALUES (?, ?)";
        jdbcTemplate.update(insert, userId, secteurId);

        return findUserWithSecteurs(userId);
    }

    private User findUserWithSecteurs(UUID userId) {
        String query = "SELECT * FROM users WHERE id = ?";

        User user = jdbcTemplate.queryForObject(query, new Object[]{userId.toString()}, (rs, rowNum) ->
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
        List<SecteursActivites> secteurs = findSecteursByUserId(userId);
        user.secteursActivites().addAll(secteurs);
        return user;
    }

    @Override
    public List<SecteursActivites> findSecteursByUserId(UUID userId) {
        String query = "SELECT secteurs.name FROM secteurs JOIN users_secteurs us ON secteurs.id_number = us.secteurs_id_number WHERE us.users_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId.toString()}, (rs, rowNum) -> {
            String secteurName = rs.getString("name");
            return SecteursActivites.fromLabel(secteurName);
        });
    }

    public List<SecteursActivites> findAllSecteurs() {
        String query = "SELECT name FROM secteurs";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            String secteurName = rs.getString("name");
            return SecteursActivites.fromLabel(secteurName);
        });
    }
}

