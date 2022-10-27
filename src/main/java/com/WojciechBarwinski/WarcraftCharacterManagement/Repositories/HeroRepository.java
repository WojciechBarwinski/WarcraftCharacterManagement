package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {
}
