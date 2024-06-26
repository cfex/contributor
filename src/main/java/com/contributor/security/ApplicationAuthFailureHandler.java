package com.contributor.security;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Component
public class ApplicationAuthFailureHandler implements AuthenticationFailureHandler {

    private final MessageSource messageSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAuthFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        LOGGER.info("Application auth failure handler triggered");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }


}
