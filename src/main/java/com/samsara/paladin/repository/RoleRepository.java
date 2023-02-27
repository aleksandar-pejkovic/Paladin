package com.samsara.paladin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.samsara.paladin.enums.RoleName;
import com.samsara.paladin.model.Role;

@Repository
public interface RoleRepository extends ListCrudRepository<Role, Long> {

    @Query(
            "SELECT CASE WHEN EXISTS ( "
                    + "SELECT u "
                    + "FROM User u "
                    + "JOIN u.roles r "
                    + "WHERE u.username = :username "
                    + "AND r.name = 'ADMIN' "
                    + ") THEN true ELSE false END "
    )
    boolean hasAdminRole(@Param("username") String username);

    Role findByName(RoleName name);
}
