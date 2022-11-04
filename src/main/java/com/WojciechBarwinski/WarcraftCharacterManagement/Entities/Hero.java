package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "heroes")
public class Hero {

        @Id
        //@Setter(AccessLevel.NONE)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String firstName;
        private String lastName;

        @ElementCollection
        @CollectionTable(name = "hero_title")
        @Column(name = "titles")
        private Set<String> titles;

        @ManyToOne
        private Race race;

        @ManyToMany(mappedBy = "heroes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        private Set<Fraction> fractions = new java.util.LinkedHashSet<>();

        @ManyToMany(mappedBy = "heroes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        private Set<Book> books = new java.util.LinkedHashSet<>();

        public Hero(String firstName, String lastName) {
                this.firstName = firstName;
                this.lastName = lastName;
        }

        public void addTitle(String newTitle) {
                if (titles == null){
                        titles = new HashSet<>();
                }
                titles.add(newTitle);
        }
}
