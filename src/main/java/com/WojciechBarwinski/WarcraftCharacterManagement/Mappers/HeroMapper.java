package com.WojciechBarwinski.WarcraftCharacterManagement.Mappers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTOToPreview;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RelationDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.*;

import java.util.HashSet;
import java.util.Set;


public class HeroMapper {

    private HeroMapper() {
    }

    public static HeroDTOToPreview mapHeroToPreview(Hero hero) {
        return HeroDTOToPreview.builder()
                .id(hero.getId())
                .firstName(hero.getFirstName())
                .lastName(hero.getLastName())
                .race(hero.getRace().getName())
                .fractions(mapFactions(hero.getFractions()))
                .build();
    }

    public static HeroDTO mapHeroToDTO(Hero hero) {
        return HeroDTO.builder()
                .id(hero.getId())
                .firstName(hero.getFirstName())
                .lastName(hero.getLastName())
                .titles(hero.getTitles())
                .race(hero.getRace().getName())
                .fractions(mapFactions(hero.getFractions()))
                .books(mapBooks(hero.getBooks()))
                .items(mapItems(hero.getItems()))
                .relations(mapRelations(hero.getOwnRelations()))
                .build();
    }

    public static Hero mapDTOToNewHero(HeroDTO DTO) {
        return Hero.builder()
                .firstName(DTO.getFirstName())
                .lastName(DTO.getLastName())
                .titles(DTO.getTitles())
                .build();
    }

    public static Set<RelationDTO> mapRelations(Set<Relation> ownRelations) {
        Set<RelationDTO> relations = new HashSet<>();
        if (ownRelations != null) {
            for (Relation relation : ownRelations) {
                relations.add(RelationDTO.builder()
                        .heroId(relation.getKey().getOther().getId())
                        .heroFirstName(relation.getKey().getOther().getFirstName())
                        .heroLastName(relation.getKey().getOther().getLastName())
                        .description(relation.getDescription())
                        .build());
            }
        }
        return relations;
    }

    private static Set<String> mapFactions(Set<Fraction> fractions) {
        Set<String> mappedFractions = new HashSet<>();
        for (Fraction fraction : fractions) {
            mappedFractions.add(fraction.getName());
        }
        return mappedFractions;
    }

    private static Set<String> mapBooks(Set<Book> books) {
        Set<String> mappedBooks = new HashSet<>();
        for (Book book : books) {
            mappedBooks.add(book.getTitle());
        }
        return mappedBooks;
    }

    private static Set<String> mapItems(Set<Item> items) {
        Set<String> mappedItems = new HashSet<>();
        if (items != null) {
            for (Item item : items) {
                mappedItems.add(item.getName());
            }
        }
        return mappedItems;
    }


}
