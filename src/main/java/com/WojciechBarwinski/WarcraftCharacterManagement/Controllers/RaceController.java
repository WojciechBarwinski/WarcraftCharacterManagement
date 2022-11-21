package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RaceDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.RaceService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController()
@RequestMapping("/races")
public class RaceController {

    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping
    public Set<RaceDTO> readAllRace(){
        return raceService.getAllRaces();
    }

    @GetMapping(value = "/{name}")
    public RaceDTO readRaceByName(@PathVariable String name){
        return raceService.getRace(name);
    }

    @PostMapping
    public RaceDTO createRace(@RequestBody RaceDTO raceDTO){
        return raceService.createRace(raceDTO);
    }

    @PutMapping(value = "/{name}")
    public RaceDTO updateRace(@PathVariable String name, @RequestBody String description){
        return raceService.updateRace(name, description);
    }

    @DeleteMapping(value = "/{name}")
    public void deleteRace(@PathVariable String name){
        raceService.deleteRace(name);
    }
}
