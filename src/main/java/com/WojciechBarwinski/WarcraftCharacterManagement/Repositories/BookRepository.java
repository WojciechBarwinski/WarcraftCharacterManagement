package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {


    Set<Book> findByTitleIgnoreCaseIn(Set<String> title);

    @Query("SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.author " +
            "LEFT JOIN FETCH b.heroes " +
            "WHERE UPPER(b.title) = UPPER(?1)")
    Optional<Book> findByTitleIgnoreCase(String title);

    @Query("SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.author " +
            "LEFT JOIN FETCH b.heroes " +
            "WHERE b.id = ?1")
    Optional<Book> findById(Long id);

    Set<Book> findBySeriesIgnoreCase(String seriesTitle);

    boolean existsByTitleIgnoreCase(String title);

    void deleteByTitleIgnoreCase(String title);
}
