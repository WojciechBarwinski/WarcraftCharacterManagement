package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.HeroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/hero")
public class HeroController {

    private HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("/all")
    public List<HeroDTO> getAllHero(){
        List<HeroDTO> allHeroes = heroService.getAllHeroes();
        return allHeroes;
    }

    @GetMapping(value = "/{id}")
    public HeroDTO getOneHero(@PathVariable Long id){
        return heroService.getHeroById(id);
    }
}
