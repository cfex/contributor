package com.contributor.shared;

import com.contributor.model.enumeration.DevStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private String title;
    private String intro;
    private String description;
    private DevStatus devStatus;
    private String github_url;
    private Boolean isPublished;
    private LocalDateTime createdAt;
}
