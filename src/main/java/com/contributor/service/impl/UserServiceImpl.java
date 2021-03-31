package com.contributor.service.impl;

import com.contributor.dao.UserDao;
import com.contributor.exception.AccountAlreadyExistsException;
import com.contributor.model.user.User;
import com.contributor.model.verification.VerificationToken;
import com.contributor.payload.response.UserDetailsResponse;
import com.contributor.service.UserService;
import com.contributor.shared.UserDto;
import com.contributor.config.ApplicationUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ApplicationUtils utils;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto findByUsername(String username) {
        return modelMapper.map(userDao.findByUsernameIgnoreCase(username), UserDto.class);
    }

    @Override
    @Transactional
    public UserDetailsResponse createUser(UserDto userDto) throws AccountAlreadyExistsException {
        if(userAlreadyExists(userDto.getUsername(), userDto.getEmail())){
            throw new AccountAlreadyExistsException();
        }

        User userMap = modelMapper.map(userDto, User.class);
        userMap.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userMap.setUserId(utils.generateUUID());
        User savedUser = userDao.save(userMap);

        return modelMapper.map(savedUser, UserDetailsResponse.class);
    }

    @Override
    @Transactional
    public void verifyUserEmail(User user, VerificationToken verificationToken) {
        user.setEmailVerificationStatus(true);
        user.setEmailVerificationToken(verificationToken.getToken());
        userDao.save(user);
    }

    private boolean userAlreadyExists(String username, String email) {
        return userDao.findByUsernameIgnoreCase(username).isPresent()
                || userDao.findByEmailIgnoreCase(email).isPresent();
    }
}
