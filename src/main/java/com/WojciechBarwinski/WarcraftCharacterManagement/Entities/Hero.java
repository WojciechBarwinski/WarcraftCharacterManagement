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
@Table(name = "heroes")
public class Hero {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        private Long id;
        private String firstName;
        private String lastName;

        @ManyToOne
        private Race race;

        @ManyToMany(mappedBy = "heroes")
        private Set<Fraction> fractions = new HashSet<>();

        public Hero(String firstName, String lastName) {
                this.firstName = firstName;
                this.lastName = lastName;
        }


}
