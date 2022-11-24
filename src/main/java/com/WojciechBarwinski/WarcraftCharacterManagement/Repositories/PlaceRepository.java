package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlaceRepository extends JpaRepository<Place, Long> {

    Place findByNameIgnoreCase(String name);
}
