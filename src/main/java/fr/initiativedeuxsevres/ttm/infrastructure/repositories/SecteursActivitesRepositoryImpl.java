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
    public User addUserSecteur(UUID userId, int secteurId) {
        String insert = "INSERT INTO users_secteurs (users_id, secteurs_id_number) VALUES (?, ?)";
        jdbcTemplate.update(insert, userId, secteurId);

        return findUserWithSecteurs(userId);
    }


    @Override
    public List<SecteursActivites> updateUserSecteurs(UUID userId, List<SecteursActivites> secteurs) {
        String queryDelete = "DELETE FROM users_secteurs WHERE users_id = ?";
        jdbcTemplate.update(queryDelete, userId.toString());

        if (secteurs != null && !secteurs.isEmpty()) {
            for (SecteursActivites secteur : secteurs) {
            String queryInsert = "INSERT INTO users_secteurs (users_id, secteurs_id_number) VALUES(?, ?)";
            jdbcTemplate.update(queryInsert, userId.toString(), secteur.name());
            }
        }

        // Requête pour récupérer les secteurs mis à jour
        String querySelect = "SELECT secteurs_id_number FROM users_secteurs WHERE users_id = ?";
        List<String> secteurNames = jdbcTemplate.queryForList(querySelect, new Object[]{userId.toString()}, String.class);

        return secteurNames.stream()
                .map(SecteursActivites::valueOf)
                .toList();
    }


    private User findUserWithSecteurs(UUID userId) {
        String query = "SELECT * FROM users WHERE id = ?";

        User user = jdbcTemplate.queryForObject(query, new Object[]{userId.toString()}, (rs, rowNum) ->
                userRepository.fromRS(rs));
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

