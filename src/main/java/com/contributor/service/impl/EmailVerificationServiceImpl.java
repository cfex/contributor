package com.contributor.service.impl;

import com.contributor.exception.InvalidEmailException;
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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final JavaMailSender mailSender;
    private final MessageSource messageSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVerificationService.class);

    @SneakyThrows
    @Override
    @Async
    public void sendMail(CompletableFuture<VerificationToken> verificationToken, String recipientEmail, RegistrationCompleteEvent event) {
        LOGGER.info("Creating mail on -> {}", Thread.currentThread().getName());
        if (isEmailValid(recipientEmail)) {
            String subject = "Registration Confirmation";
            String confirmationURL = event.getApplicationURL() + "/registrationConfirm?token=" + verificationToken.get().getToken();
            String message = "Please click on the link below:";
            SimpleMailMessage emailMessage = new SimpleMailMessage();
//            emailMessage.setFrom();
            emailMessage.setTo(recipientEmail);
            emailMessage.setSubject(subject);
            emailMessage.setText(message + "\r\n" + "http://localhost:8080/auth" + confirmationURL);
            mailSender.send(emailMessage);
        } else {
            throw new InvalidEmailException();
        }
    }

    @Override
    public boolean isEmailValid(String email) {
        LOGGER.info("Verifying mail");
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }

        return result;
    }
}
