package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Relation;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.RelationKey;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.RelationRepository;
import org.springframework.stereotype.Service;

@Service
public class RelationServiceImpl implements RelationService {

    RelationRepository relationRepository;

    public RelationServiceImpl(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }


    @Override
    public void addOneDirectionRelation(Long hero1Id, Long hero2Id, String hero2ToHero1) {
        relationRepository.save(new Relation(new RelationKey(hero1Id, hero2Id), hero2ToHero1));
    }

    @Override
    public void addTwoDirectionsRelation(Long hero1Id, Long hero2Id, String hero2ToHero1, String hero1ToHero2) {
        relationRepository.save(new Relation(new RelationKey(hero1Id, hero2Id), hero2ToHero1));
        relationRepository.save(new Relation(new RelationKey(hero2Id, hero1Id), hero1ToHero2));
    }
}
