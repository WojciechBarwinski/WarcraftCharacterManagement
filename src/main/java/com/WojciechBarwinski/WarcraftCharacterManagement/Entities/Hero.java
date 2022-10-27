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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "heroes")
public class Hero {

        @Id
        @Setter(AccessLevel.NONE)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String firstName;
        private String lastName;

        @ElementCollection
        @CollectionTable(name = "hero_title")
        @Column(name = "title")
        private List<String> titles = new ArrayList<>();

        @ManyToOne
        private Race race;

        @ManyToMany(mappedBy = "heroes")
        private Set<Fraction> fractions = new HashSet<>();

        @ManyToMany(mappedBy = "heroes")
        private Set<Book> books = new HashSet<>();

        public Hero(String firstName, String lastName) {
                this.firstName = firstName;
                this.lastName = lastName;
        }

        public void addTitle(String newTitle) {
                if (titles == null){
                        titles = new ArrayList<>();
                }
                titles.add(newTitle);
        }
}
