package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.HeroNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HeroServiceImpl implements HeroService {

    HeroRepository heroRepository;


    public HeroServiceImpl(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public List<HeroDTO> getAllHeroes(){
        List<HeroDTO> heroesDTO = new ArrayList<>();
        for (Hero hero : heroRepository.findAll()) {
            heroesDTO.add(HeroMapper.mapHeroToHeroDTO(hero));
        }
        return heroesDTO;
    }

    @Override
    public HeroDTO getOneHero() {
        Hero hero = heroRepository.findById(1L).get();
        return HeroMapper.mapHeroToHeroDTO(hero);
    }

    @Override
    public HeroDTO getHeroById(Long id) {
        Optional<Hero> hero = heroRepository.findById(id);

        if (hero.isEmpty()){
            throw new HeroNotFoundException("nie znaleziono postaci o id=" + id);
        }
        return HeroMapper.mapHeroToHeroDTO(hero.get());
    }
}
