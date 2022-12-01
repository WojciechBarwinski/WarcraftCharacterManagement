package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {


    Set<Book> findByTitleIgnoreCaseIn(Set<String> title);


}
