package com.samsara.paladin.configuration.validation.hero.level;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HeroLevelValidator implements ConstraintValidator<HeroLevelConstraint, Integer> {

    public static final int MIN_LEVEL = 0;
    public static final int MAX_LEVEL = 80;

    @Override
    public void initialize(HeroLevelConstraint heroLevelConstraint) {
    }

    @Override
    public boolean isValid(Integer heroLevel, ConstraintValidatorContext context) {
        return heroLevel != null && heroLevel >= MIN_LEVEL && heroLevel <= MAX_LEVEL;
    }
}
