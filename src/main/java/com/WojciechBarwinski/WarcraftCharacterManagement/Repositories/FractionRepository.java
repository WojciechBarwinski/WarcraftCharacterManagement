package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Fraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface FractionRepository extends JpaRepository<Fraction, Long> {

    Optional<Fraction> findByName(String fractionName);

    @Query("SELECT f FROM Fraction f WHERE f.name IN ?1")
    Set<Fraction> findByNames(Set<String> names);
}
