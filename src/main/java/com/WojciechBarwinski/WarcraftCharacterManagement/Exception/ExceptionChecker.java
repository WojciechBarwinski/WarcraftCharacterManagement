package com.WojciechBarwinski.WarcraftCharacterManagement.Exception;

import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import org.springframework.stereotype.Component;

@Component
public class ExceptionChecker {

    private HeroRepository heroRepository;


    ExceptionChecker(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public void ifHeroDoesNotExist(Long id){
        if (!heroRepository.existsById(id)){
            throw new HeroNotFoundException(id.toString());
        }
    }

    public void ifHeroByNameDoesNotExist(String name){
        if (!heroRepository.existsByFirstName(name)){
            throw new HeroNotFoundException(name);
        }
    }

    public void ifIdAndNameAreNull(Long id, String name){
        if (id == null && name == null){
            throw new ResourceNotFoundException("Koniecznie należy podać imię lub id bohatera");
        }
    }

    public void ifOwnerIdAndHeroIdAreTheSame(Long ownerId, Long heroId){
        if (ownerId.equals(heroId)){
            throw new UpdateConflictException("Id bohatera w relacji jest identyczne jak obecnego bohatera");
        }
    }
}

