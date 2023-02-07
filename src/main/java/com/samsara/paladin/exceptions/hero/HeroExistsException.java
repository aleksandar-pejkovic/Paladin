package com.samsara.paladin.exceptions.hero;

import jakarta.validation.constraints.NotEmpty;

public class HeroExistsException extends RuntimeException {
    public HeroExistsException(@NotEmpty String errorMessage) {
        super(errorMessage);
    }
}
