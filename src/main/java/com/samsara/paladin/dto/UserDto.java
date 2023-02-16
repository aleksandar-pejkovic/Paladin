package com.samsara.paladin.dto;

import java.util.Date;

import com.samsara.paladin.configuration.validation.user.about.AboutConstraint;
import com.samsara.paladin.configuration.validation.user.name.NameConstraint;
import com.samsara.paladin.configuration.validation.user.password.PasswordConstraint;
import com.samsara.paladin.configuration.validation.user.securityQuestion.SecurityQuestionConstraint;
import com.samsara.paladin.configuration.validation.user.username.UsernameConstraint;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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

    private Long id;

    @NameConstraint
    private String firstName;

    @NameConstraint
    private String lastName;

    @UsernameConstraint
    private String username;

    @NotNull
    @Email
    private String email;

    @PasswordConstraint
    private String password;

    @AboutConstraint
    private String about;

    private Integer heroCount;

    private Date creationDate;

    private Boolean enabled;

    @SecurityQuestionConstraint
    private String secretQuestion;

    @SecurityQuestionConstraint
    private String secretAnswer;
}
