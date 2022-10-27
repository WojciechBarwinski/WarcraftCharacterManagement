package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;


import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

    Race findByName(String raceName);
}
