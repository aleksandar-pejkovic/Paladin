package com.samsara.paladin.configuration.validation.hero.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = HeroTypeValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HeroType {

    String message() default "Hero type is not recognized as valid class! "
            + "Name length must shorter than 60!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
