package com.samsara.paladin.service.email;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.samsara.paladin.dto.EmailDetails;
import com.samsara.paladin.model.Event;
import com.samsara.paladin.repository.RoleRepository;
import com.samsara.paladin.repository.UserRepository;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Async
    @Override
    public void sendEmailToAdmin(Event event) {

        List<String> adminEmailsList = userRepository.findAdminEmails();
        String[] adminEmails = adminEmailsList.toArray(new String[0]);

        boolean isAdmin = roleRepository.hasAdminRole(event.getUsername());

        EmailDetails emailDetails = new EmailDetails(event, isAdmin);

        SimpleMailMessage message = getSimpleMailMessage(adminEmails, emailDetails);
        try {
            emailSender.send(message);
        } catch (MailSendException e) {
            System.out.println("Mail server connection failed. Email was not sent to admin(s)!");
        }

    }

    private SimpleMailMessage getSimpleMailMessage(String[] adminEmails, EmailDetails emailDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(adminEmails);
        message.setSubject(emailDetails.getSubject());
        message.setText(emailDetails.getText());
        return message;
    }
}
