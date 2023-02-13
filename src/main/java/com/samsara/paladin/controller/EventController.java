package com.samsara.paladin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samsara.paladin.model.Event;
import com.samsara.paladin.service.event.EventServiceImpl;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventServiceImpl eventService;

    @Secured("SCOPE_READ_EVENTS")
    @GetMapping
    public List<Event> fetchAllEvents() {
        return eventService.loadAllEvents();
    }
}
