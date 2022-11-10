package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RelationDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;

import java.util.Set;

public interface RelationService {

    void addOneDirectionRelation(Hero hero1, Hero hero2, String hero2ToHero1);
    Set<RelationDTO> addNewRelation(Long heroId, RelationDTO newRelation);
    Set<RelationDTO> allRelationByHero(Long id);
    Set<RelationDTO> updateRelation(Long ownerId, RelationDTO updateRelation);
    void deleteOneSideOfRelation(Long ownerId, Long heroId);
    void deleteTwoSidesOfRelation(Long ownerId, Long heroId);


}
