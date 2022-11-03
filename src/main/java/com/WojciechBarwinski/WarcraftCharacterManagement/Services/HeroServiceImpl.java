package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Race;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.HeroNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.RaceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.UpdateConflictException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.RaceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper.mapDTOToHero;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper.mapHeroToDTO;

@Service
public class HeroServiceImpl implements HeroService {

    HeroRepository heroRepository;
    RaceRepository raceRepository;
    private static final int PAGE_SIZE = 5;


    public HeroServiceImpl(HeroRepository heroRepository,
                           RaceRepository raceRepository) {
        this.heroRepository = heroRepository;
        this.raceRepository = raceRepository;
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
        Hero hero = mapDTOToHero(heroDTO);
        hero.setRace(checkRace(heroDTO.getRace()));

        return mapHeroToDTO(heroRepository.save(hero));
    }

    @Override
    public HeroDTO updateHero(HeroDTO heroDTO, Long id) {
        if (!heroDTO.getId().equals(id)){
            throw new UpdateConflictException("Hero ID in JSON=" + heroDTO.getId() + " is incorrect with the path ID=" + id + " Don't change hero ID");
        }

        if (heroRepository.findById(id).isEmpty()){
            throw new HeroNotFoundException("There is no hero with id=" + id);
        }

        Hero hero = mapDTOToHero(heroDTO);
        hero.setRace(checkRace(heroDTO.getRace()));

        return mapHeroToDTO(heroRepository.save(hero));
    }

    //TODO zła nazwa
    private Race checkRace(String raceName){
        return raceRepository.findByName(raceName)
                .orElseThrow(() -> new RaceNotFoundException(raceName + " -> this race doesn't exist"));
    }
}
