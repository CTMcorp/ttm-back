package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;
import fr.initiativedeuxsevres.ttm.domain.services.SecteursActivitesService;
import fr.initiativedeuxsevres.ttm.domain.services.TypesAccompagnementService;
import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import fr.initiativedeuxsevres.ttm.web.dto.SecteursActivitesDto;
import fr.initiativedeuxsevres.ttm.web.dto.TypesAccompagnementDto;
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

    public SecteursTypesController(SecteursActivitesService secteursActivitesService, TypesAccompagnementService typesAccompagnementService) {
        this.secteursActivitesService = secteursActivitesService;
        this.typesAccompagnementService = typesAccompagnementService;
    }

    @PutMapping("/{userId}/secteur/{secteurId}")
    public ResponseEntity<List<SecteursActivitesDto>> addSecteurToUser(@PathVariable UUID userId, @PathVariable Integer secteurId) {
        List<SecteursActivites> updateSecteurs = secteursActivitesService.addUserSecteur(userId, secteurId);
        List<SecteursActivitesDto> secteursDto = updateSecteurs.stream().map(
                SecteursActivitesDto::mapSecteursActivitesToSecteursActivitesDto
        ).toList();
        return ResponseEntity.ok(secteursDto);
    }

    @DeleteMapping("/{userId}/secteur/{secteurId}")
    public ResponseEntity<List<SecteursActivitesDto>> deleteSecteurFromUser(@PathVariable UUID userId, @PathVariable Integer secteurId) {
        List<SecteursActivites> deleteSecteurs = secteursActivitesService.deleteUserSecteur(userId, secteurId);
        List<SecteursActivitesDto> secteursDto = deleteSecteurs.stream().map(
                SecteursActivitesDto::mapSecteursActivitesToSecteursActivitesDto
        ).toList();
        return ResponseEntity.ok(secteursDto);
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

    @PutMapping("/{userId}/type/{typeId}")
    public ResponseEntity<List<TypesAccompagnementDto>> addTypeToUser(@PathVariable UUID userId, @PathVariable Integer typeId) {
        List<TypesAccompagnement> updateTypes= typesAccompagnementService.addUserType(userId, typeId);
        List<TypesAccompagnementDto> typesDto = updateTypes.stream().map(
                TypesAccompagnementDto::mapTypesAccompagnementToTypesAccompagnementDto
        ).toList();
        return ResponseEntity.ok(typesDto);
    }

    @DeleteMapping("/{userId}/type/{typeId}")
    public ResponseEntity<List<TypesAccompagnementDto>> deleteTypeFromUser(@PathVariable UUID userId, @PathVariable Integer typeId) {
        List<TypesAccompagnement> deleteTypes = typesAccompagnementService.deleteUserType(userId, typeId);
        List<TypesAccompagnementDto> typesDto = deleteTypes.stream().map(
                TypesAccompagnementDto::mapTypesAccompagnementToTypesAccompagnementDto
        ).toList();
        return ResponseEntity.ok(typesDto);
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
}
