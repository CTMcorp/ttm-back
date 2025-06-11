package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.TypesAccompagnementRepository;
import fr.initiativedeuxsevres.ttm.domain.repositories.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TypesAccompagnementRepositoryImpl implements TypesAccompagnementRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public TypesAccompagnementRepositoryImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public List<TypesAccompagnement> addUserType(UUID userId, int typeId) {
        String insert = "INSERT INTO users_types (users_id, types_id_number) VALUES (?, ?)";
        jdbcTemplate.update(insert, userId, typeId);

        return findTypesByUserId(userId);
    }

    @Override
    public List<TypesAccompagnement> deleteUserType(UUID userId, int typeId) {
        String deleteQuery = "DELETE FROM users_types WHERE users_id = ? AND types_id_number = ?";
        jdbcTemplate.update(deleteQuery, userId.toString(), typeId);
        return findTypesByUserId(userId);
    }

    @Override
    public List<TypesAccompagnement> findTypesByUserId(UUID userId) {
        String query = "SELECT types_id_number, types.name FROM users_types JOIN types ON users_types.types_id_number = types.id_number WHERE users_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId.toString()}, (rs, rowNum) -> {
            String typeName = rs.getString("name");
            return TypesAccompagnement.fromLabel(typeName);
        });
    }

    @Override
    public List<TypesAccompagnement> findAllTypes() {
        String query = "SELECT name FROM types";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            String typeName = rs.getString("name");
            return TypesAccompagnement.fromLabel(typeName);
        });
    }
}
