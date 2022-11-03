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

    public static Hero mapDTOToHero(HeroDTO DTO){
        Hero newHero = new Hero();
        newHero.setFirstName(DTO.getFirstName());
        newHero.setLastName(DTO.getLastName());
        newHero.setTitles(DTO.getTitles());

        if (DTO.getId() != null){
            newHero.setId(DTO.getId());
        }
        return newHero;
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
