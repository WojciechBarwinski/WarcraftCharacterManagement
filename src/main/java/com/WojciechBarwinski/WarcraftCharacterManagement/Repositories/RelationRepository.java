package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Relation;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.RelationKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationRepository extends JpaRepository<Relation, RelationKey> {
}
