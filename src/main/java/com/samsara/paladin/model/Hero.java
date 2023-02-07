package com.samsara.paladin.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.samsara.paladin.enums.HeroType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "heroes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private HeroType type;

    private Integer level;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
