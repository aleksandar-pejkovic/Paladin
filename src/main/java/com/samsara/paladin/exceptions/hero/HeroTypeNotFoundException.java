package com.samsara.paladin.exceptions.hero;

import jakarta.validation.constraints.NotEmpty;

public class HeroTypeNotFoundException extends RuntimeException {
    public HeroTypeNotFoundException(@NotEmpty String errorMessage) {
        super(errorMessage);
    }
}
