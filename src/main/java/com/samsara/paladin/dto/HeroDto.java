package com.samsara.paladin.dto;

import java.util.Date;

import com.samsara.paladin.configuration.validation.hero.level.HeroLevel;
import com.samsara.paladin.configuration.validation.hero.name.HeroName;
import com.samsara.paladin.configuration.validation.hero.type.HeroType;

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

    private Long id;

    @HeroName
    private String name;

    @HeroType
    private String type;

    @HeroLevel
    private Integer level;

    private String username;

    private Date creationDate;
}
