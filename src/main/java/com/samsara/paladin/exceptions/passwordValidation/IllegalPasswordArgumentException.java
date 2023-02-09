package com.samsara.paladin.exceptions.passwordValidation;

import jakarta.validation.constraints.NotEmpty;

public class IllegalPasswordArgumentException extends RuntimeException {
    public IllegalPasswordArgumentException(@NotEmpty String errorMessage) {
        super(errorMessage);
    }
}
