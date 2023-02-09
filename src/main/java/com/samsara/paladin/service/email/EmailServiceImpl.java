package com.samsara.paladin.service.email;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.samsara.paladin.dto.EmailDetails;
import com.samsara.paladin.model.Event;
import com.samsara.paladin.model.User;
import com.samsara.paladin.repository.UserRepository;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender emailSender;

    private final UserRepository userRepository;

    public EmailServiceImpl(JavaMailSender emailSender, UserRepository userRepository) {
        this.emailSender = emailSender;
        this.userRepository = userRepository;
    }

    @Override
    public void sendEmailToAdmin(Event event) {

        List<String> adminEmailsList = userRepository.findAdminEmails();
        String[] adminEmails = adminEmailsList.toArray(new String[0]);

        boolean isAdmin = false;
        Optional<User> optionalUser = userRepository.findByUsername(event.getUsername());
        if (optionalUser.isPresent()) {
            isAdmin = optionalUser.get().isAdmin();
        }

        EmailDetails emailDetails = new EmailDetails(event, isAdmin);

        SimpleMailMessage message = getSimpleMailMessage(adminEmails, emailDetails);
        emailSender.send(message);
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
