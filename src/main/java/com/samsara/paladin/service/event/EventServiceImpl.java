package com.samsara.paladin.service.event;

import java.util.List;

import org.springframework.stereotype.Service;

import com.samsara.paladin.model.Event;
import com.samsara.paladin.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> loadAllEvents() {
        return eventRepository.findAll();
    }
}
