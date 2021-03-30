package com.contributor.model.event;

import com.contributor.payload.response.UserDetailsResponse;
import com.contributor.service.UserService;
import com.contributor.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenService tokenService;
    private final MessageSource messageSource;
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(RegistrationCompleteEvent event) {
        UserDetailsResponse user = event.getUserDetails();
        String token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(token, user);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationURL = event.getApplicationURL() + "/registrationConfirm?token=" + token;
        String message = "hello";

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(recipientAddress);
        emailMessage.setSubject(subject);
        emailMessage.setText(message + "\r\n" + "http://localhost:8080/auth" + confirmationURL);
        mailSender.send(emailMessage);
    }
}
