package com.samsara.paladin.events;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.samsara.paladin.enums.EventAction;
import com.samsara.paladin.enums.EventCategory;

@Component
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(
            final EventCategory category,
            final String object,
            final EventAction action,
            final String username
    ) {
        System.out.println("Publishing custom event. ");

        EventDetails event = new EventDetails(
                this,
                category,
                object,
                action,
                username,
                new Date()
        );

        applicationEventPublisher.publishEvent(event);
    }
}
