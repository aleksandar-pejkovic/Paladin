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

    @NotEmpty
    @Size(min = ValidationConstants.USERNAME_MIN_SIZE, max = ValidationConstants.USERNAME_MAX_SIZE)
    @Pattern(regexp = ValidationConstants.USERNAME_PATTERN,
            message = ValidationConstants.USERNAME_MESSAGE)
    private String username;

    @NotEmpty
    @Size(min = ValidationConstants.DEFAULT_MIN_SIZE, max = ValidationConstants.DEFAULT_MAX_SIZE)
    @Pattern(regexp = ValidationConstants.DEFAULT_PATTERN,
            message = ValidationConstants.DEFAULT_MESSAGE)
    private String secretAnswer;

    @NotEmpty
    @Size(min = ValidationConstants.PASSWORD_MIN_SIZE, max = ValidationConstants.PASSWORD_MAX_SIZE)
    @Pattern(regexp = ValidationConstants.PASSWORD_PATTERN,
            message = ValidationConstants.PASSWORD_MESSAGE)
    private String newPassword;
}
