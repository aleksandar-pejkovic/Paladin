package com.samsara.paladin.model;

import java.util.Date;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.format.annotation.DateTimeFormat;

import com.samsara.paladin.enums.RoleName;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private String about;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date creationDate;

    private String secretQuestion;

    private String secretAnswer;

    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Hero> heroes;

    @OneToOne(mappedBy = "user")
    private Avatar avatar;

    public boolean isAdmin() {
        return roles.stream()
                .map(Role::getName)
                .anyMatch(Predicate.isEqual(RoleName.ADMIN));
    }
}
