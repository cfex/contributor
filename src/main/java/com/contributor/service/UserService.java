package com.contributor.service;

import com.contributor.model.user.User;
import com.contributor.model.verification.VerificationToken;
import com.contributor.payload.response.UserDetailsResponse;
import com.contributor.shared.UserDto;

public interface UserService {
    UserDetailsResponse findByUsername(String username);

    UserDetailsResponse findByUserIdOrUsernameAndRetrieveOnlyPublished(String username);

    UserDetailsResponse createUser(UserDto userDto);

    void verifyUserEmail(User user, VerificationToken verificationToken);
}
