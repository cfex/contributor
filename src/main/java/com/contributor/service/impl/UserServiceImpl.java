package com.contributor.service.impl;

import com.contributor.config.AvatarGenerator;
import com.contributor.dao.AuthorityDao;
import com.contributor.dao.UserDao;
import com.contributor.exception.AccountAlreadyExistsException;
import com.contributor.exception.UserNotFoundException;
import com.contributor.model.Project;
import com.contributor.model.enumeration.Authorities;
import com.contributor.model.user.User;
import com.contributor.model.verification.VerificationToken;
import com.contributor.payload.response.ProjectResponseMinified;
import com.contributor.payload.response.UserDetailsResponse;
import com.contributor.service.UserService;
import com.contributor.shared.UserDto;
import com.contributor.config.ApplicationUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ApplicationUtils utils;
    private final ModelMapper modelMapper;
    private final AuthorityDao authorityDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetailsResponse findByUsername(String username) {
        return modelMapper.map(userDao.findByUsername(username).orElseThrow(), UserDetailsResponse.class);
    }

    @SneakyThrows
    @Override
    public UserDetailsResponse findByUserIdOrUsernameAndRetrieveOnlyPublished(String query) {
        User user = userDao.findByUserIdOrUsername(query, query)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        List<ProjectResponseMinified> hosted = user.getHosted().stream()
                .filter(Project::getIsPublished)
                .map(project -> modelMapper.map(project, ProjectResponseMinified.class))
                .collect(Collectors.toList());
        UserDetailsResponse map = modelMapper.map(user, UserDetailsResponse.class);
        map.setHosted(hosted);

        return map;
    }

    @SneakyThrows
    @Override
    @Transactional
    public UserDetailsResponse createUser(UserDto userDto) {
        if (userAlreadyExists(userDto.getUsername(), userDto.getEmail())) {
            throw new AccountAlreadyExistsException("Account with that username/email already exists!");
        }
        String generatedUUID = utils.generateUUID();
        User userMap = modelMapper.map(userDto, User.class);
        userMap.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userMap.setUserId(generatedUUID);
        userMap.setAvatar(AvatarGenerator.generate(generatedUUID));
        userMap.setAuthority(authorityDao.findByAuthority(Authorities.ROLE_USER));
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
