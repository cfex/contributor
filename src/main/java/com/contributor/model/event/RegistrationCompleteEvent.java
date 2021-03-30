package com.contributor.model.event;

import com.contributor.payload.response.UserDetailsResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private UserDetailsResponse userDetails;
    private Locale locale;
    private String applicationURL;

    public RegistrationCompleteEvent(UserDetailsResponse userDetails, Locale locale, String applicationURL) {
        super(userDetails);
        this.userDetails = userDetails;
        this.locale = locale;
        this.applicationURL = applicationURL;
    }
}
