package com.samsara.paladin.exceptions.user;

import jakarta.validation.constraints.NotEmpty;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(@NotEmpty String errorMessage) {
        super(errorMessage);
    }
}
