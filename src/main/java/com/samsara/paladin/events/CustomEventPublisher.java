package com.samsara.paladin.events;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.samsara.paladin.enums.EventAction;
import com.samsara.paladin.enums.EventCategory;
import com.samsara.paladin.model.Event;

@Component
public class CustomEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CustomEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(
            final EventCategory category,
            final String object,
            final EventAction action,
            final String username
    ) {
        System.out.println("Publishing custom event. ");

        Event event = Event.builder()
                .category(category)
                .object(object)
                .action(action)
                .username(username)
                .date(new Date())
                .build();

        applicationEventPublisher.publishEvent(event);
    }
}
