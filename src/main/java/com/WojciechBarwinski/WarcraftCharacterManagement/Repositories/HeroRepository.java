package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

    @Query("SELECT h FROM Hero h " +
            "JOIN FETCH h.race " +
            "LEFT JOIN FETCH h.fractions ")
    List<Hero> findAllHeroes();

    @Query("SELECT h FROM Hero h " +
            "LEFT JOIN FETCH h.books " +
            "LEFT JOIN FETCH h.race " +
            "LEFT JOIN FETCH h.fractions " +
            "LEFT JOIN FETCH h.titles " +
            "LEFT JOIN FETCH h.items " +
            "LEFT JOIN FETCH h.ownRelations " +
            "WHERE h.id = ?1")
    @Override
    Optional<Hero> findById(Long id);

    @Query("SELECT h FROM Hero h " +
            "LEFT JOIN FETCH h.books " +
            "LEFT JOIN FETCH h.race " +
            "LEFT JOIN FETCH h.fractions " +
            "LEFT JOIN FETCH h.titles " +
            "LEFT JOIN FETCH h.items " +
            "LEFT JOIN FETCH h.ownRelations " +
            "WHERE UPPER(h.firstName) = UPPER(?1)")
    Optional<Hero> findByFirstName(String firstName);

    boolean existsByRace_NameIgnoreCase(String name);

    Set<Hero> findByFirstNameIgnoreCaseIn(Set<String> heroes);
}

