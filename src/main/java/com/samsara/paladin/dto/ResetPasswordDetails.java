package com.samsara.paladin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class ResetPasswordDetails {

    private static final int USERNAME_MIN_SIZE = 3;

    private static final int USERNAME_MAX_SIZE = 35;

    private static final int PASSWORD_MIN_SIZE = 6;

    private static final int PASSWORD_MAX_SIZE = 12;

    private static final int DEFAULT_MIN_SIZE = 1;

    private static final int DEFAULT_MAX_SIZE = 60;

    private static final String USERNAME_PATTERN = "^[a-z][a-z0-9]*$";

    private static final String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}";

    private static final String DEFAULT_PATTERN = "^[a-zA-Z0-9ČĆŠĐŽčćšđž,.!? \\u0027-]+$";

    private static final String USERNAME_MESSAGE = "Username must contain only lower case letters and numbers, "
            + "must start with a letter, and must be 3-35 characters long!";

    private static final String PASSWORD_MESSAGE = "Password must contain at least one number, "
            + "one lowercase and one uppercase letter, and must be 6-12 characters long!";

    private static final String DEFAULT_MESSAGE = "Incorrect format!";

    @NotEmpty
    @Size(min = USERNAME_MIN_SIZE, max = USERNAME_MAX_SIZE)
    @Pattern(regexp = USERNAME_PATTERN,
            message = USERNAME_MESSAGE)
    private String username;

    @NotEmpty
    @Size(min = DEFAULT_MIN_SIZE, max = DEFAULT_MAX_SIZE)
    @Pattern(regexp = DEFAULT_PATTERN,
            message = DEFAULT_MESSAGE)
    private String secretAnswer;

    @NotEmpty
    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE)
    @Pattern(regexp = PASSWORD_PATTERN,
            message = PASSWORD_MESSAGE)
    private String newPassword;
}
