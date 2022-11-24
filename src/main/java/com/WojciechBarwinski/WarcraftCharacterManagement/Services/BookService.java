package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.BookDTO;

import java.util.Set;

public interface BookService {

    Set<BookDTO> getAllBooks();
    void deleteBookById(Long id);
}
