package com.samsara.paladin.configuration.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.samsara.paladin.model.Permission;
import com.samsara.paladin.model.Role;
import com.samsara.paladin.model.User;
import com.samsara.paladin.repository.PermissionRepository;
import com.samsara.paladin.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<User> user = userRepository.findUserWithRolesFetched(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username '" + username + "' not found!");
        } else {
            return buildUserDetails(user.get());
        }
    }

    private org.springframework.security.core.userdetails.User buildUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<String> permissions = getPermissions(roles);
        return getGrantedAuthorities(permissions);
    }

    private List<String> getPermissions(Collection<Role> roles) {
        List<String> grantedAuthorities = new ArrayList<>();
        List<Permission> permissions = new ArrayList<>();
        for (Role role : roles) {
            grantedAuthorities.add(role.getName().getAuthority());
            permissions.addAll(permissionRepository.getPermissionsByRole(role.getName()));
        }
        for (Permission permission : permissions) {
            grantedAuthorities.add(permission.getName().name());
        }
        return grantedAuthorities;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }
}
