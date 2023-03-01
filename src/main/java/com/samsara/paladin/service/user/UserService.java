package com.samsara.paladin.service.user;

import java.util.List;
import java.util.Set;

import com.samsara.paladin.dto.ResetPasswordDetails;
import com.samsara.paladin.dto.UserDto;

public interface UserService {

    Set<UserDto> searchUsers(String searchTerm);

    UserDto registerUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto grantAdminRoleToUser(String username);

    void deleteUser(String username);

    List<UserDto> loadAllUsers();

    UserDto loadUserByUsername(String username);

    UserDto loadUserByEmail(String email);

    List<UserDto> loadUsersByFirstName(String firstName);

    List<UserDto> loadUsersByLastName(String lastName);

    List<UserDto> loadFirst10AddedUsers();

    List<UserDto> loadLast10AddedUsers();

    List<UserDto> loadEnabledUsers();

    List<UserDto> loadAdmins();

    boolean resetUserPassword(ResetPasswordDetails resetPasswordDetails);
}
