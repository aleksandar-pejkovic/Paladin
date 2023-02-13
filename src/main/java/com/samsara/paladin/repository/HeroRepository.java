package com.samsara.paladin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.samsara.paladin.enums.HeroType;
import com.samsara.paladin.model.Hero;
import com.samsara.paladin.model.User;

@Repository
public interface HeroRepository extends ListCrudRepository<Hero, Long> {

    Optional<Hero> findByName(String name);

    List<Hero> findByUser(User user);

    List<Hero> findByType(HeroType type);

    List<Hero> findByLevel(Integer level);

    List<Hero> findByLevelGreaterThan(Integer level);

    List<Hero> findByLevelLessThan(Integer level);

    List<Hero> findFirst10ByOrderByLevelDesc();

    List<Hero> findFirst10ByOrderByCreationDateDesc();

    boolean existsByName(String name);
}
