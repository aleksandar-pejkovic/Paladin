package com.samsara.paladin.configuration.validation.user.about;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = AboutValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AboutConstraint {

    String message() default "Incorrect format!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
