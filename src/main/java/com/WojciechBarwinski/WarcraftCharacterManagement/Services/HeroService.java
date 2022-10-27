package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;

import java.util.List;

public interface HeroService {

    List<HeroDTO> getAllHeroes();

    HeroDTO getOneHero();
}
