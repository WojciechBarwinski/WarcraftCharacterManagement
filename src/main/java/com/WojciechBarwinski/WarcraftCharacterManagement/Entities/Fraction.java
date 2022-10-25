package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fraction {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "name")
    private String fractionName;

    @Lob
    @Column(name = "description")
    private String fractionDescription;

    @ManyToMany
    @JoinTable(
            name = "hero_fraction",
            joinColumns = @JoinColumn(name = "fraction_id"),
            inverseJoinColumns = @JoinColumn(name = "hero_id"))
    private Set<Hero> heroes = new HashSet<>();



    public Fraction(String fractionName, String fractionDescription) {
        this.fractionName = fractionName;
        this.fractionDescription = fractionDescription;
    }

    public void addHeroToFraction(Hero hero){
        heroes.add(hero);
    }
}
