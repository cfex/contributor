package com.contributor.payload.response;

import com.contributor.model.enumeration.DevStatus;
import com.contributor.model.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectResponseMinified {
    private String projectId;
    private String title;
    private String intro;
    private String description;
    private DevStatus devStatus;
    private LocalDateTime createdAt;
    private User host;
}
