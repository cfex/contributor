package com.contributor.service.impl;

import com.contributor.dao.UserDao;
import com.contributor.dao.VerificationTokenDao;
import com.contributor.exception.TokenNotFoundException;
import com.contributor.model.user.User;
import com.contributor.model.verification.VerificationToken;
import com.contributor.payload.response.UserDetailsResponse;
import com.contributor.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
@Service
public class VerificationTokenImpl implements VerificationTokenService {

    private final VerificationTokenDao verificationTokenDao;
    private final UserDao userDao;

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
    public void createVerificationToken(String token, UserDetailsResponse userDetails) {
        User user = userDao.findByUsernameIgnoreCase(userDetails.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User with that username doesn't exists"));

        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationToken.setExpirationDate(calculateExpirationDate(60 * 24));
        verificationTokenDao.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return null;
    }

    @Override
    public Timestamp calculateExpirationTime(int expirationTime) {
        return null;
    }

    private Date calculateExpirationDate(int expirationTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expirationTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
}
