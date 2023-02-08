package com.samsara.paladin.events;

import java.util.Date;

import org.springframework.context.ApplicationEvent;

import com.samsara.paladin.enums.EventAction;
import com.samsara.paladin.enums.EventCategory;

import lombok.Getter;

@Getter
public class EventDetails extends ApplicationEvent {

    private EventCategory category;

    private String object;

    private EventAction action;

    private String username;

    private Date date;

    public EventDetails(
            Object source,
            EventCategory category,
            String object,
            EventAction action,
            String username,
            Date date
    ) {
        super(source);
        this.category = category;
        this.object = object;
        this.action = action;
        this.username = username;
        this.date = date;
    }
}
