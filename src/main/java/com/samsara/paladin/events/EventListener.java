package com.samsara.paladin.events;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.samsara.paladin.model.Event;
import com.samsara.paladin.repository.EventRepository;

@Component
public class EventListener implements ApplicationListener<EventDetails> {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    public EventListener(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void onApplicationEvent(EventDetails eventDetails) {
        Event event = modelMapper.map(eventDetails, Event.class);
        eventRepository.save(event);
        System.out.println("New event noted in '" + event.getCategory().name() + "' category.");
    }
}
