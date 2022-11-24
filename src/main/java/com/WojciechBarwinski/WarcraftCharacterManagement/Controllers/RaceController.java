package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.DTOFlag;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RaceDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.RaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.WojciechBarwinski.WarcraftCharacterManagement.ControllerType.RACE;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Controllers.RespEntityPathCreator.getCorrectResponseEntity;

@RestController()
@RequestMapping("/races")
public class RaceController {

    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @PostMapping
    public ResponseEntity<DTOFlag> createRace(@RequestBody RaceDTO raceDTO) {
        return getCorrectResponseEntity(RACE, raceService.createRace(raceDTO));
    }

    @GetMapping
    public Set<RaceDTO> readAllRace() {
        return raceService.getAllRaces();
    }

    @GetMapping(value = "/{name}")
    public RaceDTO readRaceByName(@PathVariable String name) {
        return raceService.getRace(name);
    }

    @PutMapping(value = "/{name}")
    public ResponseEntity<DTOFlag> updateRace(@PathVariable String name, @RequestBody String description) {
        return getCorrectResponseEntity(RACE, raceService.updateRace(name, description));
    }

    @DeleteMapping(value = "/{name}")
    public void deleteRace(@PathVariable String name) {
        raceService.deleteRace(name);
    }
}
