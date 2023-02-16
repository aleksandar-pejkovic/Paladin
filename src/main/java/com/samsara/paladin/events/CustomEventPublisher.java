package com.samsara.paladin.events;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.samsara.paladin.enums.EventAction;
import com.samsara.paladin.enums.EventCategory;
import com.samsara.paladin.model.Event;

@Component
public class CustomEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(
            final EventCategory category,
            final String object,
            final EventAction action
    ) {

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Publishing custom event. ");

        Event event = Event.builder()
                .category(category)
                .object(object)
                .action(action)
                .username(loggedInUsername)
                .date(new Date())
                .build();

        applicationEventPublisher.publishEvent(event);
    }
}
