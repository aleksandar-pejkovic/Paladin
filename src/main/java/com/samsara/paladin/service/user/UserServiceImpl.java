package com.samsara.paladin.service.user;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samsara.paladin.dto.ResetPasswordDetails;
import com.samsara.paladin.dto.UserDto;
import com.samsara.paladin.enums.EventAction;
import com.samsara.paladin.enums.EventCategory;
import com.samsara.paladin.enums.RoleName;
import com.samsara.paladin.events.EventPublisher;
import com.samsara.paladin.exceptions.passwordValidation.IllegalPasswordArgumentException;
import com.samsara.paladin.exceptions.passwordValidation.PasswordArgumentMissingException;
import com.samsara.paladin.exceptions.passwordValidation.ResetPasswordFailedException;
import com.samsara.paladin.exceptions.user.EmailExistsException;
import com.samsara.paladin.exceptions.user.EmailNotFoundException;
import com.samsara.paladin.exceptions.user.UsernameExistsException;
import com.samsara.paladin.exceptions.user.UsernameNotFoundException;
import com.samsara.paladin.model.Role;
import com.samsara.paladin.model.User;
import com.samsara.paladin.repository.RoleRepository;
import com.samsara.paladin.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto registerUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UsernameExistsException("Account with username '" + userDto.getUsername() + "' already exist");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailExistsException("Account with email '" + userDto.getEmail() + "' already exist!");
        }
        if (userDto.getPassword() == null) {
            throw new PasswordArgumentMissingException("Password missing for new user!");
        }
        userDto.setCreationDate(new Date());
        userDto.setEnabled(true);
        User user = convertUserToEntity(userDto);
        encryptUserPassword(user);
        assignDefaultRoleToUser(user);
        User registeredUser = userRepository.save(user);
        publishUserEvent(registeredUser.getUsername(), EventAction.REGISTER);
        return convertUserToDto(registeredUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Username '" + userDto.getUsername() + "' not found!");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailExistsException("Account with email '" + userDto.getEmail() + "' already exist!");
        }
        User currentUser = optionalUser.get();
        if (userDto.getPassword() != null) {
            throw new IllegalPasswordArgumentException("Please use 'password reset' option for password update!");
        }
        modelMapper.map(userDto, currentUser);
        User updatedUser = userRepository.save(currentUser);
        publishUserEvent(updatedUser.getUsername(), EventAction.EDIT);
        return convertUserToDto(updatedUser);
    }

    @Override
    public boolean resetUserPassword(ResetPasswordDetails resetPasswordDetails) {

        userRepository.findByUsername(resetPasswordDetails.getUsername())
                .filter(user -> user.getSecretAnswer().equals(resetPasswordDetails.getSecretAnswer()))
                .map(user -> encryptUserPassword(user, resetPasswordDetails.getNewPassword()))
                .map(userRepository::save)
                .orElseThrow(
                        () -> new ResetPasswordFailedException("Password reset failed! Wrong data!")
                );
        publishUserEvent(resetPasswordDetails.getUsername(), EventAction.CHANGE_PASSWORD);
        return true;
    }

    @Override
    public UserDto grantAdminRoleToUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Username '" + username + "' not found!");
        }
        User currentUser = optionalUser.get();
        currentUser.getRoles().add(roleRepository.findByName(RoleName.ADMIN));
        User adminUser = userRepository.save(currentUser);
        publishUserEvent(adminUser.getUsername(), EventAction.GRANT_ADMIN);
        return convertUserToDto(adminUser);
    }

    @Override
    public void deleteUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Username '" + username + "' not found!");
        }
        User currentUser = optionalUser.get();
        userRepository.delete(currentUser);
        publishUserEvent(currentUser.getUsername(), EventAction.DELETE);
    }

    @Override
    public List<UserDto> loadAllUsers() {
        return convertUsersToDtoList(userRepository.findAll());
    }

    @Override
    public UserDto loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertUserToDto)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Username '%s' not found!", username))
                );
    }

    @Override
    public UserDto loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertUserToDto)
                .orElseThrow(
                        () -> new EmailNotFoundException(String.format("Email '%s' not found!", email))
                );
    }

    @Override
    public List<UserDto> loadUsersByFirstName(String firstName) {
        return convertUsersToDtoList(userRepository.findByFirstName(firstName));
    }

    @Override
    public List<UserDto> loadUsersByLastName(String lastName) {
        return convertUsersToDtoList(userRepository.findByLastName(lastName));
    }

    @Override
    public List<UserDto> loadFirst10AddedUsers() {
        return convertUsersToDtoList(userRepository.findFirst10ByOrderByCreationDateAsc());
    }

    @Override
    public List<UserDto> loadLast10AddedUsers() {
        return convertUsersToDtoList(userRepository.findFirst10ByOrderByCreationDateDesc());
    }

    @Override
    public List<UserDto> loadEnabledUsers() {
        return convertUsersToDtoList(userRepository.findByEnabled(true));
    }

    @Override
    public List<UserDto> loadAdmins() {
        Role adminRole = roleRepository.findByName(RoleName.ADMIN);
        return convertUsersToDtoList(userRepository.findByRoles(adminRole));
    }

    private void encryptUserPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private User encryptUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }

    private void assignDefaultRoleToUser(User user) {
        Role userRole = roleRepository.findByName(RoleName.USER);
        user.setRoles(Collections.singleton(userRole));
    }

    private void publishUserEvent(String username, EventAction action) {
        eventPublisher.publishEvent(
                EventCategory.USER,
                username,
                action
        );
    }

    private List<UserDto> convertUsersToDtoList(List<User> users) {
        return users
                .stream()
                .map(this::convertUserToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertUserToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        if (user.getHeroes() != null) {
            userDto.setHeroCount(user.getHeroes().size());
        } else {
            userDto.setHeroCount(0);
        }
        return userDto;
    }

    private User convertUserToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
