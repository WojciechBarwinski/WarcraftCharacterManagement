package com.WojciechBarwinski.WarcraftCharacterManagement.Exception;

import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import org.springframework.stereotype.Component;

@Component
public class ExceptionChecker {

    private final HeroRepository heroRepository;
    private final ExceptionMessage em;


    ExceptionChecker(HeroRepository heroRepository,
                     ExceptionMessage exceptionMessage) {
        this.heroRepository = heroRepository;
        this.em = exceptionMessage;
    }

    public void ifHeroDoesNotExist(Long id) {
        if (!heroRepository.existsById(id)) {
            throw new HeroNotFoundException(id.toString());
        }
    }

    public void ifDescriptionDoesNotExist(String description) {
        if (description == null || description.isEmpty()) {
            throw new IncorrectDateException(em.getMessage("noDescription"));
        }
    }

    public void ifIdAndNameAreNull(Long id, String name) {
        if (id == null && name == null) {
            throw new ResourceNotFoundException(em.getMessage("nameAndIdNull"));
        }
    }

    public void ifOwnerIdAndHeroIdAreTheSame(Long ownerId, Long heroId) {
        if (ownerId.equals(heroId)) {
            throw new UpdateConflictException(em.getMessage("theSameIds"));
        }
    }

    public void ifHeroIdIsNull(Long id) {
        if (id == null) {
            throw new IncorrectDateException(em.getMessage("nullHeroId"));
        }
    }

    public void ifItemNameIsNull(String itemName) {
        if (itemName == null) {
            throw new IncorrectDateException(em.getMessage("nullItemName"));
        }
    }

    public void ifRaceNameDoesNotExist(String name) {
        if (name == null || name.isEmpty()) {
            throw new IncorrectDateException(em.getMessage("nullRaceName"));
        }
    }

    public void ifAuthorNamesAreNull(String firstName, String lastName) {
        if ((firstName == null || firstName.isEmpty()) || (lastName == null || lastName.isEmpty())) {
            throw new IncorrectDateException(em.getMessage("nullHeroNames"));
        }
    }
}

