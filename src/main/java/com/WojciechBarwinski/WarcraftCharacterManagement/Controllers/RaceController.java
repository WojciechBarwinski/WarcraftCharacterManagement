package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RaceDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Services.RaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController()
@RequestMapping("/races")
public class RaceController {

    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @PostMapping
    public ResponseEntity<RaceDTO> createRace(@RequestBody RaceDTO raceDTO) {
        RaceDTO race = raceService.createRace(raceDTO);
        return getCorrectURILocation(race);
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
    public ResponseEntity<RaceDTO> updateRace(@PathVariable String name, @RequestBody String description) {
        RaceDTO race = raceService.updateRace(name, description);
        return getCorrectURILocation(race);
    }

    @DeleteMapping(value = "/{name}")
    public void deleteRace(@PathVariable String name) {
        raceService.deleteRace(name);
    }

    private ResponseEntity<RaceDTO> getCorrectURILocation(RaceDTO raceDTO) {
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/races/{name}")
                .buildAndExpand(raceDTO.getName())
                .toUri();
        return ResponseEntity.created(location).body(raceDTO);
    }
}
