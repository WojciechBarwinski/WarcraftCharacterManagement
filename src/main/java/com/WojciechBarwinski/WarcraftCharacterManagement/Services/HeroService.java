package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface HeroService {
    List<HeroDTO> getAllHeroes(int page, Sort.Direction direction);
    HeroDTO getHeroById(Long id);
    HeroDTO getHeroByFirstName(String firstName);
    HeroDTO createNewHero(HeroDTO hero);
    HeroDTO updateHero(HeroDTO hero, Long id);
}
