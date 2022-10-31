package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface HeroService {
    List<HeroDTO> getAllHeroes(int page, Sort.Direction direction);
    HeroDTO getHeroById(Long id);

    void addHero(HeroDTO hero);
}
