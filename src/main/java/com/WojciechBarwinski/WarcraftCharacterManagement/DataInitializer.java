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
        bow.setDescription("jakiś tam opis luku");
        sword.setDescription("jakis tam opis miecza");
        Hero sylvanas = heroRepository.findById(1L).get();
        Hero tyrande = heroRepository.findById(2L).get();
        Hero thrall = heroRepository.findById(3L).get();
        Hero alinya = heroRepository.findById(4L).get();
        Hero shandris = heroRepository.findById(5L).get();
        sylvanas.setRace(raceRepository.findByName("Undead").get());
        tyrande.setRace(raceRepository.findByName("Night Elf").get());
        shandris.setRace(raceRepository.findByName("Night Elf").get());
        alinya.setRace(raceRepository.findByName("Night Elf").get());
        thrall.setRace(raceRepository.findByName("Orc").get());

        Fraction elune = fractionRepository.findByName("Sisterhood of Elune").get();
        Fraction horde = fractionRepository.findByName("Horde").get();
        Fraction alliance = fractionRepository.findByName("Alliance").get();
        Fraction forsaken = fractionRepository.findByName("Forsaken").get();

        elune.addHeroToFraction(tyrande);
        elune.addHeroToFraction(alinya);
        elune.addHeroToFraction(shandris);
        horde.addHeroToFraction(thrall);
        horde.addHeroToFraction(sylvanas);
        alliance.addHeroToFraction(tyrande);
        alliance.addHeroToFraction(alinya);
        alliance.addHeroToFraction(shandris);
        forsaken.addHeroToFraction(sylvanas);


        bow.setOwner(sylvanas);
        sword.setOwner(tyrande);
        itemRepository.save(bow);
        itemRepository.save(sword);
    }

    private static List<Hero> createListOfHeroesToAdd(){
        List<Hero> heroes = new ArrayList<>();

        heroes.add(new Hero("Sylvanas", "Windrunner"));
        heroes.add(new Hero("Tyrande", "Whisperwind"));
        heroes.add(new Hero("Thall", null));
        heroes.add(new Hero("Alinya", null));
        heroes.add(new Hero("Shandris", "Feathermoon"));
        return heroes;
    }
    private static List<Race> createListOfRacesToAdd(){
        List<Race> races = new ArrayList<>();

        races.add(new Race("Night Elf", "Jakis tam opis elfow"));
        races.add(new Race("Undead", "Jakis tam opis nieumarlego"));
        races.add(new Race("Human", "Jakis tam opis człowieka"));
        races.add(new Race("Dwarf", "Jakis tam opis krasnoluda"));
        races.add(new Race("Orc", "Jakis tam opis orka"));
        return races;
    }
    private static List<Fraction> createListOfFractionsToAdd(){
        List<Fraction> fractions = new ArrayList<>();

        fractions.add(new Fraction("Sisterhood of Elune", "opis frakcji"));
        fractions.add(new Fraction("Alliance", "opis frakcji"));
        fractions.add(new Fraction("Forsaken", "opis frakcji"));
        fractions.add(new Fraction("Horde", "opis frakcji"));
        return fractions;
    }

}
