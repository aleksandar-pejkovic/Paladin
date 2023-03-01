package com.samsara.paladin.service.hero;

import java.util.List;

import com.samsara.paladin.dto.HeroDto;

public interface HeroService {

    List<HeroDto> searchHeroes(String searchTerm);

    HeroDto createHero(HeroDto heroDto);

    HeroDto updateHero(HeroDto heroDto);

    void deleteHero(String name);

    List<HeroDto> loadAllHeroes();

    HeroDto loadHeroByName(String name);

    List<HeroDto> loadHeroesByUser(String username);

    List<HeroDto> loadHeroesByType(String type);

    List<HeroDto> loadHeroesByLevel(Integer level);

    List<HeroDto> loadHeroesByMinLevel(Integer level);

    List<HeroDto> loadHeroesByMaxLevel(Integer level);

    List<HeroDto> loadBest10HeroesByLevel();

    List<HeroDto> loadLast10AddedHeroes();
}
