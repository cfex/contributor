package com.contributor.payload.response;

import com.contributor.model.enumeration.AccountLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponse {
    private String userId;
    private String name;
    private String username;
    private String nickname;
    private String bio;
    private String school;
    private String email;
    private AccountLevel accountLevel;
    private String avatar;
    @Builder.Default
    private List<ProjectResponseMinified> hosted = new ArrayList<>();
}
