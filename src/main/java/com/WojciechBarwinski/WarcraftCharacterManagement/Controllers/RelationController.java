package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RelationDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.RelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController()
@RequestMapping("/heroes/{heroId}/relations")
public class RelationController {

    private final RelationService relationService;

    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new relations to hero", notes = "Need id other hero and description. Return all hero's relations")
    @PostMapping()
    public Set<RelationDTO> createNewRelation(@PathVariable Long heroId, @RequestBody RelationDTO newRelation){
        return relationService.addNewRelation(heroId, newRelation);
    }

    @ApiOperation(value = "Read all relations of current hero", notes = "show all relations")
    @GetMapping()
    public Set<RelationDTO> readRelationOfHero(@PathVariable Long heroId){
        return relationService.allRelationByHero(heroId);
    }

}
