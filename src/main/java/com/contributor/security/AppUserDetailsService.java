package com.contributor.security;

import com.contributor.dao.UserDao;
import com.contributor.exception.errors.AccountAlreadyExistsException;
import com.contributor.exception.errors.EmailVerificationException;
import com.contributor.exception.errors.UserNotFoundException;
import com.contributor.model.user.User;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) {

        User user = userDao.findByUsernameOrEmailIgnoreCase(s, s)
                .orElseThrow(RuntimeException::new);

        if(user.getEmailVerificationStatus()) {
            return AppUserDetailsModel.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .authorities(getAuthorities(user))
                    .build();
        } else {
            throw new EmailVerificationException("Email not verified!");
        }
    }

    private List<GrantedAuthority> getAuthorities(@NotNull User user) {
        return user.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority().name()))
                .collect(Collectors.toList());
    }
}
