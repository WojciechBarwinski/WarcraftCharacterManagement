package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.BookDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.DTOFlag;

import java.util.Set;

public interface BookService {

    Set<BookDTO> getAllBooks();
    void deleteBookById(Long id);

    BookDTO getBookById(Long id);

    BookDTO getBookByTitle(String title);

    Set<BookDTO> getAllBooksBySeries(String seriesTitle);

    void deleteBookByTitle(String title);

    BookDTO createBook(BookDTO bookDTO);

    DTOFlag updateBook(Long id, BookDTO bookDTO);
}
