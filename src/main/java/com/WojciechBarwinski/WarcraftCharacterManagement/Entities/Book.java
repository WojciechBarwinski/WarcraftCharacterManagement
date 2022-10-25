package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne
    private Author author;

    public Book(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
