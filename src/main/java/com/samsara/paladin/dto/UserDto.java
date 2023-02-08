package com.samsara.paladin.dto;

import java.util.Date;

import jakarta.validation.constraints.Email;
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
public class UserDto {

    private static final int NAME_MIN_SIZE = 2;

    private static final int NAME_MAX_SIZE = 20;

    private static final int USERNAME_MIN_SIZE = 3;

    private static final int USERNAME_MAX_SIZE = 35;

    private static final int PASSWORD_MIN_SIZE = 6;

    private static final int PASSWORD_MAX_SIZE = 12;

    private static final int DEFAULT_MIN_SIZE = 1;

    private static final int DEFAULT_MAX_SIZE = 60;

    private static final int ABOUT_MAX_SIZE = 300;

    private static final String NAME_PATTERN = "^[A-ZČĆŠĐŽ][a-zA-ZčćšđžČĆŠĐŽ]*[ \\u0027-]?"
            + "[a-zA-ZčćšđžČĆŠĐŽ]*[a-zčćšđž0-9]$";

    private static final String FIRST_NAME_MESSAGE = "First name can contain letters only "
            + "and must not contain whitespace!";

    private static final String LAST_NAME_MESSAGE = "Last name can contain letters only "
            + "and must not contain whitespace!";

    private static final String USERNAME_PATTERN = "^[a-z][a-z0-9]*$";

    private static final String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}";

    private static final String DEFAULT_PATTERN = "^[a-zA-Z0-9ČĆŠĐŽčćšđž,.!? \\u0027-]+$";

    private static final String USERNAME_MESSAGE = "Username must contain only lower case letters and numbers, "
            + "must start with a letter, and must be 3-35 characters long!";

    private static final String PASSWORD_MESSAGE = "Password must contain at least one number, "
            + "one lowercase and one uppercase letter, and must be 6-12 characters long!";

    private static final String DEFAULT_MESSAGE = "Incorrect format!";

    private Long id;

    @NotEmpty
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    @Pattern(regexp = NAME_PATTERN,
            message = FIRST_NAME_MESSAGE)
    private String firstName;

    @NotEmpty
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    @Pattern(regexp = NAME_PATTERN,
            message = LAST_NAME_MESSAGE)
    private String lastName;

    @NotEmpty
    @Size(min = USERNAME_MIN_SIZE, max = USERNAME_MAX_SIZE)
    @Pattern(regexp = USERNAME_PATTERN,
            message = USERNAME_MESSAGE)
    private String username;

    @NotEmpty
    @Size(max = DEFAULT_MAX_SIZE)
    @Email
    private String email;

    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE)
    @Pattern(regexp = PASSWORD_PATTERN,
            message = PASSWORD_MESSAGE)
    private String password;

    @NotEmpty
    @Size(min = DEFAULT_MIN_SIZE, max = ABOUT_MAX_SIZE)
    @Pattern(regexp = DEFAULT_PATTERN,
            message = DEFAULT_MESSAGE)
    private String about;

    private Integer heroCount;

    private Date creationDate;

    private Boolean enabled;

    @NotEmpty
    @Size(min = DEFAULT_MIN_SIZE, max = DEFAULT_MAX_SIZE)
    @Pattern(regexp = DEFAULT_PATTERN,
            message = DEFAULT_MESSAGE)
    private String secretQuestion;

    @NotEmpty
    @Size(min = DEFAULT_MIN_SIZE, max = DEFAULT_MAX_SIZE)
    @Pattern(regexp = DEFAULT_PATTERN,
            message = DEFAULT_MESSAGE)
    private String secretAnswer;
}
