package com.contributor.service.impl;

import com.contributor.dao.UserDao;
import com.contributor.dao.VerificationTokenDao;
import com.contributor.exception.NoTokenFoundException;
import com.contributor.exception.TokenNotFoundException;
import com.contributor.exception.UserNotFoundException;
import com.contributor.model.user.User;
import com.contributor.model.verification.VerificationToken;
import com.contributor.payload.response.UserDetailsResponse;
import com.contributor.service.EmailVerificationService;
import com.contributor.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@PropertySource("classpath:utils.yml")
@Service
public class VerificationTokenImpl implements VerificationTokenService {

    private final UserDao userDao;
    private final Environment environment;
    private final VerificationTokenDao verificationTokenDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationTokenService.class);

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenDao.findByToken(token).orElseThrow(TokenNotFoundException::new);
    }

    @Override
    public VerificationToken findByUser(User user) {
        return verificationTokenDao.findByUser(user).orElseThrow(TokenNotFoundException::new);
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<VerificationToken> createVerificationToken(UserDetailsResponse userDetails) {
        LOGGER.info("Creating token on -> {}", Thread.currentThread().getName());
        User user = userDao.findByUsernameIgnoreCase(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationToken.setExpirationDate(calculateExpirationDate(
                Integer.parseInt(Objects.requireNonNull(environment.getProperty("expiration-time")))));

        return CompletableFuture.completedFuture(verificationTokenDao.save(verificationToken));
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenDao.findByToken(token).orElseThrow(NoTokenFoundException::new);
    }

    private Date calculateExpirationDate(int expirationTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expirationTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
}
