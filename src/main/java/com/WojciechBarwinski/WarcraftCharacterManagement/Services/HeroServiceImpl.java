package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTOToPreview;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<HeroDTOToPreview> getAllHeroes(int page, Sort.Direction direction){
        List<HeroDTOToPreview> heroesDTO = new ArrayList<>();
        for (Hero hero : heroRepository.findAllHeroes(PageRequest.of(page, PAGE_SIZE, Sort.by(direction, "firstName")))) {
            heroesDTO.add(mapHeroToPreview(hero));
        }
        return heroesDTO;
    }

    @Override
    public HeroDTO getHeroById(Long id) {
        return mapHeroToDTO(heroRepository.findById(id)
                .orElseThrow(()-> new HeroNotFoundException(id.toString())));
    }

    @Override
    public HeroDTO getHeroByFirstName(String firstName) {
        return mapHeroToDTO(heroRepository.findByFirstName(firstName)
                .orElseThrow(()->new HeroNotFoundException(firstName)));
    }

    @Transactional
    @Override
    public HeroDTO createNewHero(HeroDTO heroDTO) {
        Hero save = heroRepository.saveAndFlush(buildNewHero(heroDTO));
        return mapHeroToDTO(save);
    }

    @Transactional
    @Override
    public HeroDTO updateHero(HeroDTO heroDTO, Long id) {
        Hero hero = buildUpdateHero(heroDTO, id);
        return mapHeroToDTO(heroRepository.save(hero));
    }

    @Override
    public void deleteHero(Long id) {
        if(!heroRepository.existsById(id)){
            throw new HeroNotFoundException(id.toString());
        }
        heroRepository.deleteById(id);
    }

    private Hero buildUpdateHero(HeroDTO dto, Long id){
        Hero heroToUpdate = new Hero();
        Hero heroFromDB = heroRepository.findById(id)
                .orElseThrow(() ->new HeroNotFoundException(id.toString()));

        heroToUpdate.setId(id);

        if (dto.getFirstName() == null){
            heroToUpdate.setFirstName(heroFromDB.getFirstName());
        } else {
            heroToUpdate.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() == null){
            heroToUpdate.setLastName(heroFromDB.getLastName());
        } else {
            heroToUpdate.setLastName(dto.getLastName());
        }

        if (dto.getTitles().isEmpty()){
            heroToUpdate.setTitles(heroFromDB.getTitles());
        } else {
            heroToUpdate.setTitles(dto.getTitles());
        }

        if (dto.getRace() == null){
            heroToUpdate.setRace(heroFromDB.getRace());
        } else {
            heroToUpdate.setRace(checkRace(dto.getRace()));
        }

        if (dto.getFractions().isEmpty()){
            heroToUpdate.setFractions(heroFromDB.getFractions());
        } else {
            heroToUpdate.setFractions(checkFraction(dto.getFractions()));
        }

        if (dto.getBooks().isEmpty()){
            heroToUpdate.setBooks(heroFromDB.getBooks());
        } else {
            heroToUpdate.setBooks(checkBook(dto.getBooks()));
        }

        //TODO Reloation build!!

        return heroToUpdate;
    }

    private Hero buildNewHero(HeroDTO heroDTO){
        Hero hero = mapDTOToNewHero(heroDTO);
        hero.setRace(checkRace(heroDTO.getRace()));
        hero.setFractions(checkFraction(heroDTO.getFractions()));
        hero.setBooks(checkBook(heroDTO.getBooks()));
        return hero;
    }

    private Race checkRace(String raceName){
        return raceRepository.findByName(raceName)
                .orElseThrow(() -> new ResourceNotFoundException(raceName + " -> this race doesn't exist"));
    }

    private Set<Fraction> checkFraction(Set<String> fractionsName){
        Set<Fraction> fractions = fractionRepository.findByNames(fractionsName);

        if (fractionsName.size()!=fractions.size()){
            throwExceptionWithIncorrectFractionsName(fractionsName, fractions);
        }
        return fractions;
    }

    private Set<Book> checkBook(Set<String> booksTitles){
        Set<Book> books = bookRepository.findByTitles(booksTitles);

        if (booksTitles.size()!=books.size()){
            throwExceptionWithIncorrectBooksTitles(booksTitles, books);
        }
        return books;
    }

    private void throwExceptionWithIncorrectFractionsName(Set<String> names, Set<Fraction> fractions){
        Set<String> collect = fractions.stream()
                .map(Fraction::getName).collect(Collectors.toSet());
        names.removeAll(collect);

        throw new ResourceNotFoundException(stringWithIncorrectNames(names) + " -> this fraction/s doesn't exist");
    }

    private void throwExceptionWithIncorrectBooksTitles(Set<String> names, Set<Book> fractions){
        Set<String> collect = fractions.stream()
                .map(Book::getTitle).collect(Collectors.toSet());
        names.removeAll(collect);

        throw new ResourceNotFoundException(stringWithIncorrectNames(names) + " -> this fraction/s doesn't exist");
    }

    private String stringWithIncorrectNames(Set<String> names){
        StringBuilder message = new StringBuilder();
        for (String s : names) {
            message.append(s).append(" ");
        }
        return message.toString();
    }

}
