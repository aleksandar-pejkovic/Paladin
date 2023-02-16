package com.samsara.paladin.configuration.validation.user.securityQuestion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = SecurityQuestionValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityQuestion {

    String message() default "Incorrect format!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
