package com.WojciechBarwinski.WarcraftCharacterManagement.repository;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
