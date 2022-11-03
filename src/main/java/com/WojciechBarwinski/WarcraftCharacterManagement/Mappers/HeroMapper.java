package com.WojciechBarwinski.WarcraftCharacterManagement.Mappers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Book;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Fraction;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;

import java.util.HashSet;
import java.util.Set;


public class HeroMapper {

    private HeroMapper() {
    }

    public static HeroDTO mapHeroToDTO(Hero hero){
        return HeroDTO.builder()
                .id(hero.getId())
                .firstName(hero.getFirstName())
                .lastName(hero.getLastName())
                .titles(hero.getTitles())
                .race(hero.getRace().getName())
                .fractions(mapFactions(hero.getFractions()))
                .books(mapBooks(hero.getBooks()))
                .build();
    }

    public static Hero mapDTOToHero(HeroDTO DTO, Long id){

        return Hero.builder()
                .id(id)
                .firstName(DTO.getFirstName())
                .lastName(DTO.getLastName())
                .titles(DTO.getTitles())
                .build();
    }

    public static Hero mapDTOToNewHero(HeroDTO DTO){

        return Hero.builder()
                .firstName(DTO.getFirstName())
                .lastName(DTO.getLastName())
                .titles(DTO.getTitles())
                .build();
    }
    private static Set<String> mapFactions(Set<Fraction> fractions){
        Set<String> mappedFractions = new HashSet<>();
        for (Fraction fraction : fractions) {
            mappedFractions.add(fraction.getName());
        }
        return mappedFractions;
    }
    private static Set<String> mapBooks(Set<Book> books){
        Set<String> mappedBooks = new HashSet<>();
        for (Book book : books) {
            mappedBooks.add(book.getTitle());
        }
        return mappedBooks;
    }

}
