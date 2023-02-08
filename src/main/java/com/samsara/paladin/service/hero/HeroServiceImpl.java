package com.samsara.paladin.service.hero;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsara.paladin.dto.HeroDto;
import com.samsara.paladin.enums.HeroType;
import com.samsara.paladin.exceptions.hero.HeroExistsException;
import com.samsara.paladin.exceptions.hero.HeroNotFoundException;
import com.samsara.paladin.exceptions.user.UsernameNotFoundException;
import com.samsara.paladin.model.Hero;
import com.samsara.paladin.model.User;
import com.samsara.paladin.repository.HeroRepository;
import com.samsara.paladin.repository.UserRepository;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;

    private final ModelMapper modelMapper;

    private UserRepository userRepository;

    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository, ModelMapper modelMapper) {
        this.heroRepository = heroRepository;
        this.modelMapper = modelMapper;
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
        Hero hero = convertToEntity(heroDto, optionalUser.get());
        return convertToDto(heroRepository.save(hero));
    }

    @Override
    public HeroDto updateHero(HeroDto heroDto) {
        Optional<Hero> optionalHero = heroRepository.findByName(heroDto.getName());
        if (optionalHero.isEmpty()) {
            throw new HeroNotFoundException("Hero '" + heroDto.getName() + "' not found!");
        }
        Hero hero = optionalHero.get();
        modelMapper.map(heroDto, hero);
        return convertToDto(heroRepository.save(hero));
    }

    @Override
    public void deleteHero(String name) {
        Optional<Hero> optionalHero = heroRepository.findByName(name);
        if (optionalHero.isEmpty()) {
            throw new HeroNotFoundException("Hero '" + name + "' not found!");
        }
        heroRepository.deleteById(optionalHero.get().getId());
    }

    @Override
    public List<HeroDto> loadAllHeroes() {
        return convertToDtoList(heroRepository.findAll());
    }

    @Override
    public HeroDto loadHeroByName(String name) {
        return heroRepository.findByName(name)
                .map(this::convertToDto)
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
        return convertToDtoList(heroRepository.findByUser(optionalUser.get()));
    }

    @Override
    public List<HeroDto> loadHeroesByType(HeroType type) {
        return convertToDtoList(heroRepository.findByType(type));
    }

    @Override
    public List<HeroDto> loadHeroesByLevel(Integer level) {
        return convertToDtoList(heroRepository.findByLevel(level));
    }

    @Override
    public List<HeroDto> loadHeroesByMinLevel(Integer level) {
        return convertToDtoList(heroRepository.findByLevelGreaterThan(level));
    }

    @Override
    public List<HeroDto> loadHeroesByMaxLevel(Integer level) {
        return convertToDtoList(heroRepository.findByLevelLessThan(level));
    }

    @Override
    public List<HeroDto> loadBest10HeroesByLevel() {
        return convertToDtoList(heroRepository.findFirst10ByOrderByLevelDesc());
    }

    @Override
    public List<HeroDto> loadLast10AddedHeroes() {
        return convertToDtoList(heroRepository.findByOrderByCreationDateDesc());
    }

    private List<HeroDto> convertToDtoList(List<Hero> heroes) {
        return heroes
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private HeroDto convertToDto(Hero hero) {
        HeroDto heroDto = modelMapper.map(hero, HeroDto.class);
        heroDto.setUsername(hero.getUser().getUsername());
        return heroDto;
    }

    private Hero convertToEntity(HeroDto heroDto, User user) {
        Hero hero = modelMapper.map(heroDto, Hero.class);
        hero.setUser(user);
        return hero;
    }
}
