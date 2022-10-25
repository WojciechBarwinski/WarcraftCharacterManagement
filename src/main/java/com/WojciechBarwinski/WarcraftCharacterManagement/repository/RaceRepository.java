package com.WojciechBarwinski.WarcraftCharacterManagement.repository;


import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

    Optional<Race> findByName(String raceName);
}
