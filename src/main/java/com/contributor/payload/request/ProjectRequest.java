package com.contributor.payload.request;

import com.contributor.model.enumeration.DevStatus;
import com.contributor.model.user.User;
import lombok.*;

import javax.validation.constraints.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectRequest {
    @NotBlank
    @Size(max = 200, min = 3, message = "The title must contain at least 3 characters and not more than 200")
    private String title;

    @Size(max = 200, min = 3, message = "The intro must contain at least 3 characters and not more than 500")
    private String intro;

    @NotBlank
    @Size(max = 5000, min = 5, message = "The description must contain at least 5 characters and not more than 5000")
    private String description;

    private DevStatus devStatus;
    private Boolean isPublished;

    @NotNull
    @Size(min = 5)
    private String github_url;

    private User host;
}                                                                          