package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByLastNameIgnoreCase(String firstName);

    void deleteByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
