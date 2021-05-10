package com.contributor.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String userId;
    private String name;
    private String username;
    private String nickname;
    private String bio;
    private String school;
    private String email;
    private String password;
    private Date birthDate;
    private LocalDateTime creationDate;
    private String emailVerificationToken;

    @Builder.Default
    private Boolean emailVerificationStatus = false;
}
