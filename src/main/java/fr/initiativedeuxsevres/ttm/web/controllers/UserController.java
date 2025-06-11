package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import fr.initiativedeuxsevres.ttm.web.mapper.UserMapperDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/")
public class UserController {
    private final UserService userService;
    private final UserMapperDto userMapperDto;

    public UserController(UserService userService, UserMapperDto userMapperDto) {
        this.userService = userService;
        this.userMapperDto = userMapperDto;
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Authentication null or unauthenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = (User) authentication.getPrincipal();
        log.info("User: {}", user);
        User findUser = userService.findById(user.userId());
        UserDto userDto = userMapperDto.mapUserToUserDto(findUser);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/profiles")
    public List<User> getAllUsers(User user){
        return userService.findAllUsers(user);
    }

//    @GetMapping("/parrainsProfiles")
//    public  List<User> getAllParrains(User parrains){
//        return userService.findAllParrains(parrains);
//    }
    @GetMapping("/parrainsProfiles")
    public  List<User> getAllParrains(){
        return userService.findAllParrains();
    }
}
