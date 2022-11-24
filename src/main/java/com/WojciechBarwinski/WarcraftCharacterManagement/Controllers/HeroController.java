package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.DTOFlag;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.HeroDTOToPreview;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.HeroService;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.RelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.WojciechBarwinski.WarcraftCharacterManagement.ControllerType.HERO;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Controllers.RespEntityPathCreator.getCorrectResponseEntity;


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

    @ApiOperation(value = "Create a new hero", notes = "THIS METHOD DOESN'T CREATE NEW RELATION OR ITEM. Returns url and json with to new created hero")
    @PostMapping()
    public ResponseEntity<DTOFlag> createNewHero(@RequestBody HeroDTO heroDTO){
        return getCorrectResponseEntity(HERO, heroService.createNewHero(heroDTO));
    }

    @ApiOperation(value = "Read all heroes", notes = "Returns list with all heroes")
    @GetMapping()
    public List<HeroDTOToPreview> readAllHero(@RequestParam int page, Sort.Direction direction){
        return heroService.getAllHeroes(page, direction);
    }

    @ApiOperation(value = "Read a hero by id", notes = "Returns hero as per id")
    @GetMapping(value = "/{id}")
    public HeroDTO readHeroById(@PathVariable Long id){
        return heroService.getHeroById(id);
    }

    @ApiOperation(value = "Read a hero by first name", notes = "Returns hero as per name")
    @GetMapping("/")
    public HeroDTO readHeroByName(@RequestParam(value="name") String name){
        return heroService.getHeroByFirstName(name);
    }

    @ApiOperation(value = "Update a hero, need only new information", notes = "THIS METHOD DOESN'T UPDATE RELATION OR ITEM. Returns url and json with to updated hero")
    @PutMapping("/{id}")
    public ResponseEntity<DTOFlag> updateHero(@PathVariable Long id ,@RequestBody HeroDTO heroDTO){
        return getCorrectResponseEntity(HERO, heroService.updateHero(heroDTO, id));
    }

    @ApiOperation(value = "Delete hero by id", notes = "Return nothing")
    @DeleteMapping("/{id}")
    public void deleteHero(@PathVariable Long id){
        heroService.deleteHero(id);
    }
}
