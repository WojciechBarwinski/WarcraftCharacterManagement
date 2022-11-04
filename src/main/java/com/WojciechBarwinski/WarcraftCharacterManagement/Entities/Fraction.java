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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "hero_fraction",
            joinColumns = @JoinColumn(name = "fraction_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "hero_id", referencedColumnName = "id"))
    private Set<Hero> heroes = new java.util.LinkedHashSet<>();



    public Fraction(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addHeroToFraction(Hero hero){
        heroes.add(hero);
    }
}
