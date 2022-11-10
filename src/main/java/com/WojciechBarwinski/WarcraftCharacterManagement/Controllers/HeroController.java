package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTOToPreview;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.HeroService;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.RelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController()
@RequestMapping("/heroes")
public class HeroController {

    private final HeroService heroService;
    private final RelationService relationService;

    public HeroController(HeroService heroService,
                          RelationService relationService) {
        this.heroService = heroService;
        this.relationService = relationService;
    }

    @ApiOperation(value = "Create a new hero", notes = "Returns url and json with to new created hero")
    @PostMapping()
    public ResponseEntity<HeroDTO> createNewHero(@RequestBody HeroDTO heroDTO){
        HeroDTO newHeroDTO = heroService.createNewHero(heroDTO);

        return getCorrectURILocation(newHeroDTO);
    }

    @ApiOperation(value = "Read all heroes", notes = "Returns list with all heroes")
    @GetMapping("/")
    public List<HeroDTOToPreview> readAllHero(@RequestParam int page, Sort.Direction direction){
        return heroService.getAllHeroes(page, direction);
    }

    @ApiOperation(value = "Read a hero by id", notes = "Returns hero as per id")
    @GetMapping(value = "/{id}")
    public HeroDTO readHeroById(@PathVariable Long id){
        return heroService.getHeroById(id);
    }

    @ApiOperation(value = "Read a hero by first name", notes = "Returns hero as per name")
    @GetMapping()
    public HeroDTO readHeroByName(@RequestParam(value="name") String name){
        return heroService.getHeroByFirstName(name);
    }

    @ApiOperation(value = "Update a hero, need only new information", notes = "Returns url and json with to updated hero")
    @PutMapping("/{id}")
    public ResponseEntity<HeroDTO> updateHero(@PathVariable Long id ,@RequestBody HeroDTO heroDTO){
        HeroDTO newHeroDTO = heroService.updateHero(heroDTO, id);
        return getCorrectURILocation(newHeroDTO);
    }

    @ApiOperation(value = "Delete hero by id", notes = "Return nothing")
    @DeleteMapping("/{id}")
    public void deleteHero(@PathVariable Long id){
        heroService.deleteHero(id);
    }

    private ResponseEntity<HeroDTO> getCorrectURILocation(HeroDTO heroDTO){
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/heroes/{id}")
                .buildAndExpand(heroDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(heroDTO);
    }
}
