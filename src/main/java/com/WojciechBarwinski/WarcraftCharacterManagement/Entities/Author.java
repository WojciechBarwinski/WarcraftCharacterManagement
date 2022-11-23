package com.WojciechBarwinski.WarcraftCharacterManagement.Entities;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    private String nationality;

    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    public Author(String firstName, String lastName, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
    }

    public void setBook(Book book){
        if (books == null){
            books = new HashSet<>();
        }
        books.add(book);
        book.setAuthor(this);
    }

    @PreRemove
    private void removeAssociationsWithChildren(){
        books.forEach(x -> x.setAuthor(null));
    }
}
