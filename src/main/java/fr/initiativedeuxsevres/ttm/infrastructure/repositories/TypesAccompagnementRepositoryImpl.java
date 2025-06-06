package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.TypesAccompagnementRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TypesAccompagnementRepositoryImpl implements TypesAccompagnementRepository {
    private final JdbcTemplate jdbcTemplate;

    public TypesAccompagnementRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUserType(UUID userId, int typeId) {
        String insert = "INSERT INTO users_types (users_id, types_id_number) VALUES (?, ?)";
        jdbcTemplate.update(insert, userId, typeId);

        return findUserWithTypes(userId);
    }

    private User findUserWithTypes(UUID userId) {
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

        List<TypesAccompagnement> types = findTypesByUserId(userId);
        user.typesAccompagnements().addAll(types);
        return user;
    }

    @Override
    public List<TypesAccompagnement> findTypesByUserId(UUID userId) {
        String query = "SELECT types.name FROM types JOIN users_types ut ON types.id_number = ut.types_id_number WHERE ut.users_id = ?";
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
