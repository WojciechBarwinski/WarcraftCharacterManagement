package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.HeroNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper.mapHeroToHeroDTO;

@Service
public class HeroServiceImpl implements HeroService {

    HeroRepository heroRepository;
    private static final int PAGE_SIZE = 8;


    public HeroServiceImpl(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public List<HeroDTO> getAllHeroes(int page, Sort.Direction direction){
        List<HeroDTO> heroesDTO = new ArrayList<>();
        for (Hero hero : heroRepository.findAllHeroes(PageRequest.of(page, PAGE_SIZE, Sort.by(direction, "firstName")))) {
            heroesDTO.add(mapHeroToHeroDTO(hero));
        }
        return heroesDTO;
    }

    @Override
    public HeroDTO getOneHero() {
        Hero hero = heroRepository.findById(1L).get();
        return mapHeroToHeroDTO(hero);
    }

    @Override
    public HeroDTO getHeroById(Long id) {
        Optional<Hero> hero = heroRepository.findById(id);

        if (hero.isEmpty()){
            throw new HeroNotFoundException("nie znaleziono postaci o id=" + id);
        }
        return mapHeroToHeroDTO(hero.get());
    }
    
}
