package com.WojciechBarwinski.WarcraftCharacterManagement.Mappers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Book;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Fraction;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;

import java.util.HashSet;
import java.util.Set;


//TODO dodac buildera i przerefactorować
public class HeroMapper {

    private HeroMapper() {
    }

    public static HeroDTO mapHeroToHeroDTO(Hero hero){
        HeroDTO heroDTO = new HeroDTO();
        heroDTO.setId(hero.getId());
        heroDTO.setFirstName(hero.getFirstName());
        heroDTO.setLastName(hero.getLastName());
        heroDTO.setRace(hero.getRace().getName());
        heroDTO.setFractions(mapFactions(hero.getFractions()));
        heroDTO.setBooks(mapBooks(hero.getBooks()));

        return heroDTO;
    }

    public static Hero mapHeroDTOToHero(HeroDTO heroDTO){
        Hero newHero = new Hero();
        newHero.setFirstName(heroDTO.getFirstName());
        newHero.setLastName(heroDTO.getLastName());

        if (heroDTO.getId() != null){
            newHero.setId(heroDTO.getId());
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
