package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.models.UserUpdateRequest;
import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import fr.initiativedeuxsevres.ttm.web.mapper.UserMapperDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<UserDto> getUserAuthenticated(Authentication authentication) {
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

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID userId) {
        User findUser = userService.findById(userId);
        UserDto userDto = userMapperDto.mapUserToUserDto(findUser);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/profiles")
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/parrainsProfiles")
    public  List<User> getAllParrains(){
        return userService.findAllParrains();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest updateRequest) {
        User updatedUser = userService.updateUser(userId, updateRequest);
        UserDto userDto = userMapperDto.mapUserToUserDto(updatedUser);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }
}
