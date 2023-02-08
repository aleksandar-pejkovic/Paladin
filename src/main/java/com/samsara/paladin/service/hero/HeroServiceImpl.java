package com.samsara.paladin.service.hero;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.samsara.paladin.dto.HeroDto;
import com.samsara.paladin.enums.EventAction;
import com.samsara.paladin.enums.EventCategory;
import com.samsara.paladin.enums.HeroType;
import com.samsara.paladin.events.CustomEventPublisher;
import com.samsara.paladin.exceptions.hero.HeroExistsException;
import com.samsara.paladin.exceptions.hero.HeroNotFoundException;
import com.samsara.paladin.exceptions.hero.HeroTypeNotFoundException;
import com.samsara.paladin.exceptions.user.UsernameNotFoundException;
import com.samsara.paladin.model.Hero;
import com.samsara.paladin.model.User;
import com.samsara.paladin.repository.HeroRepository;
import com.samsara.paladin.repository.UserRepository;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;

    private final ModelMapper modelMapper;

    private CustomEventPublisher eventPublisher;

    private UserRepository userRepository;

    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository, ModelMapper modelMapper) {
        this.heroRepository = heroRepository;
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setEventPublisher(CustomEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public HeroDto createHero(HeroDto heroDto) {
        if (heroRepository.existsByName(heroDto.getName())) {
            throw new HeroExistsException("Hero named '" + heroDto.getName() + "' already exist!");
        }
        Optional<User> optionalUser = userRepository.findByUsername(heroDto.getUsername());
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username '%s' not found!", heroDto.getUsername()));
        }
        heroDto.setCreationDate(new Date());
        Hero hero = convertHeroToEntity(heroDto, optionalUser.get());
        Hero createdHero = heroRepository.save(hero);
        publishHeroEvent(createdHero.getName(), EventAction.ADD);
        return convertHeroToDto(createdHero);
    }

    @Override
    public HeroDto updateHero(HeroDto heroDto) {
        Optional<Hero> optionalHero = heroRepository.findByName(heroDto.getName());
        if (optionalHero.isEmpty()) {
            throw new HeroNotFoundException("Hero '" + heroDto.getName() + "' not found!");
        }
        Hero hero = optionalHero.get();
        modelMapper.map(heroDto, hero);
        Hero updatedHero = heroRepository.save(hero);
        publishHeroEvent(updatedHero.getName(), EventAction.EDIT);
        return convertHeroToDto(updatedHero);
    }

    @Override
    public void deleteHero(String name) {
        Optional<Hero> optionalHero = heroRepository.findByName(name);
        if (optionalHero.isEmpty()) {
            throw new HeroNotFoundException("Hero '" + name + "' not found!");
        }
        heroRepository.deleteById(optionalHero.get().getId());
        publishHeroEvent(optionalHero.get().getName(), EventAction.DELETE);
    }

    @Override
    public List<HeroDto> loadAllHeroes() {
        return convertHeroesToDtoList(heroRepository.findAll());
    }

    @Override
    public HeroDto loadHeroByName(String name) {
        return heroRepository.findByName(name)
                .map(this::convertHeroToDto)
                .orElseThrow(
                        () -> new HeroNotFoundException("Hero '" + name + "' not found!")
                );
    }

    @Override
    public List<HeroDto> loadHeroesByUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Username '" + username + "' not found!");
        }
        return convertHeroesToDtoList(heroRepository.findByUser(optionalUser.get()));
    }

    @Override
    public List<HeroDto> loadHeroesByType(String heroTypeName) {
        return HeroType.valueOfType(heroTypeName)
                .map(heroRepository::findByType)
                .map(this::convertHeroesToDtoList)
                .orElseThrow(
                        () -> new HeroTypeNotFoundException("Hero type '" + heroTypeName + "' not found!")
                );
    }

    @Override
    public List<HeroDto> loadHeroesByLevel(Integer level) {
        return convertHeroesToDtoList(heroRepository.findByLevel(level));
    }

    @Override
    public List<HeroDto> loadHeroesByMinLevel(Integer level) {
        return convertHeroesToDtoList(heroRepository.findByLevelGreaterThan(level));
    }

    @Override
    public List<HeroDto> loadHeroesByMaxLevel(Integer level) {
        return convertHeroesToDtoList(heroRepository.findByLevelLessThan(level));
    }

    @Override
    public List<HeroDto> loadBest10HeroesByLevel() {
        return convertHeroesToDtoList(heroRepository.findFirst10ByOrderByLevelDesc());
    }

    @Override
    public List<HeroDto> loadLast10AddedHeroes() {
        return convertHeroesToDtoList(heroRepository.findByOrderByCreationDateDesc());
    }

    private void publishHeroEvent(String name, EventAction action) {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        eventPublisher.publishEvent(
                EventCategory.HERO,
                name,
                action,
                loggedInUsername
        );
    }

    private List<HeroDto> convertHeroesToDtoList(List<Hero> heroes) {
        return heroes
                .stream()
                .map(this::convertHeroToDto)
                .collect(Collectors.toList());
    }

    private HeroDto convertHeroToDto(Hero hero) {
        HeroDto heroDto = modelMapper.map(hero, HeroDto.class);
        heroDto.setUsername(hero.getUser().getUsername());
        return heroDto;
    }

    private Hero convertHeroToEntity(HeroDto heroDto, User user) {
        Hero hero = modelMapper.map(heroDto, Hero.class);
        hero.setUser(user);
        Optional<HeroType> optionalHeroType = HeroType.valueOfType(heroDto.getType());
        if (optionalHeroType.isEmpty()) {
            throw new HeroTypeNotFoundException("Invalid hero type!");
        }
        hero.setType(optionalHeroType.get());
        return hero;
    }
}
