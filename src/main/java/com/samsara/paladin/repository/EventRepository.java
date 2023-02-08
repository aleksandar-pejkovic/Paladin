package com.samsara.paladin.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.samsara.paladin.model.Event;

@Repository
public interface EventRepository extends ListCrudRepository<Event, Long> {
}
