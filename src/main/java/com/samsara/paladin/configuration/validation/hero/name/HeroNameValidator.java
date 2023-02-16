package com.samsara.paladin.configuration.validation.hero.name;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HeroNameValidator implements ConstraintValidator<HeroNameConstraint, String> {

    @Override
    public void initialize(HeroNameConstraint heroNameConstraint) {
    }

    @Override
    public boolean isValid(String heroName, ConstraintValidatorContext context) {
        if (heroName == null) {
            return false;
        }
        String regex = "^([a-zA-Z0-9čćšđžČĆŠĐŽ]+ ?)*[a-zA-Z0-9čćšđžČĆŠĐŽ]{3,25}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(heroName);
        return matcher.find();
    }
}
