package com.samsara.paladin.configuration.validation.hero.name;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = HeroNameValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HeroName {

    String message() default "Hero name may have more than one word "
            + "with exactly one space between them. "
            + "Words must contain only letters and numbers! "
            + "Name length must be between 3 and 25!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
