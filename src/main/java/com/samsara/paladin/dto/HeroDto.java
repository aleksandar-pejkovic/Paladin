package com.samsara.paladin.dto;

import java.util.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HeroDto {

    private static final int NAME_MIN_SIZE = 3;

    private static final int NAME_MAX_SIZE = 25;

    private static final String NAME_PATTERN = "^([a-zA-Z0-9čćšđžČĆŠĐŽ]+ ?)*[a-zA-Z0-9čćšđžČĆŠĐŽ]$";

    private static final String NAME_MESSAGE = "Hero name may have more than one word "
            + "with exactly one space between them. Words must contain only letters and numbers!";

    private static final int DEFAULT_MAX_SIZE = 60;

    private static final int MIN_LEVEL = 0;

    private static final int MAX_LEVEL = 80;

    private Long id;

    @NotEmpty
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE)
    @Pattern(regexp = NAME_PATTERN,
            message = NAME_MESSAGE)
    private String name;

    @Size(max = DEFAULT_MAX_SIZE)
    private String type;

    @Min(MIN_LEVEL)
    @Max(MAX_LEVEL)
    private Integer level;

    private String username;

    private Date creationDate;
}
