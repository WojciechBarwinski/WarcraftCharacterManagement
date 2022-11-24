package com.WojciechBarwinski.WarcraftCharacterManagement.Services;


import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RaceDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Race;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionChecker;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.IncorrectDateException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ResourceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.RaceMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.RaceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

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

        return mapRaceToDTO(raceRepository.save(mapDTOToRace(raceDTO)));
    }

    @Override
    public RaceDTO getRace(String raceName) {
        Race race = raceRepository.findByNameIgnoreCase(raceName)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono rasy o " + raceName));

        return mapRaceToDTO(race);
    }

    @Override
    public Set<RaceDTO> getAllRaces() {
        return raceRepository.findAll().stream()
                .map(RaceMapper::mapRaceToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public RaceDTO updateRace(String name, String description) {
        return mapRaceToDTO(raceRepository.save(new Race(name, description)));
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