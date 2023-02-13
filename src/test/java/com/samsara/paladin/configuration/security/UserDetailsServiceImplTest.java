package com.samsara.paladin.configuration.security;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.samsara.paladin.enums.PermissionName;
import com.samsara.paladin.enums.RoleName;
import com.samsara.paladin.model.Permission;
import com.samsara.paladin.model.Role;
import com.samsara.paladin.model.User;
import com.samsara.paladin.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Load user by username test when credentials are valid then correct")
    void loadUserByUsernameTestWhenCredentialsValidThenCorrect() {

        Set<Permission> permissions = new HashSet<>();
        permissions.add(Permission.builder()
                .id(1L)
                .name(PermissionName.READ)
                .build()
        );
        permissions.add(Permission.builder()
                .id(2L)
                .name(PermissionName.UPDATE)
                .build()
        );

        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder()
                .id(1L)
                .name(RoleName.USER)
                .permissions(permissions)
                .build()
        );

        User user = User.builder()
                .username("test")
                .password("testCryptPassword")
                .enabled(true)
                .roles(roles)
                .build();

        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        UserDetails authorizedUser = userDetailsService.loadUserByUsername(user.getUsername());

        verify(userRepository).findByUsername(user.getUsername());

        assertAll(
                () -> assertEquals(user.getUsername(), authorizedUser.getUsername()),
                () -> assertEquals(user.getPassword(), authorizedUser.getPassword()),
                () -> assertTrue(authorizedUser.isEnabled()),
                () -> assertTrue(authorizedUser.isAccountNonExpired()),
                () -> assertTrue(authorizedUser.isCredentialsNonExpired()),
                () -> assertTrue(authorizedUser.isAccountNonLocked()),
                () -> assertNotNull(authorizedUser.getAuthorities())
        );
    }

    @Test
    @DisplayName("Load user by username test when credentials not valid then throw")
    void loadUserByUsernameTestWhenCredentialsNotValidThenThrow() {

        when(userRepository.findByUsername("test"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails authorizedUser = userDetailsService.loadUserByUsername("test");
            verify(userRepository).findByUsername("test");
            assertNull(authorizedUser);
        });
    }
}