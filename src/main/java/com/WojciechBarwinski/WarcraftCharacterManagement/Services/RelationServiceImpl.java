package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Relation;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.RelationKey;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.RelationRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class RelationServiceImpl implements RelationService {

    RelationRepository relationRepository;

    public RelationServiceImpl(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }


    @Override
    public void addOneDirectionRelation(Hero hero1, Hero hero2, String hero2ToHero1) {
        relationRepository.save(new Relation(new RelationKey(hero1, hero2), hero2ToHero1));
    }

    @Override
    public Map<String, String> allRelationByHero(Long id) {
        Set<Relation> all = relationRepository.findByKey_OwnerId(id);
        return HeroMapper.mapRelations(all);
    }
}
