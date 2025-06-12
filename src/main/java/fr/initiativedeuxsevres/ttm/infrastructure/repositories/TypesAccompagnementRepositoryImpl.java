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
    public User addUserType(UUID userId, int typeId) {
        String insert = "INSERT INTO users_types (users_id, types_id_number) VALUES (?, ?)";
        jdbcTemplate.update(insert, userId, typeId);

        return findUserWithTypes(userId);
    }

    @Override
    public List<TypesAccompagnement> updateUserTypes(UUID userId, List<TypesAccompagnement> types) {
        String queryDelete = "DELETE FROM users_types WHERE users_id = ?";
        jdbcTemplate.update(queryDelete, userId.toString());

        if (types != null && !types.isEmpty()) {
            for (TypesAccompagnement type : types) {
                String queryInsert = "INSERT INTO users_types (users_id, types_id_number) VALUES(?, ?)";
                jdbcTemplate.update(queryInsert, userId.toString(), type.name());
            }
        }

        // Requête pour récupérer les secteurs mis à jour
        String querySelect = "SELECT types_id_number FROM users_types WHERE users_id = ?";
        List<String> typeNames = jdbcTemplate.queryForList(querySelect, new Object[]{userId.toString()}, String.class);

        return typeNames.stream()
                .map(TypesAccompagnement::valueOf)
                .toList();
    }

    private User findUserWithTypes(UUID userId) {
        String query = "SELECT * FROM users WHERE id = ?";

        User user = jdbcTemplate.queryForObject(query, new Object[]{userId.toString()}, (rs, rowNum) ->
                userRepository.fromRS(rs));

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
