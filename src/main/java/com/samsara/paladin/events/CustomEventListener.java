package com.samsara.paladin.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.samsara.paladin.model.Event;
import com.samsara.paladin.repository.EventRepository;

@Component
public class CustomEventListener {

    private final EventRepository eventRepository;

    public CustomEventListener(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @EventListener
    public void onApplicationEvent(Event event) {
        eventRepository.save(event);
        System.out.println("New event noted in '" + event.getCategory().name() + "' category.");
    }
}
