package com.WojciechBarwinski.WarcraftCharacterManagement.repository;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Fraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FractionRepository extends JpaRepository<Fraction, Long> {
}
