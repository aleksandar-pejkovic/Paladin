package com.samsara.paladin.exceptions.hero;

import jakarta.validation.constraints.NotEmpty;

public class HeroNotFoundException extends RuntimeException {
    public HeroNotFoundException(@NotEmpty String errorMessage) {
        super(errorMessage);
    }
}
