package com.contributor.service;

import com.contributor.model.user.User;
import com.contributor.model.verification.VerificationToken;
import com.contributor.payload.response.UserDetailsResponse;

import java.util.concurrent.CompletableFuture;

public interface VerificationTokenService {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    CompletableFuture<VerificationToken> createVerificationToken(UserDetailsResponse user);

    VerificationToken getVerificationToken(String token);
}
