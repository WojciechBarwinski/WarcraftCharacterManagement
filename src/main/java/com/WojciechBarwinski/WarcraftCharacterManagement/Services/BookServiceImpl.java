package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.BookDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.DTOFlag;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Author;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Book;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionChecker;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionMessage;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ResourceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.BookMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.AuthorRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.BookRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.BookMapper.mapBookToDTO;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.BookMapper.mapDTOToBook;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final HeroRepository heroRepository;
    private final ExceptionChecker exceptCheck;
    private final ExceptionMessage em;


    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           HeroRepository heroRepository,
                           ExceptionMessage exceptionMessage,
                           ExceptionChecker exceptionChecker) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.heroRepository = heroRepository;
        this.exceptCheck = exceptionChecker;
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
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noBookTitle") + title)));
    }

    @Override
    public Set<BookDTO> getAllBooksBySeries(String seriesTitle) {
        return bookRepository.findBySeriesIgnoreCase(seriesTitle)
                .stream()
                .map(BookMapper::mapBookToDTOToPreview)
                .collect(Collectors.toSet());
    }

    @Override
    public BookDTO createBook(BookDTO dto) {
        exceptCheck.ifDescriptionDoesNotExist(dto.getDescription());
        exceptCheck.ifBookTitleIsNull(dto.getTitle());
        exceptCheck.ifAuthorNamesAreNull(dto.getAuthor());

        Book book = mapDTOToBook(dto);
        book.setAuthor(authorRepository.findByLastNameIgnoreCase(dto.getAuthor())
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noAuthorName" + dto.getAuthor()))));
        return mapBookToDTO(bookRepository.save(book));
    }

    @Override
    public DTOFlag updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.save(buildUpdateBook(id, bookDTO));
        return mapBookToDTO(book);
    }

    @Transactional
    @Override
    public void deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException(em.getMessage("noBookId") + id);
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteBookByTitle(String title){
        if (!bookRepository.existsByTitleIgnoreCase(title)) {
            throw new ResourceNotFoundException(em.getMessage("noBookTitle") + title);
        }
        bookRepository.deleteByTitleIgnoreCase(title);
    }

    private Book buildUpdateBook(Long id, BookDTO dto){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noBookId")));

        if (!StringUtils.isBlank(dto.getTitle())){
            book.setTitle(dto.getTitle());
        }

        if (!StringUtils.isBlank(dto.getAuthor())){
            Author author = authorRepository.findByLastNameIgnoreCase(dto.getAuthor())
                    .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noAuthorName" + dto.getAuthor())));
            book.setAuthor(author);
        }

        if (!StringUtils.isBlank(dto.getSeries())){
            book.setSeries(dto.getSeries());
        }

        if (!StringUtils.isBlank(dto.getDescription())){
            book.setDescription(dto.getDescription());
        }

        if (!dto.getHeroes().isEmpty()){
            book.setHeroes(updateHeroesSet(book.getHeroes(), dto.getHeroes()));
        }

        return book;
    }

    private Set<Hero> updateHeroesSet(Set<Hero> originalHeroes, Set<String> heroesToCheck) {
        for (Hero hero : checkHeroes(heroesToCheck)) {
            if (originalHeroes.contains(hero)){
                originalHeroes.remove(hero);
            } else {
                originalHeroes.add(hero);
            }
        }
        return originalHeroes;
    }

    private Set<Hero> checkHeroes(Set<String> heroesToCheck){
        Set<Hero> heroes = heroRepository.findByFirstNameIgnoreCaseIn(heroesToCheck);

        if (heroes.size() != heroesToCheck.size()){
            throwExceptionWithIncorrectHeroesNames(heroesToCheck, heroes);
        }
        return heroes;
    }
    private void throwExceptionWithIncorrectHeroesNames(Set<String> names, Set<Hero> heroes){
        Set<String> collect = heroes.stream()
                .map(Hero::getFirstName)
                .collect(Collectors.toSet());
        names.removeAll(collect);
        throw new ResourceNotFoundException(em.getMessage("noHeroName") + stringWithIncorrectNames(names));

    }

    private String stringWithIncorrectNames(Set<String> names) {
        StringBuilder message = new StringBuilder();
        for (String s : names) {
            message.append(s).append(" ");
        }
        return message.toString();
    }


}
