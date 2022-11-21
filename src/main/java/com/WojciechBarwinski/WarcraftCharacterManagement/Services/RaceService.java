package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RaceDTO;

import java.util.Set;

public interface RaceService {

    RaceDTO createRace(RaceDTO raceDTO);
    RaceDTO getRace(String raceName);
    Set<RaceDTO> getAllRaces();

    RaceDTO updateRace(String name, String description);
    void deleteRace(String raceName);
}
