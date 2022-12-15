package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTOToPreview;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Book;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Fraction;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Race;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionMessage;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ResourceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.BookRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.FractionRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.RaceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper.mapDTOToNewHero;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper.mapHeroToDTO;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final RaceRepository raceRepository;
    private final FractionRepository fractionRepository;
    private final BookRepository bookRepository;
    private final ExceptionMessage em;


    public HeroServiceImpl(HeroRepository heroRepository,
                           RaceRepository raceRepository,
                           FractionRepository fractionRepository,
                           BookRepository bookRepository,
                           ExceptionMessage exceptionMessage) {
        this.heroRepository = heroRepository;
        this.raceRepository = raceRepository;
        this.fractionRepository = fractionRepository;
        this.bookRepository = bookRepository;
        this.em = exceptionMessage;
    }

    public List<HeroDTOToPreview> getAllHeroes() {
        return heroRepository.findAllHeroes().stream()
                .map(HeroMapper::mapHeroToPreview)
                .collect(Collectors.toList());

    }

    @Override
    public HeroDTO getHeroById(Long id) {
        return mapHeroToDTO(heroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noHeroId") + id)));
    }

    @Override
    public HeroDTO getHeroByFirstName(String firstName) {
        return mapHeroToDTO(heroRepository.findByFirstName(firstName)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noHeroName") + firstName)));
    }

    @Transactional
    @Override
    public HeroDTO createNewHero(HeroDTO dto) {
        Hero save = heroRepository.saveAndFlush(buildNewHero(dto));
        return mapHeroToDTO(save);
    }

    @Transactional
    @Override
    public HeroDTO updateHero(Long id, HeroDTO dto) {
        Hero save = heroRepository.save(buildUpdateHero(id, dto));
        return mapHeroToDTO(save);
    }

    @Override
    public void deleteHero(Long id) {
        if (!heroRepository.existsById(id)) {
            throw new ResourceNotFoundException(em.getMessage("noHeroId") + id);
        }
        heroRepository.deleteById(id);
    }

    private Hero buildUpdateHero(Long id, HeroDTO dto) {
        Hero hero = heroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noHeroId") + id));

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

    private Hero buildNewHero(HeroDTO dto) {
        Hero hero = mapDTOToNewHero(dto);
        hero.setRace(checkRace(dto.getRace()));
        hero.setFractions(checkFraction(dto.getFractions()));
        hero.setBooks(checkBook(dto.getBooks()));
        return hero;
    }

    private Race checkRace(String raceName) {
        return raceRepository.findByNameIgnoreCase(raceName)
                .orElseThrow(() -> new ResourceNotFoundException(em.getMessage("noRaceName") + raceName));
    }

    private Set<String> updateTitleSet(Set<String> originalTitles, Set<String> titlesToCheck) {
        for (String title : titlesToCheck) {
            if (originalTitles.contains(title)) {
                originalTitles.remove(title);
            } else {
                originalTitles.add(title);
            }
        }
        return originalTitles;
    }

    private Set<Fraction> updateFractionSet(Set<Fraction> originalFractions, Set<String> fractionsToCheck) {
        for (Fraction fraction : checkFraction(fractionsToCheck)) {
            if (originalFractions.contains(fraction)) {
                originalFractions.remove(fraction);
            } else {
                originalFractions.add(fraction);
            }
        }
        return originalFractions;
    }

    private Set<Book> updateBookSet(Set<Book> originalBooks, Set<String> booksToCheck) {
        for (Book book : checkBook(booksToCheck)) {
            if (originalBooks.contains(book)) {
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

        throw new ResourceNotFoundException(em.getMessage("noFractionName") + stringWithIncorrectNames(names));
    }

    private void throwExceptionWithIncorrectBooksTitles(Set<String> names, Set<Book> fractions) {
        Set<String> collect = fractions.stream()
                .map(Book::getTitle).collect(Collectors.toSet());
        names.removeAll(collect);

        throw new ResourceNotFoundException(em.getMessage("noBookTitle") + stringWithIncorrectNames(names));
    }

    private String stringWithIncorrectNames(Set<String> names) {
        StringBuilder message = new StringBuilder();
        for (String s : names) {
            message.append(s).append(" ");
        }
        return message.toString();
    }

}
