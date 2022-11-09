package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RelationDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RelationService {

    //TODO Zmienic nazwy by były bardziej selfDescription!!!!!!!
    void addOneDirectionRelation(Hero hero1, Hero hero2, String hero2ToHero1);
    Set<RelationDTO> addNewRelation(Long heroId, RelationDTO newRelation);

    Set<RelationDTO> allRelationByHero(Long id);
}
