package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    private String series;

    @Lob
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @ManyToMany
    @JoinTable(
            name = "hero_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "hero_id"))
    private Set<Hero> heroes = new HashSet<>();

    public Book(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void addHeroToBook(Hero hero){
        heroes.add(hero);
    }
}
