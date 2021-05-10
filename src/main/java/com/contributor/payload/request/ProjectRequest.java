package com.contributor.payload.request;

import com.contributor.model.enumeration.DevStatus;
import com.contributor.model.user.User;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectRequest {
    private String title;
    private String intro;
    private String description;
    private DevStatus devStatus;
    private Boolean published;
    private String github_url;
    private User host; 
}                                                                          