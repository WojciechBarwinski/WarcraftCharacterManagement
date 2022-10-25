package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fractions")
public class Fraction {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    @ManyToMany
    @JoinTable(
            name = "hero_fraction",
            joinColumns = @JoinColumn(name = "fraction_id"),
            inverseJoinColumns = @JoinColumn(name = "hero_id"))
    private Set<Hero> heroes = new HashSet<>();



    public Fraction(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addHeroToFraction(Hero hero){
        heroes.add(hero);
    }
}
