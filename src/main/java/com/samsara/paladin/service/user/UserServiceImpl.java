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
import com.samsara.paladin.enums.RoleName;
import com.samsara.paladin.exceptions.user.EmailExistsException;
import com.samsara.paladin.exceptions.user.EmailNotFoundException;
import com.samsara.paladin.exceptions.user.ResetPasswordFailedException;
import com.samsara.paladin.exceptions.user.UsernameExistsException;
import com.samsara.paladin.exceptions.user.UsernameNotFoundException;
import com.samsara.paladin.model.Role;
import com.samsara.paladin.model.User;
import com.samsara.paladin.repository.RoleRepository;
import com.samsara.paladin.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto registerUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UsernameExistsException("Account with username: '" + userDto.getUsername() + "' already exist");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailExistsException("Account with email: '" + userDto.getEmail() + "' already exist!");
        }
        userDto.setCreationDate(new Date());
        userDto.setEnabled(true);
        User user = convertToEntity(userDto);
        encryptUserPassword(user);
        assignDefaultRoleToUser(user);
        User registeredUser = saveUser(user);
        return convertToDto(registeredUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Username '" + userDto.getUsername() + "' not found!");
        }
        User storedUser = optionalUser.get();
        modelMapper.map(userDto, storedUser);
        if (userDto.getPassword() != null) {
            encryptUserPassword(storedUser);
        }
        User updatedUser = saveUser(storedUser);
        return convertToDto(updatedUser);
    }

    @Override
    public UserDto grantAdminRoleToUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Username '" + username + "' not found!");
        }
        User storedUser = optionalUser.get();
        storedUser.getRoles().add(roleRepository.findByName(RoleName.ADMIN));
        User adminUser = saveUser(storedUser);
        return convertToDto(adminUser);
    }

    @Override
    public void deleteUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Username '" + username + "' not found!");
        }
        userRepository.delete(optionalUser.get());
    }

    @Override
    public List<UserDto> loadAllUsers() {
        return convertToDtoList(userRepository.findAll());
    }

    @Override
    public UserDto loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToDto)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Username '%s' not found!", username))
                );
    }

    @Override
    public UserDto loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDto)
                .orElseThrow(
                        () -> new EmailNotFoundException(String.format("Email '%s' not found!", email))
                );
    }

    @Override
    public List<UserDto> loadUsersByFirstName(String firstName) {
        return convertToDtoList(userRepository.findByFirstName(firstName));
    }

    @Override
    public List<UserDto> loadUsersByLastName(String lastName) {
        return convertToDtoList(userRepository.findByLastName(lastName));
    }

    @Override
    public List<UserDto> loadFirst10AddedUsers() {
        return convertToDtoList(userRepository.findFirst10ByOrderByCreationDateAsc());
    }

    @Override
    public List<UserDto> loadLast10AddedUsers() {
        return convertToDtoList(userRepository.findFirst10ByOrderByCreationDateDesc());
    }

    @Override
    public List<UserDto> loadEnabledUsers() {
        return convertToDtoList(userRepository.findByEnabled(true));
    }

    @Override
    public List<UserDto> loadAdmins() {
        Role role = roleRepository.findByName(RoleName.ADMIN);
        return convertToDtoList(userRepository.findByRoles(role));
    }

    @Override
    public boolean resetUserPassword(ResetPasswordDetails resetPasswordDetails) {

        userRepository.findByUsername(resetPasswordDetails.getUsername())
                .filter(user -> user.getSecretAnswer().equals(resetPasswordDetails.getSecretAnswer()))
                .map(user -> encryptUserPassword(user, resetPasswordDetails.getNewPassword()))
                .map(this::saveUser)
                .orElseThrow(
                        () -> new ResetPasswordFailedException("Password reset failed!")
                );
        return true;
    }

    private void encryptUserPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private User encryptUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }

    private void assignDefaultRoleToUser(User user) {
        user.setRoles(Collections.singleton(roleRepository.findByName(RoleName.USER)));
    }

    private User saveUser(User user) {
        return userRepository.save(user);
    }

    private List<UserDto> convertToDtoList(List<User> users) {
        return users
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
