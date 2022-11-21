package com.WojciechBarwinski.WarcraftCharacterManagement.Mappers;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RaceDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Race;

public class RaceMapper {

    private RaceMapper() {
    }


    public static RaceDTO mapRaceToDTO(Race race) {
        return RaceDTO.builder()
                .name(race.getName())
                .description(race.getDescription())
                .build();
    }

    public static Race mapDTOToRace(RaceDTO raceDTO) {
        return new Race(raceDTO.getName(), raceDTO.getDescription());
    }
}
