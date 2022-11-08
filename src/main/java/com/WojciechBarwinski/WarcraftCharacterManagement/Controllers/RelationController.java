package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;


import com.WojciechBarwinski.WarcraftCharacterManagement.Services.RelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/heroes/{heroId}/relations")
public class RelationController {

    private final RelationService relationService;

    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }

    @ApiOperation(value = "Create a new relations to hero", notes = "Need id other hero and description")
    @PostMapping()
    public ResponseEntity<Map<String, String>> createNewRelation(@PathVariable Long heroId, Map<String, String> newRelation){

        return null;
    }


    @GetMapping()
    public Map<String, String> readRelationOfHero(@PathVariable Long heroId){
        Map<String, String> relation = relationService.allRelationByHero(heroId);
        return relation;
    }

}
