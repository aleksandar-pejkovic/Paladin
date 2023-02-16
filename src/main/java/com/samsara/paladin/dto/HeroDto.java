package com.samsara.paladin.dto;

import java.util.Date;

import com.samsara.paladin.configuration.validation.hero.level.HeroLevelConstraint;
import com.samsara.paladin.configuration.validation.hero.name.HeroNameConstraint;
import com.samsara.paladin.configuration.validation.hero.type.HeroTypeConstraint;

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

    @HeroNameConstraint
    private String name;

    @HeroTypeConstraint
    private String type;

    @HeroLevelConstraint
    private Integer level;

    private String username;

    private Date creationDate;
}
