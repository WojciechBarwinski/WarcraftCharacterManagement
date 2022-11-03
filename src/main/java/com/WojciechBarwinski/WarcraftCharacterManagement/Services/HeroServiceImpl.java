package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Book;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Fraction;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Race;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.HeroNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ResourceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.BookRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.FractionRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.RaceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper.*;

@Service
public class HeroServiceImpl implements HeroService {

    HeroRepository heroRepository;
    RaceRepository raceRepository;
    FractionRepository fractionRepository;
    BookRepository bookRepository;
    private static final int PAGE_SIZE = 5;


    public HeroServiceImpl(HeroRepository heroRepository,
                           RaceRepository raceRepository,
                           FractionRepository fractionRepository,
                           BookRepository bookRepository) {
        this.heroRepository = heroRepository;
        this.raceRepository = raceRepository;
        this.fractionRepository = fractionRepository;
        this.bookRepository = bookRepository;
    }

    public List<HeroDTO> getAllHeroes(int page, Sort.Direction direction){
        List<HeroDTO> heroesDTO = new ArrayList<>();
        for (Hero hero : heroRepository.findAllHeroes(PageRequest.of(page, PAGE_SIZE, Sort.by(direction, "firstName")))) {
            heroesDTO.add(mapHeroToDTO(hero));
        }
        return heroesDTO;
    }

    @Override
    public HeroDTO getHeroById(Long id) {
        Optional<Hero> hero = heroRepository.findById(id);

        if (hero.isEmpty()){
            throw new HeroNotFoundException("nie znaleziono postaci o id=" + id);
        }
        return mapHeroToDTO(hero.get());
    }

    @Override
    public HeroDTO getHeroByFirstName(String firstName) {
        Optional<Hero> hero = heroRepository.findByFirstName(firstName);
        if (hero.isEmpty()){
            throw new HeroNotFoundException("nie znaleziono postaci o first name=" + firstName);
        }
        return mapHeroToDTO(hero.get());
    }

    @Override
    public HeroDTO createNewHero(HeroDTO heroDTO) {
        Hero hero = mapDTOToNewHero(heroDTO);
        hero.setRace(checkRace(heroDTO.getRace()));

        return mapHeroToDTO(heroRepository.save(hero));
    }

    @Override
    public HeroDTO updateHero(HeroDTO heroDTO, Long id) {

        if (heroRepository.findById(id).isEmpty()){
                throw new HeroNotFoundException("There is no hero with id=" + id);
            }
        Hero hero = mapDTOToHero(heroDTO, id);
        hero.setRace(checkRace(heroDTO.getRace()));
        hero.setFractions(checkFraction(heroDTO.getFractions()));
        hero.setBooks(checkBook(heroDTO.getBooks()));

        heroRepository.saveAndFlush(hero);

        Hero hero1 = heroRepository.findById(id).get();
        return mapHeroToDTO(hero1);
    }

    //TODO zła nazwa
    private Race checkRace(String raceName){
        return raceRepository.findByName(raceName)
                .orElseThrow(() -> new ResourceNotFoundException(raceName + " -> this race doesn't exist"));
    }

    private Set<Fraction> checkFraction(Set<String> fractionSet){
        Set<Fraction> fractions = new HashSet<>();
        for (String fraction : fractionSet) {
            fractions.add(fractionRepository.findByName(fraction)
                    .orElseThrow(() -> new ResourceNotFoundException(fraction + " -> this fraction doesn't exist")));
        }
        return fractions;
    }

    private Set<Book> checkBook(Set<String> bookSet){
        Set<Book> books = new HashSet<>();
        for (String book : bookSet) {
            books.add(bookRepository.findByTitle(book)
                    .orElseThrow(() -> new ResourceNotFoundException(book + " -> this book doesn't exist")));
        }
        return books;
    }
}
