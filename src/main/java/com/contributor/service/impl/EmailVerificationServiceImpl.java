package com.contributor.service.impl;

import com.contributor.exception.InvalidEmailException;
import com.contributor.exception.TokenNotFoundException;
import com.contributor.model.event.RegistrationCompleteEvent;
import com.contributor.model.verification.VerificationToken;
import com.contributor.service.EmailVerificationService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final JavaMailSender mailSender;
    private final MessageSource messageSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVerificationService.class);

    @SneakyThrows
    @Override
    @Async
    @Transactional
    public void sendMail(CompletableFuture<VerificationToken> verificationToken, String recipientEmail, RegistrationCompleteEvent event) {
        LOGGER.info("Creating mail on -> {}", Thread.currentThread().getName());
        if (isEmailValid(recipientEmail)) {
            String subject = "Registration Confirmation";
            String confirmationURL = event.getApplicationURL() + "/registrationConfirm?token=" + verificationToken.get().getToken();
            String message = "Please click on the link below:";
            SimpleMailMessage emailMessage = new SimpleMailMessage();
            emailMessage.setTo(recipientEmail);
            emailMessage.setSubject(subject);
            emailMessage.setText(message + "\r\n" + "http://localhost:8080/auth" + confirmationURL);
            mailSender.send(emailMessage);
        } else {
            throw new TokenNotFoundException("Error creating token. Please try again.");
        }
    }

    @SneakyThrows
    @Override
    public boolean isEmailValid(String email) {
        LOGGER.info("Verifying mail");
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new InvalidEmailException("Email address is not valid.");
        }

        return true;
    }
}
