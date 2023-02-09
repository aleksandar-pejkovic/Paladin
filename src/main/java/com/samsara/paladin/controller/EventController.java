package com.samsara.paladin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samsara.paladin.model.Event;
import com.samsara.paladin.service.event.EventServiceImpl;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventServiceImpl eventService;

    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> fetchAll() {
        return eventService.loadAllEvents();
    }
}
