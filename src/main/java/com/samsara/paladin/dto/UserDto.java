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

    private static final int ABOUT_MAX_SIZE = 300;

    private static final String NAME_PATTERN = "^[A-ZČĆŠĐŽ][a-zA-ZčćšđžČĆŠĐŽ]*[ \\u0027-]?"
            + "[a-zA-ZčćšđžČĆŠĐŽ]*[a-zčćšđž0-9]$";

    private static final String FIRST_NAME_MESSAGE = "First name can contain letters only "
            + "and must not contain whitespace!";

    private static final String LAST_NAME_MESSAGE = "Last name can contain letters only "
            + "and must not contain whitespace!";

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
    @Size(min = ValidationConstants.USERNAME_MIN_SIZE, max = ValidationConstants.USERNAME_MAX_SIZE)
    @Pattern(regexp = ValidationConstants.USERNAME_PATTERN,
            message = ValidationConstants.USERNAME_MESSAGE)
    private String username;

    @NotEmpty
    @Size(max = ValidationConstants.DEFAULT_MAX_SIZE)
    @Email
    private String email;

    @Size(min = ValidationConstants.PASSWORD_MIN_SIZE, max = ValidationConstants.PASSWORD_MAX_SIZE)
    @Pattern(regexp = ValidationConstants.PASSWORD_PATTERN,
            message = ValidationConstants.PASSWORD_MESSAGE)
    private String password;

    @NotEmpty
    @Size(min = ValidationConstants.DEFAULT_MIN_SIZE, max = ABOUT_MAX_SIZE)
    @Pattern(regexp = ValidationConstants.DEFAULT_PATTERN,
            message = ValidationConstants.DEFAULT_MESSAGE)
    private String about;

    private Integer heroCount;

    private Date creationDate;

    private Boolean enabled;

    @NotEmpty
    @Size(min = ValidationConstants.DEFAULT_MIN_SIZE, max = ValidationConstants.DEFAULT_MAX_SIZE)
    @Pattern(regexp = ValidationConstants.DEFAULT_PATTERN,
            message = ValidationConstants.DEFAULT_MESSAGE)
    private String secretQuestion;

    @NotEmpty
    @Size(min = ValidationConstants.DEFAULT_MIN_SIZE, max = ValidationConstants.DEFAULT_MAX_SIZE)
    @Pattern(regexp = ValidationConstants.DEFAULT_PATTERN,
            message = ValidationConstants.DEFAULT_MESSAGE)
    private String secretAnswer;
}
