package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
