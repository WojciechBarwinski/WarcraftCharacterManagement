package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.BookDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionMessage;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ResourceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.BookMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.BookMapper.mapBookToDTO;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ExceptionMessage em;


    public BookServiceImpl(BookRepository bookRepository,
                           ExceptionMessage exceptionMessage) {
        this.bookRepository = bookRepository;
        this.em = exceptionMessage;
    }

    @Override
    public Set<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::mapBookToDTOToPreview)
                .collect(Collectors.toSet());
    }

    @Override
    public BookDTO getBookById(Long id) {
        return mapBookToDTO(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noBookId") + id)));
    }

    @Override
    public BookDTO getBookByTitle(String title) {
        return mapBookToDTO(bookRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noBookName") + title)));
    }

    @Override
    public Set<BookDTO> getAllBooksBySeries(String seriesTitle) {
        return bookRepository.findBySeriesIgnoreCase(seriesTitle)
                .stream()
                .map(BookMapper::mapBookToDTOToPreview)
                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException(em.getMessage("noBookId") + id);
        }
        bookRepository.deleteById(id);
    }


}
