package com.samsara.paladin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samsara.paladin.dto.ResetPasswordDetails;
import com.samsara.paladin.dto.UserDto;
import com.samsara.paladin.service.user.UserServiceImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUserAccount(@Valid @RequestBody UserDto userDto) {
        UserDto userResponse = userService.registerUser(userDto);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUserAccount(@Valid @RequestBody UserDto userDto) {
        UserDto userResponse = userService.updateUser(userDto);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/promote/{username}")
    public ResponseEntity<UserDto> promoteUserToAdmin(@PathVariable @NotBlank String username) {
        UserDto userResponse = userService.grantAdminRoleToUser(username);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{username}")
    public String removeUserAccount(@PathVariable @NotBlank String username) {
        userService.deleteUser(username);
        return "User '" + username + "' deleted!";
    }

    @GetMapping("/all")
    public List<UserDto> fetchAllUsers() {
        return userService.loadAllUsers();
    }

    @GetMapping("/username/{username}")
    public UserDto fetchUsersByUsername(@PathVariable @NotBlank String username) {
        return userService.loadUserByUsername(username);
    }

    @GetMapping("/email/{email}")
    public UserDto fetchUsersByEmail(@PathVariable @NotBlank String email) {
        return userService.loadUserByEmail(email);
    }

    @GetMapping("/firstName/{firstName}")
    public List<UserDto> fetchUsersByFirstName(@PathVariable @NotBlank String firstName) {
        return userService.loadUsersByFirstName(firstName);
    }

    @GetMapping("/lastName/{lastName}")
    public List<UserDto> fetchUsersByLastName(@PathVariable @NotBlank String lastName) {
        return userService.loadUsersByLastName(lastName);
    }

    @GetMapping("/firstTenAdded")
    public List<UserDto> fetchFirst10AddedUsers() {
        return userService.loadFirst10AddedUsers();
    }

    @GetMapping("/lastTenAdded")
    public List<UserDto> fetchLast10AddedUsers() {
        return userService.loadLast10AddedUsers();
    }

    @GetMapping("/enabled")
    public List<UserDto> fetchEnabledUsers() {
        return userService.loadEnabledUsers();
    }

    @GetMapping("/admins")
    public List<UserDto> showAdmins() {
        return userService.loadAdmins();
    }

    @PutMapping("/password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordDetails passwordDetails) {
        userService.resetUserPassword(passwordDetails);
        return new ResponseEntity<>("Password changed!", HttpStatus.OK);
    }
}
