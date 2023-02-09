package com.samsara.paladin.dto;

import com.samsara.paladin.model.Event;

import lombok.Getter;

@Getter
public class EmailDetails {

    private final Event event;

    private final boolean isAdmin;

    private String subject;

    private String text;

    public EmailDetails(Event event, boolean isAdmin) {
        this.event = event;
        this.isAdmin = isAdmin;
        setSubject();
        setText();
    }

    public void setSubject() {
        this.subject = String.format(
                "%s performed on %s object %s",
                event.getAction().name(),
                event.getCategory().name(),
                event.getObject()
        );
    }

    public void setText() {
        this.text = String.format(
                "Following action '%s' was performed "
                        + "in category '%s' "
                        + "over an object '%s' "
                        + "by '%s' with username '%s'.",
                event.getAction().name(),
                event.getCategory().name(),
                event.getObject(),
                isAdmin ? "ADMIN" : "USER",
                event.getUsername()
        );
    }
}
