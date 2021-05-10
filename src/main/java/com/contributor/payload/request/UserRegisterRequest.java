package com.contributor.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterRequest {

    @NotNull
    @Size(min = 3, max = 50, message = "First name must contain between 3 and 50 characters")
    private String name;

    @NotNull
    @Size(min = 3, max = 50, message = "First name must contain between 3 and 50 characters")
    private String username;

    @NotNull
    @Size(min = 3, max = 50, message = "First name must contain between 3 and 50 characters")
    private String nickname;

    @NotNull
    @Email
    @Size(min = 6, max = 100, message = "Email can't be empty and it should be between 6 and 100 characters.")
    private String email;

    private String school;

    @NotNull
    @Past
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthDate;

    @NotNull
    @Size(min = 6, message = "Password must contain 6 or more characters. Please consider using " +
            "capital letters and special characters")
    private String password;

}
