package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
