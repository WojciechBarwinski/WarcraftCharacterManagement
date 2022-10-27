package com.WojciechBarwinski.WarcraftCharacterManagement.Mappers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;



public class HeroMapper {

    public static HeroDTO mapHeroToHeroDTO(Hero hero){
        HeroDTO heroDTO = new HeroDTO();
        heroDTO.setId(hero.getId());
        heroDTO.setFirstName(hero.getFirstName());
        heroDTO.setLastName(hero.getLastName());
        //heroDTO.setRace(hero.getRace());
        //heroDTO.setFractions(hero.getFractions());
        //heroDTO.setBooks(hero.getBooks());

        return heroDTO;
    }
}
