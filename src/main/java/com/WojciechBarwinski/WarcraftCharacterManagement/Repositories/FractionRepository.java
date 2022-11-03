package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Fraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FractionRepository extends JpaRepository<Fraction, Long> {

    Optional<Fraction> findByName(String fractionName);
}
