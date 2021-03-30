package com.contributor.service;

import com.contributor.model.user.User;
import com.contributor.model.verification.VerificationToken;
import com.contributor.payload.response.UserDetailsResponse;

import java.sql.Timestamp;

public interface VerificationTokenService {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    void createVerificationToken(String token, UserDetailsResponse user);

    VerificationToken getVerificationToken(String token);

    Timestamp calculateExpirationTime(int expirationTime);
}
