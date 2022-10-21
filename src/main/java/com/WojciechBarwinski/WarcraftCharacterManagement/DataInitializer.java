package com.WojciechBarwinski.WarcraftCharacterManagement;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Fraction;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Item;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Race;
import com.WojciechBarwinski.WarcraftCharacterManagement.repository.FractionRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.repository.HeroRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.repository.ItemRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.repository.RaceRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent>{

    HeroRepository heroRepository;
    ItemRepository itemRepository;
    RaceRepository raceRepository;
    FractionRepository fractionRepository;

    public DataInitializer(HeroRepository heroRepository,
                           ItemRepository itemRepository,
                           RaceRepository raceRepository,
                           FractionRepository fractionRepository) {
        this.heroRepository = heroRepository;
        this.itemRepository = itemRepository;
        this.raceRepository = raceRepository;
        this.fractionRepository = fractionRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        heroRepository.saveAll(createListOfHeroesToAdd());
        raceRepository.saveAll(createListOfRacesToAdd());
        fractionRepository.saveAll(createListOfFractionsToAdd());

        Item bow = new Item();
        Item sword = new Item();
        bow.setItemDescription("jakiś tam opis luku");
        sword.setItemDescription("jakis tam opis miecza");
        Hero character1 = heroRepository.findById(1L).get();
        Hero character2 = heroRepository.findById(2L).get();
        character1.setRace(raceRepository.findByRaceName("Night Elf").get());
        character2.setRace(raceRepository.findByRaceName("Undead").get());
        bow.setOwner(character1);
        sword.setOwner(character2);
        itemRepository.save(bow);
        itemRepository.save(sword);
    }

    static List<Hero> createListOfHeroesToAdd(){
        List<Hero> heroes = new ArrayList<>();

        heroes.add(new Hero("Sylvanas", "Windrunner"));
        heroes.add(new Hero("Tyrande", "Whisperwind"));
        heroes.add(new Hero("Thall", null));
        heroes.add(new Hero("Alinya", null));
        heroes.add(new Hero("Shandris", "Feathermoon"));
        return heroes;
    }
    static List<Race> createListOfRacesToAdd(){
        List<Race> races = new ArrayList<>();

        races.add(new Race("Night Elf", "Jakis tam opis elfow"));
        races.add(new Race("Undead", "Jakis tam opis nieumarlego"));
        races.add(new Race("Human", "Jakis tam opis człowieka"));
        races.add(new Race("Dwarf", "Jakis tam opis krasnoluda"));
        races.add(new Race("Orc", "Jakis tam opis orka"));
        return races;
    }
    static List<Fraction> createListOfFractionsToAdd(){
        List<Fraction> fractions = new ArrayList<>();

        fractions.add(new Fraction("Sisterhood of Elune", "opis frakcji"));
        fractions.add(new Fraction("Alliance", "opis frakcji"));
        fractions.add(new Fraction("Forsaken", "opis frakcji"));
        fractions.add(new Fraction("Horde", "opis frakcji"));
        return fractions;
    }

}
