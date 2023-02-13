package com.samsara.paladin.service.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsara.paladin.model.Event;
import com.samsara.paladin.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> loadAllEvents() {
        return eventRepository.findAll();
    }
}
