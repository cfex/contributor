package com.contributor.service;

import com.contributor.model.event.RegistrationCompleteEvent;
import com.contributor.model.verification.VerificationToken;

import java.util.concurrent.CompletableFuture;

public interface EmailVerificationService {
    void sendMail(CompletableFuture<VerificationToken> verificationToken, String recipient, RegistrationCompleteEvent event);

    boolean isEmailValid(String email);
}
