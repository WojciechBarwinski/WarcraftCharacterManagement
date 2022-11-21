package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RelationDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.RelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController()
@RequestMapping("/heroes/{ownerId}/relations")
public class RelationController {

    private final RelationService relationService;

    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new relations to hero", notes = "Need id other hero and description. Return all hero's relations")
    @PostMapping()
    public Set<RelationDTO> createNewRelation(@PathVariable Long ownerId, @RequestBody RelationDTO newRelation) {
        return relationService.createNewRelation(ownerId, newRelation);
    }

    @ApiOperation(value = "Read all relations of current hero", notes = "show all relations")
    @GetMapping()
    public Set<RelationDTO> readRelationsOfHero(@PathVariable Long ownerId) {
        return relationService.allRelationByHero(ownerId);
    }

    @PutMapping
    public Set<RelationDTO> updateRelation(@PathVariable Long ownerId, @RequestBody RelationDTO updateRelation) {
        return relationService.updateRelation(ownerId, updateRelation);
    }

    @ApiOperation(value = "Delete relation but only by side of owner", notes = "return all remain relations")
    @DeleteMapping()
    public Set<RelationDTO> deleteOneSideOfRelation(@PathVariable Long ownerId, @RequestParam(value = "heroId") Long heroId) {
        relationService.deleteOneSideOfRelation(ownerId, heroId);
        return relationService.allRelationByHero(ownerId);
    }

    @ApiOperation(value = "Delete relation by both side", notes = "return all remain relations")
    @DeleteMapping("/{heroId}")
    public Set<RelationDTO> deleteTwoSidesOfRelation(@PathVariable Long ownerId, Long heroId) {
        relationService.deleteTwoSidesOfRelation(ownerId, heroId);
        return relationService.allRelationByHero(ownerId);
    }

}
