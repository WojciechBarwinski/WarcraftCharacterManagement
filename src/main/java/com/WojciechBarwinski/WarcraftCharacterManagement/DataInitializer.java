package com.WojciechBarwinski.WarcraftCharacterManagement;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.*;
import com.WojciechBarwinski.WarcraftCharacterManagement.repository.*;
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
    PlaceRepository placeRepository;
    AuthorRepository authorRepository;
    BookRepository bookRepository;

    public DataInitializer(HeroRepository heroRepository,
                           ItemRepository itemRepository,
                           RaceRepository raceRepository,
                           FractionRepository fractionRepository,
                           PlaceRepository placeRepository,
                           AuthorRepository authorRepository,
                           BookRepository bookRepository) {
        this.heroRepository = heroRepository;
        this.itemRepository = itemRepository;
        this.raceRepository = raceRepository;
        this.fractionRepository = fractionRepository;
        this.placeRepository = placeRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        heroRepository.saveAll(createListOfHeroesToAdd());
        raceRepository.saveAll(createListOfRacesToAdd());
        fractionRepository.saveAll(createListOfFractionsToAdd());
        placeRepository.saveAll(createListOfPlacesToAdd());
        authorRepository.saveAll(createListOfAuthorsToAdd());
        bookRepository.saveAll(createListOfBooksToAdd());

        Item bow = new Item();
        Item sword = new Item();
        bow.setDescription("jakiś tam opis luku");
        sword.setDescription("jakis tam opis miecza");
        Hero sylvanas = heroRepository.findById(1L).get();
        Hero tyrande = heroRepository.findById(2L).get();
        Hero thrall = heroRepository.findById(3L).get();
        Hero alinya = heroRepository.findById(4L).get();
        Hero shandris = heroRepository.findById(5L).get();
        sylvanas.setRace(raceRepository.findByName("Undead"));
        tyrande.setRace(raceRepository.findByName("Night Elf"));
        shandris.setRace(raceRepository.findByName("Night Elf"));
        alinya.setRace(raceRepository.findByName("Night Elf"));
        thrall.setRace(raceRepository.findByName("Orc"));

        Fraction elune = fractionRepository.findByName("Sisterhood of Elune");
        Fraction horde = fractionRepository.findByName("Horde");
        Fraction alliance = fractionRepository.findByName("Alliance");
        Fraction forsaken = fractionRepository.findByName("Forsaken");

        placeRepository.findByName("Kalimdor").setUpperLocation(placeRepository.findByName("Azeroth"));
        placeRepository.findByName("Tendrasill").setUpperLocation(placeRepository.findByName("Kalimdor"));
        placeRepository.findByName("Durotar").setUpperLocation(placeRepository.findByName("Kalimdor"));

        elune.addHeroToFraction(tyrande);
        elune.addHeroToFraction(alinya);
        elune.addHeroToFraction(shandris);
        horde.addHeroToFraction(thrall);
        horde.addHeroToFraction(sylvanas);
        alliance.addHeroToFraction(tyrande);
        alliance.addHeroToFraction(alinya);
        alliance.addHeroToFraction(shandris);
        forsaken.addHeroToFraction(sylvanas);

        tyrande.addTitle("aaaa");
        tyrande.addTitle("bbb");
        tyrande.addTitle("cccc");

        sylvanas.addTitle("suka");
        sylvanas.addTitle("królowa nieumarłych");

        Book tWoE = bookRepository.findById(1L).get();
        Book tDS = bookRepository.findById(2L).get();
        Book sylv = bookRepository.findById(3L).get();

        tWoE.setAuthor(authorRepository.findById(1L).get());
        tWoE.addHeroToBook(tyrande);
        tWoE.addHeroToBook(shandris);

        tDS.setAuthor(authorRepository.findById(1L).get());
        tDS.addHeroToBook(tyrande);

        sylv.setAuthor(authorRepository.findById(2L).get());
        sylv.addHeroToBook(sylvanas);
        sylv.addHeroToBook(thrall);
        sylv.addHeroToBook(tyrande);

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
    private static List<Place> createListOfPlacesToAdd(){
        List<Place> places = new ArrayList<>();

        places.add(new Place("Azeroth", "opis Azeroth"));
        places.add(new Place("Kalimdor", "opis Kalimdoru"));
        places.add(new Place("Tendrasill", "opis Tendrassil"));
        places.add(new Place("Durotar", "opis Durotaru"));
        return places;
    }
    private static List<Author> createListOfAuthorsToAdd(){
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Richard A.", "Knaak", "USA"));
        authors.add(new Author("Christie", "Golden", "USA"));
        return authors;
    }
    private static List<Book> createListOfBooksToAdd(){
        List<Book> books = new ArrayList<>();

        books.add(new Book("The Well of Eternity", "jakis tam opis"));
            books.get(0).setSeries("War of The Ancients");
        books.add(new Book("The Demon Soul", "jakis tam opis"));
            books.get(1).setSeries("War of The Ancients");
        books.add(new Book("Sylvanas", "jakis tam opis"));
        return books;
    }
}
