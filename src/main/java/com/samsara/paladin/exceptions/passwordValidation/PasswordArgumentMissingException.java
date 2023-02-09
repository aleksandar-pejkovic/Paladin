package com.samsara.paladin.exceptions.passwordValidation;

import jakarta.validation.constraints.NotEmpty;

public class PasswordArgumentMissingException extends RuntimeException {
    public PasswordArgumentMissingException(@NotEmpty String errorMessage) {
        super(errorMessage);
    }
}
