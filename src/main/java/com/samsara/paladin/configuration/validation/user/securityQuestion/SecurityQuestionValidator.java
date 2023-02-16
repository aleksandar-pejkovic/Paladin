package com.samsara.paladin.configuration.validation.user.securityQuestion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SecurityQuestionValidator implements ConstraintValidator<SecurityQuestion, String> {

    @Override
    public void initialize(SecurityQuestion securityQuestion) {
    }

    @Override
    public boolean isValid(String securityQuestion, ConstraintValidatorContext context) {
        String regex = "^[a-zA-Z0-9ČĆŠĐŽčćšđž,.!? \\u0027-]+${1,60}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(securityQuestion);
        return matcher.find();
    }
}
