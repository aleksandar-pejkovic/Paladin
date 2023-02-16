package com.samsara.paladin.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.samsara.paladin.model.Event;
import com.samsara.paladin.repository.EventRepository;
import com.samsara.paladin.service.email.EmailServiceImpl;

@Component
public class CustomEventListener {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @EventListener
    public void onApplicationEvent(Event event) {
        eventRepository.save(event);
        System.out.println(event.getDate() + " Event noted in '" + event.getCategory().name() + "' category.");
        emailService.sendEmailToAdmin(event);
        System.out.println("Email notification sent to admin(s).");
    }
}
