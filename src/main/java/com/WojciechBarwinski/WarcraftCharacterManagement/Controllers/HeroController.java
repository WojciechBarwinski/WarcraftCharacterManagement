package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.HeroService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @ApiOperation(value = "Get a hero by first name", notes = "Returns hero as per name")
    @GetMapping()
    public HeroDTO getHeroByName(@RequestParam(value="name") String name){
        return heroService.getHeroByFirstName(name);
    }


    @ApiOperation(value = "Create a new hero", notes = "Returns url and json with to new created hero")
    @PostMapping("/post")
    public ResponseEntity<HeroDTO> addNewHero(@RequestBody HeroDTO heroDTO){
        HeroDTO newHeroDTO = heroService.createNewHero(heroDTO);

        return getCorrectURILocation(newHeroDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HeroDTO> updateHero(@PathVariable Long id ,@RequestBody HeroDTO heroDTO){
        HeroDTO newHeroDTO = heroService.updateHero(heroDTO, id);
        return getCorrectURILocation(newHeroDTO);
    }

    private ResponseEntity<HeroDTO> getCorrectURILocation(HeroDTO heroDTO){
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/heroes/{id}")
                .buildAndExpand(heroDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(heroDTO);
    }
}
