package com.contributor.model.event;

import com.contributor.service.ProjectService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class ContributionRequestListener implements ApplicationListener<ContributionRequestEvent> {

    private final ProjectService projectService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContributionRequestEvent.class);

    @Override
    @Async
    public void onApplicationEvent(ContributionRequestEvent event) {
        LOGGER.info("[Request contribution event triggered on thread {}] [Triggered on {}]",
                Thread.currentThread().getName(), LocalDateTime.now());

        this.requestContribution(event);
    }

    private void requestContribution(ContributionRequestEvent event) {
        projectService.requestContribution(event.getAuthUser(), event.getProjectId());
    }
}
