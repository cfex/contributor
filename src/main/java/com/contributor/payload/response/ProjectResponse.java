package com.contributor.payload.response;

import com.contributor.model.Comment;
import com.contributor.model.ProjectContributors;
import com.contributor.model.enumeration.DevStatus;
import com.contributor.model.user.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectResponse {
    private String projectId;
    private String title;
    private String intro;
    private String description;
    private DevStatus devStatus;
    private String github_url;
    private Boolean isPublished;
    private User host;
    private LocalDateTime createdAt;
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
    @Builder.Default
    private List<ProjectContributors> contributors = new ArrayList<>();
}
