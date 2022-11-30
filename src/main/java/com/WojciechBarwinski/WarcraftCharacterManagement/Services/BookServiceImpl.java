package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.BookDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.BookMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Set<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::mapBookToDTOToPreview)
                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
