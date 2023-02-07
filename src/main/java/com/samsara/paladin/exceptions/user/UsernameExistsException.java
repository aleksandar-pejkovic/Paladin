package com.samsara.paladin.exceptions.user;

import jakarta.validation.constraints.NotEmpty;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(@NotEmpty String errorMessage) {
        super(errorMessage);
    }
}
