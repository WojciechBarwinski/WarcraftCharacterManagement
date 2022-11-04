package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String book);

    @Query("SELECT b FROM Book b WHERE b.title IN ?1")
    Set<Book> findByTitles(Set<String> titles);
}
