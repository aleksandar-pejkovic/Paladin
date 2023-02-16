package com.samsara.paladin.configuration.validation.user.name;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<NameConstraint, String> {

    @Override
    public void initialize(NameConstraint nameConstraint) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return false;
        }
        String regex = "^[A-ZČĆŠĐŽ][a-zA-ZčćšđžČĆŠĐŽ]*[ \\u0027-]?"
                + "[a-zA-ZčćšđžČĆŠĐŽ]*[a-zčćšđž0-9]{2,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
}
