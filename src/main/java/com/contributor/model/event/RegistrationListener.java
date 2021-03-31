package com.contributor.model.event;

import com.contributor.model.verification.VerificationToken;
import com.contributor.payload.response.UserDetailsResponse;
import com.contributor.service.EmailVerificationService;
import com.contributor.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenService tokenService;
    private final EmailVerificationService emailVerificationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationListener.class);

    @Override
    @Async
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        LOGGER.info("Event triggered on -> {}", Thread.currentThread().getName());
        this.confirmRegistration(event);
    }

    private void confirmRegistration(RegistrationCompleteEvent event) {
        UserDetailsResponse user = event.getUserDetails();
        CompletableFuture<VerificationToken> verificationToken = tokenService.createVerificationToken(user);
        emailVerificationService.sendMail(verificationToken, user.getEmail(), event);
    }
}
