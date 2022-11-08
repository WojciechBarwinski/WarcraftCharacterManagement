package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;

public interface RelationService {

    //TODO Zmienic nazwy by były bardziej selfDescription!!!!!!!
    void addOneDirectionRelation(Hero hero1, Hero hero2, String hero2ToHero1);
    //void addTwoDirectionsRelation(Long hero1Id, Long hero2Id, String hero2ToHero1, String hero1ToHero2);
}
