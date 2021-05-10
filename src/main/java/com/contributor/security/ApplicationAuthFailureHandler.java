package com.contributor.security;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@AllArgsConstructor
@Component
public class ApplicationAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final MessageSource messageSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAuthFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        LOGGER.info("Application auth failure handler triggered");
        setDefaultFailureUrl("/auth/error");
        super.onAuthenticationFailure(request, response, exception);
//        String message = messageSource.getMessage("messages.error", null, request.getLocale());
//        String message = "Application auth failure handler triggered";
//        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, message);
    }
}
