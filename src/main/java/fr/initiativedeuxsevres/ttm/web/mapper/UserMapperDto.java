package fr.initiativedeuxsevres.ttm.web.mapper;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.SecteursActivitesRepository;
import fr.initiativedeuxsevres.ttm.domain.repositories.TypesAccompagnementRepository;
import fr.initiativedeuxsevres.ttm.web.dto.SecteursActivitesDto;
import fr.initiativedeuxsevres.ttm.web.dto.TypesAccompagnementDto;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import fr.initiativedeuxsevres.ttm.web.dto.UserUpdateDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserMapperDto {
    private final SecteursActivitesRepository secteursActivitesRepository;
    private final TypesAccompagnementRepository typesAccompagnementRepository;

    public UserMapperDto(SecteursActivitesRepository secteursActivitesRepository, TypesAccompagnementRepository typesAccompagnementRepository) {
        this.secteursActivitesRepository = secteursActivitesRepository;
        this.typesAccompagnementRepository = typesAccompagnementRepository;
    }

    public UserDto mapUserToUserDto(User user) {

        List<SecteursActivitesDto> secteurs = secteursActivitesRepository.findSecteursByUserId(user.userId()).stream().map(
                SecteursActivitesDto::mapSecteursActivitesToSecteursActivitesDto
        ).toList();

        List<TypesAccompagnementDto> types = typesAccompagnementRepository.findTypesByUserId(user.userId()).stream().map(
                TypesAccompagnementDto::mapTypesAccompagnementToTypesAccompagnementDto
        ).toList();

        return new UserDto(
                user.userId(),
                user.firstname(),
                user.lastname(),
                user.email(),
                user.description(),
                user.role(),
                user.photo(),
                secteurs,
                types);
    }


    public static User copyWith(User original, UserUpdateDto dto) {
        return new User(
                original.userId(),
                dto.firstname(),
                dto.lastname(),
                original.email(),
                original.password(),
                dto.description(),
                original.role(),
                dto.photo(),
                dto.secteursActivites(),
                dto.typesAccompagnements()
        );
    }

}