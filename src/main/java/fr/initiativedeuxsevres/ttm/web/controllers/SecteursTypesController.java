package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.services.SecteursActivitesService;
import fr.initiativedeuxsevres.ttm.domain.services.TypesAccompagnementService;
import fr.initiativedeuxsevres.ttm.web.dto.SecteursActivitesDto;
import fr.initiativedeuxsevres.ttm.web.dto.TypesAccompagnementDto;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import fr.initiativedeuxsevres.ttm.web.mapper.UserMapperDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ttm")
public class SecteursTypesController {
    private final SecteursActivitesService secteursActivitesService;
    private final TypesAccompagnementService typesAccompagnementService;
    private final UserMapperDto userMapperDto;

    public SecteursTypesController(SecteursActivitesService secteursActivitesService, TypesAccompagnementService typesAccompagnementService, UserMapperDto userMapperDto) {
        this.secteursActivitesService = secteursActivitesService;
        this.typesAccompagnementService = typesAccompagnementService;
        this.userMapperDto = userMapperDto;
    }

    @PostMapping("/{userId}/type/{typeId}")
    public ResponseEntity<UserDto> addTypeToUser(@PathVariable UUID userId, @PathVariable Integer typeId) {
        User updateUser = typesAccompagnementService.addUserType(userId, typeId);
        UserDto user = userMapperDto.mapUserToUserDto(updateUser);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/types")
    public ResponseEntity<List<TypesAccompagnementDto>> getUserTypes(@PathVariable UUID userId) {
        List<TypesAccompagnement> types = typesAccompagnementService.findTypesByUserId(userId);
        List<TypesAccompagnementDto> typesDto = types.stream().map(
                TypesAccompagnementDto::mapTypesAccompagnementToTypesAccompagnementDto
        ).toList();
        return ResponseEntity.ok(typesDto);
    }

    @GetMapping("/allTypes")
    public ResponseEntity<List<TypesAccompagnementDto>> getAllTypes() {
        List<TypesAccompagnement> types = typesAccompagnementService.findAllTypes();
        List<TypesAccompagnementDto> typesDto = types.stream().map(
                TypesAccompagnementDto::mapTypesAccompagnementToTypesAccompagnementDto
        ).toList();
        return ResponseEntity.ok(typesDto);
    }

    @PostMapping("/{userId}/secteur/{secteurId}")
    public ResponseEntity<UserDto> addSecteurToUser(@PathVariable UUID userId, @PathVariable Integer secteurId) {
        User updateUser = secteursActivitesService.addUserSecteur(userId, secteurId);
        UserDto user = userMapperDto.mapUserToUserDto(updateUser);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/secteurs")
    public ResponseEntity<List<SecteursActivitesDto>> getUserSecteurs(@PathVariable UUID userId) {
        List<SecteursActivites> secteurs = secteursActivitesService.findSecteursByUserId(userId);
        List<SecteursActivitesDto> secteursDto = secteurs.stream().map(
                SecteursActivitesDto::mapSecteursActivitesToSecteursActivitesDto
        ).toList();
        return ResponseEntity.ok(secteursDto);
    }

    @GetMapping("/allSecteurs")
    public ResponseEntity<List<SecteursActivitesDto>> getAllSecteurs() {
        List<SecteursActivites> secteurs = secteursActivitesService.findAllSecteurs();
        List<SecteursActivitesDto> secteursDto = secteurs.stream().map(
                SecteursActivitesDto::mapSecteursActivitesToSecteursActivitesDto
        ).toList();
        return ResponseEntity.ok(secteursDto);
    }
}
