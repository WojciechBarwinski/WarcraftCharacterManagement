package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.HeroNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.RaceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.RaceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper.mapHeroDTOToHero;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper.mapHeroToHeroDTO;

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
            heroesDTO.add(mapHeroToHeroDTO(hero));
        }
        return heroesDTO;
    }

    @Override
    public HeroDTO getHeroById(Long id) {
        Optional<Hero> hero = heroRepository.findById(id);

        if (hero.isEmpty()){
            throw new HeroNotFoundException("nie znaleziono postaci o id=" + id);
        }
        return mapHeroToHeroDTO(hero.get());
    }

    @Override
    public HeroDTO getHeroByFirstName(String firstName) {
        return mapHeroToHeroDTO(heroRepository.findByFirstName(firstName));
    }

    @Override
    public void addHero(HeroDTO heroDTO) {
        //Hero save = heroRepository.save(mapHeroDTOToHero(hero));
        Hero hero = mapHeroDTOToHero(heroDTO);
        hero.setRace(raceRepository.findByName(heroDTO.getRace())
                .orElseThrow(() -> new RaceNotFoundException(heroDTO.getRace() + " -> this race doesn't exist")));

        heroRepository.save(hero);
    }

}
