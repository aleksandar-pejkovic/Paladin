package com.samsara.paladin.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.samsara.paladin.model.Event;
import com.samsara.paladin.repository.EventRepository;
import com.samsara.paladin.service.email.EmailServiceImpl;

@Component
public class CustomEventListener {

    private final EventRepository eventRepository;

    private final EmailServiceImpl emailService;

    public CustomEventListener(EventRepository eventRepository, EmailServiceImpl emailService) {
        this.eventRepository = eventRepository;
        this.emailService = emailService;
    }

    @EventListener
    public void onApplicationEvent(Event event) {
        eventRepository.save(event);
        System.out.println("New event noted in '" + event.getCategory().name() + "' category.");
        emailService.sendEmailToAdmin(event);
        System.out.println("Email notification sent to admin(s).");
    }
}
