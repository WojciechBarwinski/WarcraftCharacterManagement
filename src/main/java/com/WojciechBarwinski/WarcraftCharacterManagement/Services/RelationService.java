package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;

public interface RelationService {

    //TODO Zmienic nazwy by były bardziej selfDescription!!!!!!!
    void addOneDirectionRelation(Long hero1Id, Long hero2Id, String hero2ToHero1);
    void addTwoDirectionsRelation(Long hero1Id, Long hero2Id, String hero2ToHero1, String hero1ToHero2);
}
