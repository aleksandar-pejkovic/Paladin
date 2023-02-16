package com.samsara.paladin.configuration.validation.hero.name;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HeroNameValidator implements ConstraintValidator<HeroName, String> {

    @Override
    public void initialize(HeroName heroName) {
    }

    @Override
    public boolean isValid(String heroName, ConstraintValidatorContext context) {
        String regex = "^([a-zA-Z0-9čćšđžČĆŠĐŽ]+ ?)*[a-zA-Z0-9čćšđžČĆŠĐŽ]{3,25}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(heroName);
        return matcher.find();
    }
}
