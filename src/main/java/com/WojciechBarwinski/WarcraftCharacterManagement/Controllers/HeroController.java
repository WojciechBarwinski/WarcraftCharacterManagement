package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.HeroService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/heroes")
public class HeroController {

    private final HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @ApiOperation(value = "Get all heroes", notes = "Returns list with all heroes")
    @GetMapping("/")
    public List<HeroDTO> getAllHero(@RequestParam int page, Sort.Direction direction){
        return heroService.getAllHeroes(page, direction);
    }

    @ApiOperation(value = "Get a hero by id", notes = "Returns hero as per id")
    @GetMapping(value = "/{id}")
    public HeroDTO getHeroById(@PathVariable Long id){
        return heroService.getHeroById(id);
    }

    @PostMapping("/put")
    public ResponseEntity<HeroDTO> addNewHero(@RequestBody HeroDTO heroDTO){
        heroService.addHero(heroDTO);
        HeroDTO createdHero = heroService.getHeroByFirstName(heroDTO.getFirstName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHero);
    }

}
