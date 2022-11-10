package com.WojciechBarwinski.WarcraftCharacterManagement.Repositories;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Relation;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.RelationKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RelationRepository extends JpaRepository<Relation, RelationKey> {

    Set<Relation> findByKey_OwnerId(Long id);

    Relation findByKey_OwnerIdAndKey_OtherId(Long ownerId, Long otherId);

    void deleteByKey_OwnerIdAndKey_OtherId(Long ownerId, Long otherId);

    boolean existsByKey_OwnerIdAndKey_OtherId(Long ownerId, Long otherId);

}
