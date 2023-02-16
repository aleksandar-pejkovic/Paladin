package com.samsara.paladin.dto;

import com.samsara.paladin.configuration.validation.user.password.Password;
import com.samsara.paladin.configuration.validation.user.securityQuestion.SecurityQuestion;
import com.samsara.paladin.configuration.validation.user.username.Username;

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

    @Username
    private String username;

    @SecurityQuestion
    private String secretAnswer;

    @Password
    private String newPassword;
}
