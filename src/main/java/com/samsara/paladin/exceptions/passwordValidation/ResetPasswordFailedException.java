package com.samsara.paladin.exceptions.passwordValidation;

import jakarta.validation.constraints.NotEmpty;

public class ResetPasswordFailedException extends RuntimeException {
    public ResetPasswordFailedException(@NotEmpty String errorMessage) {
        super(errorMessage);
    }
}
