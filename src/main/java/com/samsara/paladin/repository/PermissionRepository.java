package com.samsara.paladin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.samsara.paladin.enums.PermissionName;
import com.samsara.paladin.enums.RoleName;
import com.samsara.paladin.model.Permission;

@Repository
public interface PermissionRepository extends ListCrudRepository<Permission, Long> {

    @Query(
            "SELECT r.permissions "
                    + "FROM Role r "
                    + "WHERE r.name = :name "
    )
    List<Permission> getPermissionsByRole(@Param("name") RoleName name);

    Permission findByName(PermissionName name);
}
