package com.contributor.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequest {
    @NotNull
    @Size(min = 2, max = 500, message = "Comment must contain at least 3 characters, and not more than 500")
    private String text;
}
