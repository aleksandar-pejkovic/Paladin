package com.samsara.paladin.service.email;

import com.samsara.paladin.model.Event;

public interface EmailService {

    void sendEmailToAdmin(Event event);
}
