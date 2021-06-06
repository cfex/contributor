package com.contributor.payload.request;

import com.contributor.model.enumeration.DevStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectUpdateRequest {
    private String title;
    private String intro;
    private String description;
    private DevStatus devStatus;
    private Boolean isPublished;
    private String github_url;
}
