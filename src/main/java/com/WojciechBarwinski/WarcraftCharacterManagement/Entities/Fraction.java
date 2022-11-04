package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;


import lombok.*;

import javax.persistence.*;
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

    @ManyToMany(mappedBy = "fractions", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Hero> heroes = new java.util.LinkedHashSet<>();

    public Fraction(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
