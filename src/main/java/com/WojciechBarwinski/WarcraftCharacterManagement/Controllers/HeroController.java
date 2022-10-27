package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.HeroService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HeroController {

    private HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("/allHero")
    public List<HeroDTO> getAllHero(){
        List<HeroDTO> allHeroes = heroService.getAllHeroes();
        return allHeroes;
    }

    @GetMapping(value = "/syl", produces = MediaType.APPLICATION_JSON_VALUE)
    public HeroDTO getOneHero(){
        HeroDTO oneHero = heroService.getOneHero();
        return oneHero;
    }
}
