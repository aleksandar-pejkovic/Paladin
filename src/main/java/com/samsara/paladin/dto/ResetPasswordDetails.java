package com.samsara.paladin.dto;

import com.samsara.paladin.configuration.validation.user.password.PasswordConstraint;
import com.samsara.paladin.configuration.validation.user.securityQuestion.SecurityQuestionConstraint;
import com.samsara.paladin.configuration.validation.user.username.UsernameConstraint;

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

    @UsernameConstraint
    private String username;

    @SecurityQuestionConstraint
    private String secretAnswer;

    @PasswordConstraint
    private String newPassword;
}
