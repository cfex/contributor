package com.contributor.model.event;

import com.contributor.security.AppUserDetailsModel;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContributionRequestEvent extends ApplicationEvent {
    private final AppUserDetailsModel authUser;
    private final String projectId;
    private final LocalDateTime requestedOn;

    public ContributionRequestEvent(AppUserDetailsModel auth, String project_id) {
        super(auth);
        authUser = auth;
        projectId = project_id;
        this.requestedOn = LocalDateTime.now();
    }
}
