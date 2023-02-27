package com.samsara.paladin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.samsara.paladin.model.Role;
import com.samsara.paladin.model.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String firstName);

    List<User> findFirst10ByOrderByCreationDateAsc();

    List<User> findFirst10ByOrderByCreationDateDesc();

    List<User> findByEnabled(boolean enabled);

    List<User> findByRoles(Role role);

    @Query(
            "SELECT u.email "
                    + "FROM User u "
                    + "JOIN u.roles r "
                    + "ON r.name = 'ADMIN' "
    )
    List<String> findAdminEmails();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
