package com.samsara.paladin.configuration.validation.hero.type;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HeroTypeValidator implements ConstraintValidator<HeroType, String> {

    @Override
    public void initialize(HeroType heroType) {
    }

    @Override
    public boolean isValid(String heroType, ConstraintValidatorContext context) {
        return com.samsara.paladin.enums.HeroType.valueOfType(heroType).isPresent();
    }
}
