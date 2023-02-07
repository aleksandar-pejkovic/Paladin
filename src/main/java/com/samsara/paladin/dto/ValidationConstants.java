package com.samsara.paladin.dto;

public class ValidationConstants {

    protected ValidationConstants() {
    }

    protected static final int USERNAME_MIN_SIZE = 3;

    static final int USERNAME_MAX_SIZE = 35;

    static final int PASSWORD_MIN_SIZE = 6;

    static final int PASSWORD_MAX_SIZE = 12;

    static final int DEFAULT_MIN_SIZE = 1;

    static final int DEFAULT_MAX_SIZE = 60;

    static final String USERNAME_PATTERN = "^[a-z][a-z0-9]*$";

    static final String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}";

    static final String DEFAULT_PATTERN = "^[a-zA-Z0-9ČĆŠĐŽčćšđž,.!? \\u0027-]+$";

    static final String USERNAME_MESSAGE = "Username must contain only lower case letters and numbers, "
            + "must start with a letter, and must be 3-35 characters long!";

    static final String PASSWORD_MESSAGE = "Password must contain at least one number, "
            + "one lowercase and one uppercase letter, and must be 6-12 characters long!";

    static final String DEFAULT_MESSAGE = "Incorrect format!";
}
