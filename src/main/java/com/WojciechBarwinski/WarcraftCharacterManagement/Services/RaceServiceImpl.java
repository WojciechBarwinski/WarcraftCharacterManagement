package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RaceDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Race;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionChecker;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.IncorrectDateException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ResourceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.RaceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.RaceMapper.mapDTOToRace;
import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.RaceMapper.mapRaceToDTO;

@Service
public class RaceServiceImpl implements RaceService {

    private final RaceRepository raceRepository;
    private final HeroRepository heroRepository;
    private final ExceptionChecker exceptCheck;

    public RaceServiceImpl(RaceRepository raceRepository,
                           HeroRepository heroRepository,
                           ExceptionChecker exceptCheck) {
        this.raceRepository = raceRepository;
        this.heroRepository = heroRepository;
        this.exceptCheck = exceptCheck;
    }

    @Override
    public RaceDTO createRace(RaceDTO raceDTO) {
        exceptCheck.ifDescriptionDoesNotExist(raceDTO.getDescription());
        exceptCheck.ifRaceNameDoesNotExist(raceDTO.getName());
        Race save = raceRepository.save(mapDTOToRace(raceDTO));
        return mapRaceToDTO(save);
    }

    @Override
    public RaceDTO getRace(String raceName) {
        Race race = raceRepository.findByNameIgnoreCase(raceName)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono rasy o " + raceName));

        return mapRaceToDTO(race);
    }

    @Override
    public Set<RaceDTO> getAllRaces() {
        Set<RaceDTO> allRace = new HashSet<>();
        for (Race race : raceRepository.findAll()) {
            allRace.add(mapRaceToDTO(race));
        }

        return allRace;
    }

    @Override
    public RaceDTO updateRace(String name, String description) {
        Race save = raceRepository.save(new Race(name, description));
        return mapRaceToDTO(save);
    }

    @Transactional
    @Override
    public void deleteRace(String raceName) {
        if (heroRepository.existsByRace_Name(raceName)) {
            throw new IncorrectDateException("Istnieja postacie bedące tej rasy");
        } else
            raceRepository.deleteByNameIgnoreCase(raceName);
    }
}
