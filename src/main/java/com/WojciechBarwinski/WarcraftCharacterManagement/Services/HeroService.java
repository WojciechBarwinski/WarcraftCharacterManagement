package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;

import java.util.List;

public interface HeroService {
    List<HeroDTO> getAllHeroes();
    HeroDTO getOneHero();

    HeroDTO getHeroById(Long id);
}
