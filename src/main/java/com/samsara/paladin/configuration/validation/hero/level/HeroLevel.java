package com.samsara.paladin.configuration.validation.hero.level;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = HeroLevelValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HeroLevel {

    String message() default "Value must be between 0 and 80!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
