package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTOToPreview;

import java.util.List;

public interface HeroService {
    List<HeroDTOToPreview> getAllHeroes();
    HeroDTO getHeroById(Long id);
    HeroDTO getHeroByFirstName(String firstName);
    HeroDTO createNewHero(HeroDTO dto);
    HeroDTO updateHero(Long id, HeroDTO dto);
    void deleteHero(Long id);
}
