package com.samsara.paladin.dto;

import java.util.Date;

import com.samsara.paladin.configuration.validation.user.about.About;
import com.samsara.paladin.configuration.validation.user.name.Name;
import com.samsara.paladin.configuration.validation.user.password.Password;
import com.samsara.paladin.configuration.validation.user.securityQuestion.SecurityQuestion;
import com.samsara.paladin.configuration.validation.user.username.Username;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private static final int DEFAULT_MIN_SIZE = 1;

    private static final int DEFAULT_MAX_SIZE = 60;

    private static final String DEFAULT_PATTERN = "^[a-zA-Z0-9ČĆŠĐŽčćšđž,.!? \\u0027-]+$";

    private static final String DEFAULT_MESSAGE = "Incorrect format!";

    private Long id;

    @Name
    private String firstName;

    @Name
    private String lastName;

    @Username
    private String username;

    @Email
    private String email;

    @Password
    private String password;

    @About
    private String about;

    private Integer heroCount;

    private Date creationDate;

    private Boolean enabled;

    @SecurityQuestion
    private String secretQuestion;

    @SecurityQuestion
    private String secretAnswer;
}
