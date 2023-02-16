package com.samsara.paladin.configuration.validation.hero.type;

import com.samsara.paladin.enums.HeroType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HeroTypeValidator implements ConstraintValidator<HeroTypeConstraint, String> {

    @Override
    public void initialize(HeroTypeConstraint heroTypeConstraint) {
    }

    @Override
    public boolean isValid(String heroType, ConstraintValidatorContext context) {
        return heroType != null && HeroType.valueOfType(heroType).isPresent();
    }
}
