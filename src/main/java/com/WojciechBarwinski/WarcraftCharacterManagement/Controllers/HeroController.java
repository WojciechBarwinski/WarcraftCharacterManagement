package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.HeroService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/heroes")
public class HeroController {

    private HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("/")
    public List<HeroDTO> getAllHero(@RequestParam int page, Sort.Direction direction){
        return heroService.getAllHeroes(page, direction);
    }

    @GetMapping(value = "/{id}")
    public HeroDTO getHeroById(@PathVariable Long id){
        return heroService.getHeroById(id);
    }

    @PostMapping("/put")
    public ResponseEntity addNewHero(@RequestBody HeroDTO hero){
        heroService.addHero(hero);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


/*    @GetMapping(value = "/test/{id}")
    public ResponseEntity<HeroDTO> getHeroByIdTest(@PathVariable Long id){
        HeroDTO heroById = heroService.getHeroById(id);
        return ResponseEntity.status(HttpStatus.OK).body(heroById);
    }*/
}
