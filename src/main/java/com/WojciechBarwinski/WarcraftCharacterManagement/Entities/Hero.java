package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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

        @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
        @JoinTable(name = "hero_fraction",
                joinColumns = @JoinColumn(name = "hero_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "fraction_id", referencedColumnName = "id"))
        private Set<Fraction> fractions;

        @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
        @JoinTable(name = "hero_book",
            joinColumns = @JoinColumn(name = "hero_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
        private Set<Book> books;

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

        public void addBook(Book book){
                if (books == null){
                        books = new HashSet<>();
                }
                books.add(book);
        }

        public void addFraction(Fraction fraction){
                if (fractions == null){
                        fractions = new HashSet<>();
                }
                fractions.add(fraction);
        }
}
