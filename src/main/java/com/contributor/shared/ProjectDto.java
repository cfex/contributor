package com.contributor.shared;

import com.contributor.model.Stack;
import com.contributor.model.enumeration.DevStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private String title;
    private String intro;
    private String description;
    private DevStatus developmentStatus;
    private List<Stack> stacks;
    private String github_url;
    private Boolean published;
    private LocalDateTime createdAt;
}
