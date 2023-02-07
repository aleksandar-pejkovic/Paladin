package com.samsara.paladin.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum HeroType {

    WARRIOR("Warrior"),
    GUARDIAN("Guardian"),
    REVENANT("Revenant"),
    THIEF("Thief"),
    RANGER("Ranger"),
    ENGINEER("Engineer"),
    ELEMENTALIST("Elementalist"),
    NECROMANCER("Necromancer"),
    MESMER("Mesmer");

    private final String type;

    public static final Map<String, HeroType> BY_TYPE = new HashMap<>();

    static {
        for (HeroType heroType : values()) {
            BY_TYPE.put(heroType.type, heroType);
        }
    }

    HeroType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static List<HeroType> getAll() {
        return Arrays.asList(HeroType.values());
    }

    public static List<String> getAllValues() {
        return Arrays.stream(HeroType.values())
                .map(HeroType::getType)
                .collect(Collectors.toList());
    }

    public static HeroType valueOfType(String type) {
        return BY_TYPE.get(type);
    }
}
