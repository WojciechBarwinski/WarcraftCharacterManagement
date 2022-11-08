package com.WojciechBarwinski.WarcraftCharacterManagement;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.*;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.*;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.RelationService;
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
    RelationService relationService;

    public DataInitializer(HeroRepository heroRepository,
                           ItemRepository itemRepository,
                           RaceRepository raceRepository,
                           FractionRepository fractionRepository,
                           PlaceRepository placeRepository,
                           AuthorRepository authorRepository,
                           BookRepository bookRepository,
                           RelationService relationService) {
        this.heroRepository = heroRepository;
        this.itemRepository = itemRepository;
        this.raceRepository = raceRepository;
        this.fractionRepository = fractionRepository;
        this.placeRepository = placeRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.relationService = relationService;
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

        placeRepository.findByName("Kalimdor").setUpperLocation(placeRepository.findByName("Azeroth"));
        placeRepository.findByName("Tendrasill").setUpperLocation(placeRepository.findByName("Kalimdor"));
        placeRepository.findByName("Durotar").setUpperLocation(placeRepository.findByName("Kalimdor"));

        tyrande.addFraction(elune);
        tyrande.addFraction(alliance);
        shandris.addFraction(elune);
        shandris.addFraction(alliance);
        alinya.addFraction(elune);
        thrall.addFraction(horde);
        sylvanas.addFraction(horde);
        sylvanas.addFraction(forsaken);


        tyrande.addTitle("aaaa");
        tyrande.addTitle("bbb");
        tyrande.addTitle("cccc");

        sylvanas.addTitle("suka");
        sylvanas.addTitle("królowa nieumarłych");

        Book tWoE = bookRepository.findById(1L).get();
        Book tDS = bookRepository.findById(2L).get();
        Book sylv = bookRepository.findById(3L).get();

        tWoE.setAuthor(authorRepository.findById(1L).get());
        tDS.setAuthor(authorRepository.findById(1L).get());
        sylv.setAuthor(authorRepository.findById(2L).get());

        tyrande.addBook(tWoE);
        tyrande.addBook(tDS);
        shandris.addBook(tWoE);
        shandris.addBook(tDS);
        sylvanas.addBook(sylv);
        thrall.addBook(sylv);

        relationService.addOneDirectionRelation(tyrande, shandris, "córka");
        relationService.addOneDirectionRelation(shandris, tyrande, "matka");

        Item bow = new Item("bow", "jakiś tam opis luku");
        Item sword = new Item("sword", "jakis tam opis miecza");
        sylvanas.addItem(bow);
        tyrande.addItem(sword);;
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
