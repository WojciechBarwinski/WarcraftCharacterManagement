package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import org.apache.commons.lang3.StringUtils;
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


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    public List<HeroDTOToPreview> getAllHeroes(int page, Sort.Direction direction) {
        List<HeroDTOToPreview> heroesDTO = new ArrayList<>();
        for (Hero hero : heroRepository.findAllHeroes(PageRequest.of(page, PAGE_SIZE, Sort.by(direction, "firstName")))) {
            heroesDTO.add(mapHeroToPreview(hero));
        }
        return heroesDTO;
    }

    @Override
    public HeroDTO getHeroById(Long id) {
        return mapHeroToDTO(heroRepository.findById(id)
                .orElseThrow(() -> new HeroNotFoundException(id.toString())));
    }

    @Override
    public HeroDTO getHeroByFirstName(String firstName) {
        return mapHeroToDTO(heroRepository.findByFirstName(firstName)
                .orElseThrow(() -> new HeroNotFoundException(firstName)));
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
        return mapHeroToDTO(heroRepository.save(buildUpdateHero(heroDTO, id)));
    }

    @Override
    public void deleteHero(Long id) {
        if (!heroRepository.existsById(id)) {
            throw new HeroNotFoundException(id.toString());
        }
        heroRepository.deleteById(id);
    }

    private Hero buildUpdateHero(HeroDTO dto, Long id) {
        Hero hero = heroRepository.findById(id)
                .orElseThrow(() -> new HeroNotFoundException(id.toString()));

        if (!StringUtils.isBlank(dto.getFirstName())) {
            hero.setFirstName(dto.getFirstName());
        }

        if (!StringUtils.isBlank(dto.getLastName())) {
            hero.setLastName(dto.getLastName());
        }

        if (!StringUtils.isBlank(dto.getRace())) {
            hero.setRace(checkRace(dto.getRace()));
        }

        if (!dto.getTitles().isEmpty()) {
            hero.setTitles(updateTitleSet(hero.getTitles(), dto.getTitles()));
        }

        if (!dto.getFractions().isEmpty()) {
            hero.setFractions(updateFractionSet(hero.getFractions(), dto.getFractions()));
        }

        if (!dto.getBooks().isEmpty()) {
            hero.setBooks(updateBookSet(hero.getBooks(), dto.getBooks()));
        }

        return hero;
    }

    private Hero buildNewHero(HeroDTO heroDTO) {
        Hero hero = mapDTOToNewHero(heroDTO);
        hero.setRace(checkRace(heroDTO.getRace()));
        hero.setFractions(checkFraction(heroDTO.getFractions()));
        hero.setBooks(checkBook(heroDTO.getBooks()));
        return hero;
    }

    private Race checkRace(String raceName) {
        return raceRepository.findByNameIgnoreCase(raceName)
                .orElseThrow(() -> new ResourceNotFoundException(raceName + " -> this race doesn't exist"));
    }

    private Set<String> updateTitleSet(Set<String> originalTitles, Set<String> titlesToCheck){
        for (String title : titlesToCheck) {
            if (originalTitles.contains(title)){
                originalTitles.remove(title);
            } else {
                originalTitles.add(title);
            }
        }

        return originalTitles;
    }

    private Set<Fraction> updateFractionSet(Set<Fraction> originalFractions, Set<String> fractionsToCheck){
        for (Fraction fraction : checkFraction(fractionsToCheck)) {
            if (originalFractions.contains(fraction)) {
                originalFractions.remove(fraction);
            } else {
                originalFractions.add(fraction);
            }
        }
        return originalFractions;
    }

    private Set<Book> updateBookSet(Set<Book> originalBooks, Set<String> booksToCheck){
        for (Book book : checkBook(booksToCheck)) {
            if (originalBooks.contains(book)){
                originalBooks.remove(book);
            } else {
                originalBooks.add(book);
            }
        }
        return originalBooks;
    }

    private Set<Fraction> checkFraction(Set<String> fractionsName) {
        Set<Fraction> fractions = fractionRepository.findByNames(fractionsName);

        if (fractionsName.size() != fractions.size()) {
            throwExceptionWithIncorrectFractionsName(fractionsName, fractions);
        }
        return fractions;
    }

    private Set<Book> checkBook(Set<String> booksTitles) {
        Set<Book> books = bookRepository.findByTitleIgnoreCaseIn(booksTitles);

        if (booksTitles.size() != books.size()) {
            throwExceptionWithIncorrectBooksTitles(booksTitles, books);
        }
        return books;
    }

    private void throwExceptionWithIncorrectFractionsName(Set<String> names, Set<Fraction> fractions) {
        Set<String> collect = fractions.stream()
                .map(Fraction::getName).collect(Collectors.toSet());
        names.removeAll(collect);

        throw new ResourceNotFoundException(stringWithIncorrectNames(names) + " -> this fraction/s doesn't exist");
    }

    private void throwExceptionWithIncorrectBooksTitles(Set<String> names, Set<Book> fractions) {
        Set<String> collect = fractions.stream()
                .map(Book::getTitle).collect(Collectors.toSet());
        names.removeAll(collect);

        throw new ResourceNotFoundException(stringWithIncorrectNames(names) + " -> this fraction/s doesn't exist");
    }

    private String stringWithIncorrectNames(Set<String> names) {
        StringBuilder message = new StringBuilder();
        for (String s : names) {
            message.append(s).append(" ");
        }
        return message.toString();
    }

}
