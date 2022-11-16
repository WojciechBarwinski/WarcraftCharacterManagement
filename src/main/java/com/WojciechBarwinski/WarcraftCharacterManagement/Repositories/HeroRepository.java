package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

    @Query("SELECT h FROM Hero h " +
            "JOIN FETCH h.race " +
            "LEFT JOIN FETCH h.fractions ")
    List<Hero> findAllHeroes(Pageable page);

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
            "WHERE h.firstName = ?1")
    Optional<Hero> findByFirstName(String firstName);
}

