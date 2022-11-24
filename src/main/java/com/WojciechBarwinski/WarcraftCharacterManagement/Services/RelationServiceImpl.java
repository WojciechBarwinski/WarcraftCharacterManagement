package com.WojciechBarwinski.WarcraftCharacterManagement.Services;

import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.RelationDTO;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Hero;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.Relation;
import com.WojciechBarwinski.WarcraftCharacterManagement.Entities.RelationKey;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.ExceptionChecker;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.HeroNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.HeroRepository;
import com.WojciechBarwinski.WarcraftCharacterManagement.Repositories.RelationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

import static com.WojciechBarwinski.WarcraftCharacterManagement.Mappers.HeroMapper.mapRelations;

@Service
public class RelationServiceImpl implements RelationService {

    HeroRepository heroRepository;
    RelationRepository relationRepository;
    ExceptionChecker exceptCheck;

    public RelationServiceImpl(RelationRepository relationRepository,
                               HeroRepository heroRepository,
                               ExceptionChecker exceptCheck) {
        this.relationRepository = relationRepository;
        this.heroRepository = heroRepository;
        this.exceptCheck = exceptCheck;
    }

    @Override
    public void addOneDirectionRelation(Hero hero1, Hero hero2, String hero2ToHero1) {
        relationRepository.save(new Relation(new RelationKey(hero1, hero2), hero2ToHero1));
    }

    @Override
    public Set<RelationDTO> createNewRelation(Long ownerId, RelationDTO relation) {
        Long heroId = relation.getHeroId();
        String heroName = relation.getHeroFirstName();
        exceptCheck.ifDescriptionDoesNotExist(relation.getDescription());
        exceptCheck.ifHeroDoesNotExist(ownerId);
        exceptCheck.ifIdAndNameAreNull(heroId, heroName);

        if (heroId!= null){
            relationRepository.save(createRelationById(ownerId, heroId, relation.getDescription()));
        } else {
            relationRepository.save(createRelationByName(ownerId, heroName, relation.getDescription()));
        }
        return mapRelations(relationRepository.findByKey_OwnerId(ownerId));
    }

    @Override
    public Set<RelationDTO> allRelationByHero(Long id) {
        exceptCheck.ifHeroDoesNotExist(id);
        return mapRelations(relationRepository.findByKey_OwnerId(id));
    }

    @Override
    public Set<RelationDTO> updateRelation(Long ownerId, RelationDTO updateRelation) {
        Long heroId = updateRelation.getHeroId();
        exceptCheck.ifDescriptionDoesNotExist(updateRelation.getDescription());

        if (!relationRepository.existsByKey_OwnerIdAndKey_OtherId(ownerId, heroId)){
            return createNewRelation(ownerId, updateRelation);
        }
        Relation relation = relationRepository.findByKey_OwnerIdAndKey_OtherId(ownerId, heroId);
        relation.setDescription(updateRelation.getDescription());
        relationRepository.save(relation);
        return mapRelations(relationRepository.findByKey_OwnerId(ownerId));
    }

    @Transactional
    @Override
    public void deleteOneSideOfRelation(Long ownerId, Long heroId) {
        exceptCheck.ifOwnerIdAndHeroIdAreTheSame(ownerId, heroId);
        relationRepository.deleteByKey_OwnerIdAndKey_OtherId(ownerId, heroId);
    }

    @Transactional
    @Override
    public void deleteTwoSidesOfRelation(Long ownerId, Long heroId) {
        exceptCheck.ifOwnerIdAndHeroIdAreTheSame(ownerId, heroId);
        relationRepository.deleteByKey_OwnerIdAndKey_OtherId(ownerId, heroId);
        relationRepository.deleteByKey_OwnerIdAndKey_OtherId(heroId, ownerId);
    }

    private Relation createRelationById(Long ownerId, Long heroId, String description){
        exceptCheck.ifOwnerIdAndHeroIdAreTheSame(ownerId, heroId);
        exceptCheck.ifHeroDoesNotExist(heroId);
        RelationKey keyById = new RelationKey(heroRepository.findById(ownerId).orElseThrow(),
                heroRepository.findById(heroId).orElseThrow());
        return new Relation(keyById, description);
    }

    private Relation createRelationByName(Long ownerId, String heroName, String description){
        Hero hero = heroRepository.findByFirstName(heroName)
                .orElseThrow(() -> new HeroNotFoundException(heroName));
        exceptCheck.ifOwnerIdAndHeroIdAreTheSame(ownerId, hero.getId());
        RelationKey keyByName = new RelationKey(heroRepository.findById(ownerId).orElseThrow(),
                hero);
        return new Relation(keyByName, description);
    }
}
