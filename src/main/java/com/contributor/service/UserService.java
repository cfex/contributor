package com.contributor.service;

import com.contributor.exception.AccountAlreadyExistsException;
import com.contributor.model.user.User;
import com.contributor.model.verification.VerificationToken;
import com.contributor.payload.response.UserDetailsResponse;
import com.contributor.shared.UserDto;


public interface UserService {
    UserDto findByUsername(String username);

    UserDetailsResponse createUser(UserDto userDto) throws AccountAlreadyExistsException;

    void verifyUserEmail(User user, VerificationToken verificationToken);
}
