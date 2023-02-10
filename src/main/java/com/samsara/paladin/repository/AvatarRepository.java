package com.samsara.paladin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.samsara.paladin.model.Avatar;

@Repository
public interface AvatarRepository extends ListCrudRepository<Avatar, Long> {

    @Query(
            "SELECT u.avatar "
                    + "FROM User u "
                    + "WHERE u.username = :username"
    )
    Optional<Avatar> findAvatarByUser(String username);
}
