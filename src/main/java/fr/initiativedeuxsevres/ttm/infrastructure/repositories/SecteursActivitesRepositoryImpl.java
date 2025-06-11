package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.SecteursActivitesRepository;
import fr.initiativedeuxsevres.ttm.domain.repositories.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SecteursActivitesRepositoryImpl implements SecteursActivitesRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public SecteursActivitesRepositoryImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public List<SecteursActivites> addUserSecteur(UUID userId, int secteurId) {
        String insert = "INSERT INTO users_secteurs (users_id, secteurs_id_number) VALUES (?, ?) ON CONFLICT DO NOTHING";
        jdbcTemplate.update(insert, userId, secteurId);

        return findSecteursByUserId(userId);
    }

    @Override
    public List<SecteursActivites> deleteUserSecteur(UUID userId, int secteurId) {
        String delete = "DELETE FROM users_secteurs WHERE users_id = ? AND secteurs_id_number = ?";
        jdbcTemplate.update(delete, userId.toString(), secteurId);
        return findSecteursByUserId(userId);
    }

    @Override
    public List<SecteursActivites> findSecteursByUserId(UUID userId) {
        String query = "SELECT secteurs_id_number, secteurs.name FROM users_secteurs JOIN secteurs ON users_secteurs.secteurs_id_number = secteurs.id_number WHERE users_id = ?";
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

