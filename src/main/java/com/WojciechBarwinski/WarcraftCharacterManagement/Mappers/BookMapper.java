package com.WojciechBarwinski.WarcraftCharacterManagement.Mappers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.BookDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Book;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;

import java.util.Set;
import java.util.stream.Collectors;

public class BookMapper {

    private BookMapper() {
    }

    public static Book mapDTOToBook(BookDTO dto){
        return Book.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .series(dto.getSeries())
                .build();
    }

    public static BookDTO mapBookToDTO(Book book){
        BookDTO bookDTO=  mapBookWithoutHeroes(book);
        if (book.getHeroes() != null){
            bookDTO.setHeroes(mapHeroToString(book.getHeroes()));
        }
        return bookDTO;
    }

    public static BookDTO mapBookToDTOToPreview(Book book){
        return mapBookWithoutHeroes(book);
    }

    private static BookDTO mapBookWithoutHeroes(Book book){
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor().getLastName())
                .series(book.getSeries())
                .build();
    }

    private static Set<String> mapHeroToString(Set<Hero> heroes){
        return heroes.stream()
                .map(x -> x.getFirstName() + " " + x.getLastName())
                .collect(Collectors.toSet());
    }
}
